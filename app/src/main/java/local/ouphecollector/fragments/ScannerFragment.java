package local.ouphecollector.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import local.ouphecollector.utils.OupheTextRecognizer;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import local.ouphecollector.R;
import local.ouphecollector.utils.ImageRecognizer;
import local.ouphecollector.utils.ScryfallApi;
//import local.ouphecollector.utils.latin.TextRecognizer;

public class ScannerFragment extends Fragment {

    private static final String TAG = "ScannerFragment";
    private static final double CARD_LABEL_CONFIDENCE_THRESHOLD = 0.7;
    private static final String CARD_LABEL = "Card";
    private static final String PLAYING_CARD_LABEL = "Playing card";
    private PreviewView previewView;
    private ImageRecognizer imageRecognizer;
    private OupheTextRecognizer textRecognizer;
    private ExecutorService cameraExecutor;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private Button scanButton;
    private Button openButton;
    private TextView cardInfoTextView;
    private boolean isScanning = false;
    private ScryfallApi scryfallApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        previewView = view.findViewById(R.id.previewView);
        scanButton = view.findViewById(R.id.scanButton);
        openButton = view.findViewById(R.id.openButton);
        cardInfoTextView = view.findViewById(R.id.cardInfoTextView);
        imageRecognizer = new ImageRecognizer();
        textRecognizer = new OupheTextRecognizer();
        cameraExecutor = Executors.newSingleThreadExecutor();
        scryfallApi = new ScryfallApi();
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        startCamera();
                    } else {
                        Toast.makeText(getContext(), "Camera permission is required to use the scanner.", Toast.LENGTH_LONG).show();
                    }
                }
        );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
        scanButton.setOnClickListener(v -> {
            isScanning = true;
            scanButton.setEnabled(false);
            cardInfoTextView.setText("");
        });
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, imageProxy -> {
                    if (isScanning) {
                        imageRecognizer.recognize(imageProxy, (labels, error) -> {
                            if (isAdded()) {
                                requireActivity().runOnUiThread(() -> {
                                    if (error != null) {
                                        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                                    } else if (labels != null && !labels.isEmpty()) {
                                        boolean cardLabelFound = false;
                                        for (ImageLabel label : labels) {
                                            Log.d(TAG, "Recognized label: " + label.getText() + " - Confidence: " + label.getConfidence());
                                            if ((label.getText().equalsIgnoreCase(CARD_LABEL) || label.getText().equalsIgnoreCase(PLAYING_CARD_LABEL))
                                                    && label.getConfidence() >= CARD_LABEL_CONFIDENCE_THRESHOLD) {
                                                cardLabelFound = true;
                                                break;
                                            }
                                        }
                                        if (cardLabelFound) {
                                            recognizeText(imageProxy);
                                        }
                                    }
                                    isScanning = false;
                                    scanButton.setEnabled(true);
                                });
                            } else {
                                Log.w(TAG, "Fragment is not attached, skipping UI update");
                            }
                            imageProxy.close(); // Close the ImageProxy here, after processing
                        });
                    } else {
                        imageProxy.close(); // Close the ImageProxy here, if the frame is not analyzed
                    }
                });

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error starting camera: " + e.getMessage());
                Toast.makeText(getContext(), "Error starting camera: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    @androidx.camera.core.ExperimentalGetImage
    private void recognizeText(ImageProxy imageProxy) {
        InputImage inputImage = InputImage.fromMediaImage(Objects.requireNonNull(imageProxy.getImage()), imageProxy.getImageInfo().getRotationDegrees());
        textRecognizer.process(inputImage, new OupheTextRecognizer.TextRecognitionCallback() {
            @Override
            public void onTextRecognized(com.google.mlkit.vision.text.Text text) {
                String cardName = extractCardName(text);
                String setCode = extractSetCode(text);
                String collectorNumber = extractCollectorNumber(text);
                scryfallApi.getCardInfo(cardName, setCode, collectorNumber, (cardInfo, apiError) -> {
                    if (apiError != null) {
                        Toast.makeText(getContext(), apiError, Toast.LENGTH_LONG).show();
                    } else if (cardInfo != null) {
                        requireActivity().runOnUiThread(() -> {
                            String cardText = "Card Name: " + cardInfo.getName() + "\n" +
                                    "Set: " + cardInfo.getExpansionName() + "\n" +
                                    "Collector Number: " + cardInfo.getCollectorNumber();
                            cardInfoTextView.setText(cardText);
                            openButton.setEnabled(true);
                            openButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.purple_500));
                        });
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error recognizing text: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
        imageRecognizer.shutdown();
        textRecognizer.shutdown();
    }


    private String extractCardName(com.google.mlkit.vision.text.Text text) {
        String rawText = text.getText();
        String[] lines = rawText.split("\n");
        if (lines.length > 0) {
            return lines[0];
        }
        return "";
    }

    private String extractSetCode(com.google.mlkit.vision.text.Text text) {
        String rawText = text.getText();
        Pattern pattern = Pattern.compile("\\(([A-Z0-9]{3,5})\\)"); // Matches 3-5 uppercase letters/numbers in parentheses
        Matcher matcher = pattern.matcher(rawText);
        if (matcher.find()) {
            return matcher.group(1); // Returns the set code inside the parentheses
        }
        return "";
    }

    private String extractCollectorNumber(com.google.mlkit.vision.text.Text text) {
        String rawText = text.getText();
        Pattern pattern = Pattern.compile("(\\d+)/\\d+"); // Matches a number followed by a slash and another number
        Matcher matcher = pattern.matcher(rawText);
        if (matcher.find()) {
            return matcher.group(1); // Returns the collector number before the slash
        }
        return "";
    }
}