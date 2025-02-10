package local.ouphecollector.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.label.ImageLabel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import local.ouphecollector.R;
import local.ouphecollector.utils.ImageRecognizer;

public class ScannerFragment extends Fragment {

    private static final String TAG = "ScannerFragment";
    private PreviewView previewView;
    private ImageRecognizer imageRecognizer;
    private ExecutorService cameraExecutor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        previewView = view.findViewById(R.id.previewView);
        imageRecognizer = new ImageRecognizer();
        cameraExecutor = Executors.newSingleThreadExecutor();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startCamera();
    }

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
                    // Pass the ImageProxy to the ImageRecognizer
                    imageRecognizer.recognize(imageProxy, labels -> {
                        // This is the RecognitionCallback
                        // Check if the Fragment is still attached
                        if (isAdded()) {
                            // Update UI on the main thread
                            requireActivity().runOnUiThread(() -> {
                                if (labels != null) {
                                    if (!labels.isEmpty()) {
                                        // Process the labels
                                        for (ImageLabel label : labels) {
                                            Log.d(TAG, "Recognized label: " + label.getText() + " - Confidence: " + label.getConfidence());
                                        }
                                        Toast.makeText(getContext(), "Card recognized", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(TAG, "No card recognized");
                                    }
                                } else {
                                    Log.d(TAG, "No labels found");
                                }
                            });
                        } else {
                            Log.w(TAG, "Fragment is not attached, skipping UI update");
                        }
                    });
                });

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error starting camera: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
        imageRecognizer.shutdown();
    }
}