package local.ouphecollector.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManaCostView extends LinearLayout {
    private static final String TAG = "ManaCostView";

    public ManaCostView(Context context) {
        super(context);
        init(context);
    }

    public ManaCostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ManaCostView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        List<String> symbols = extractSymbols(manaCost);
        for (String symbol : symbols) {
            Log.d(TAG, "ManaCostView found symbol: " + symbol);
            ManaSymbolView symbolView = new ManaSymbolView(getContext());
            Log.d(TAG, "ManaSymbolView created");
            symbolView.setSymbol(symbol);
            addView(symbolView);
        }
    }

    private List<String> extractSymbols(String manaCost) {
        List<String> symbols = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(manaCost);
        while (matcher.find()) {
            symbols.add(matcher.group(1));
        }
        return symbols;
    }
}