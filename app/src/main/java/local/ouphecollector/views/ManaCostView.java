package local.ouphecollector.views;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
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
        Log.d(TAG, "setManaCost called for: " + manaCost);
        removeAllViews();
        if (manaCost == null || manaCost.isEmpty()) {
            return;
        }

        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(manaCost);

        while (matcher.find()) {
            String symbol = matcher.group(1);
            Log.d(TAG, "ManaCostView found symbol: " + symbol);
            ManaSymbolView symbolView = new ManaSymbolView(getContext());
            Log.d(TAG, "ManaSymbolView created");
            int width = 64;
            int height = 64;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            symbolView.setLayoutParams(params);
            symbolView.setSymbol(symbol);
            addView(symbolView);
        }
    }
}