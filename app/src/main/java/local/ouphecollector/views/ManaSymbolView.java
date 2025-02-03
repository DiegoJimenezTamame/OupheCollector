package local.ouphecollector.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

public class ManaSymbolView extends androidx.appcompat.widget.AppCompatImageView {
    private static final String TAG = "ManaSymbolView";
    private String symbol;

    public ManaSymbolView(Context context) {
        super(context);
    }

    public ManaSymbolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ManaSymbolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSymbol(String symbol) {
        Log.d(TAG, "setSymbol called for: " + symbol);
        this.symbol = symbol;
        loadSymbol();
    }

    private void loadSymbol() {
        Log.d(TAG, "loadSymbol called for: " + symbol);
        SymbolManager.SymbolManagerCallback callback = new SymbolManager.SymbolManagerCallback() {
            @Override
            public void onSymbolsLoaded() {
                Drawable drawable = SymbolManager.getInstance(getContext()).getSymbol(symbol);
                if (drawable != null) {
                    setImageDrawable(drawable);
                } else {
                    Log.e(TAG, "Symbol not found: " + symbol);
                }
            }
        };
        if (!SymbolManager.getInstance(getContext()).isInitialized()) {
            SymbolManager.getInstance(getContext()).addCallback(callback);
        } else {
            callback.onSymbolsLoaded();
        }
    }
}