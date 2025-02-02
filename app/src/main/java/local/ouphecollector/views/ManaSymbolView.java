package local.ouphecollector.views;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import local.ouphecollector.models.CardSymbol;

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
        CardSymbol cardSymbol = SymbolManager.getInstance().getSymbol(symbol);
        if (cardSymbol != null) {
            String svgUri = cardSymbol.getSvg_uri();
            if (svgUri != null) {
                RequestBuilder<PictureDrawable> requestBuilder = Glide.with(this.getContext())
                        .as(PictureDrawable.class)
                        .transition(DrawableTransitionOptions.withCrossFade());
                requestBuilder.load(Uri.parse(svgUri)).into(this);
            } else {
                Log.e(TAG, "svgUri is null for symbol: " + symbol);
            }
        } else {
            Log.e(TAG, "Symbol not found: " + symbol);
        }
    }
}