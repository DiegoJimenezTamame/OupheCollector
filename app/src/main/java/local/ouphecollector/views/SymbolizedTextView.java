package local.ouphecollector.views;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;


public class SymbolizedTextView extends androidx.appcompat.widget.AppCompatTextView {
    public interface SymbolizedTextCallback {
        void onSymbolizedTextReady(SpannableString symbolizedText);
    }
    public SymbolizedTextView(Context context) {
        super(context);
    }

    public SymbolizedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SymbolizedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSymbolizedText(String text) {
        SymbolManager.replaceSymbolsWithImages(getContext(), text, new SymbolizedTextCallback() {
            @Override
            public void onSymbolizedTextReady(SpannableString symbolizedText) {
                setText(symbolizedText);
            }
        });
    }
}