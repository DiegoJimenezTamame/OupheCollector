package local.ouphecollector.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolizedTextView extends AppCompatTextView {
    private static final String TAG = "SymbolizedTextView";
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("\\{(.+?)\\}");

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
        Log.d(TAG, "setSymbolizedText called with text: " + text);
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Matcher matcher = SYMBOL_PATTERN.matcher(text);
        while (matcher.find()) {
            String symbol = matcher.group(1);
            Log.d(TAG, "Found symbol: " + symbol + " at start: " + matcher.start() + " end: " + matcher.end());
            Drawable drawable = SymbolManager.getInstance(getContext()).getSymbol(symbol);
            if (drawable != null) {
                drawable.setBounds(0, 0, 40, 40);
                ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                builder.setSpan(imageSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                Log.e(TAG, "Symbol not found: " + symbol);
            }
        }
        setText(builder);
    }
}