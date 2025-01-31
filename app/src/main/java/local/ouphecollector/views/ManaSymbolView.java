package local.ouphecollector.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class ManaSymbolView extends androidx.appcompat.widget.AppCompatImageView {
    private String symbol;

    public ManaSymbolView(Context context) {
        super(context);
    }

    public ManaSymbolView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ManaSymbolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
        SymbolManager.getManaSymbolDrawable(getContext(), symbol, new SymbolManager.SymbolLoadCallback() { // Use SymbolManager.SymbolLoadCallback
            @Override
            public void onSymbolLoaded(Drawable drawable) {
                setImageDrawable(drawable);
            }

            @Override
            public void onSymbolLoadFailed(String symbol, Exception e) {
                // Handle the error, e.g., set a placeholder image
                setImageResource(android.R.drawable.ic_delete);
            }
        });
    }
}