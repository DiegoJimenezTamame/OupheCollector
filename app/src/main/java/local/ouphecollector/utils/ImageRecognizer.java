package local.ouphecollector.utils;

import android.media.Image;
import android.util.Log;

import androidx.annotation.OptIn;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageRecognizer {

    private static final String TAG = "ImageRecognizer";
    private static final float CONFIDENCE_THRESHOLD = 0.7f;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ImageLabeler labeler;

    public ImageRecognizer() {
        labeler = ImageLabeling.getClient(new ImageLabelerOptions.Builder().setConfidenceThreshold(CONFIDENCE_THRESHOLD).build());
    }

    public void recognize(ImageProxy imageProxy, RecognitionCallback callback) {
        Log.d(TAG, "Recognizing image");
        executorService.execute(() -> {
            InputImage image = getInputImageFromImageProxy(imageProxy, callback);
            if (image == null) {
                Log.e(TAG, "Failed to create InputImage from ImageProxy");
                return;
            }
            extractLabelsFromImage(image, callback);
        });
    }

    private void extractLabelsFromImage(InputImage image, RecognitionCallback callback) {
        labeler.process(image) // Removed the 'result' variable here
                .addOnSuccessListener(labels -> {
                    for (ImageLabel label : labels) {
                        Log.d(TAG, "Extracted label: " + label.getText() + " - Confidence: " + label.getConfidence());
                    }
                    callback.onLabelsRecognized(labels, null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error extracting labels: " + e.getMessage());
                    callback.onLabelsRecognized(null, "Error: " + e.getMessage());
                })
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Label extraction completed successfully.");
                    } else {
                        Log.e(TAG, "Label extraction failed.", task.getException());
                    }
                });
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private InputImage getInputImageFromImageProxy(ImageProxy imageProxy, RecognitionCallback callback) {
        Image mediaImage = imageProxy.getImage();
        if (mediaImage == null) {
            Log.e(TAG, "mediaImage is null");
            callback.onLabelsRecognized(null, "Error: mediaImage is null");
            return null;
        }
        try {
            return InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
        } catch (IllegalStateException e) {
            Log.e(TAG, "Error creating InputImage: " + e.getMessage());
            callback.onLabelsRecognized(null, "Error: " + e.getMessage());
            return null;
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public interface RecognitionCallback {
        void onLabelsRecognized(List<ImageLabel> labels, String error);
    }
}