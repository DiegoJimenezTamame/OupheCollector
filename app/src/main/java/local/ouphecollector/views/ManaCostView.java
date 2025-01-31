package local.ouphecollector.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManaCostView extends LinearLayout {
    private static final String TAG = "ManaCostView";

    public ManaCostView(Context context) {
        super(context);
        init();
    }

    public ManaCostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ManaCostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void setManaCost(String manaCost) {
        removeAllViews();
        if (manaCost == null || manaCost.isEmpty()) {
            return;
        }

        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(manaCost);

        while (matcher.find()) {
            String symbol = matcher.group(1);
            ManaSymbolView symbolView = new ManaSymbolView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            symbolView.setLayoutParams(params);
            symbolView.setSymbol(symbol);
            addView(symbolView);
        }
    }
}