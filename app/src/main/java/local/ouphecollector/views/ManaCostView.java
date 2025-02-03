package local.ouphecollector.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManaCostView extends LinearLayout {
    private static final String TAG = "ManaCostView";
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("\\{(.*?)\\}");

    public ManaCostView(Context context) {
        super(context);
        init(context);
    }

    public ManaCostView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ManaCostView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void setManaCost(String manaCost) {
        Log.d(TAG, "setManaCost called for: " + manaCost);
        removeAllViews();

        if (manaCost == null || manaCost.isEmpty()) {
            return;
        }

        SymbolizedTextView symbolizedTextView = new SymbolizedTextView(getContext());
        symbolizedTextView.setSymbolizedText(manaCost);
        addView(symbolizedTextView);
    }
}