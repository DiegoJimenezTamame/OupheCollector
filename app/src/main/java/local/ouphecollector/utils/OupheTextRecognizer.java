package local.ouphecollector.utils;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class OupheTextRecognizer {
    private final TextRecognizer recognizer;

    public OupheTextRecognizer() {
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }

    public void process(InputImage image, TextRecognitionCallback callback) {
        recognizer.process(image)
                .addOnSuccessListener(callback::onTextRecognized)
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void shutdown() {
        recognizer.close();
    }

    public interface TextRecognitionCallback {
        void onTextRecognized(com.google.mlkit.vision.text.Text text);

        void onError(String error);
    }
}