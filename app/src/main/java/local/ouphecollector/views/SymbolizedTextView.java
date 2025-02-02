package local.ouphecollector.views;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import local.ouphecollector.models.CardSymbol;

public class SymbolizedTextView extends AppCompatTextView {
    private static final String TAG = "SymbolizedTextView";

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
        Log.d(TAG, "setSymbolizedText called for: " + text);
        if (text == null || text.isEmpty()) {
            setText("");
            return;
        }
        replaceSymbolsWithImages(text);
    }

    private void replaceSymbolsWithImages(String text) {
        Log.d(TAG, "replaceSymbolsWithImages called for: " + text);
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String symbol = matcher.group(1);
            CardSymbol cardSymbol = SymbolManager.getInstance().getSymbol(symbol);
            if (cardSymbol != null) {
                String svgUri = cardSymbol.getSvg_uri();
                if (svgUri != null) {
                    RequestBuilder<PictureDrawable> requestBuilder = Glide.with(this.getContext())
                            .as(PictureDrawable.class)
                            .transition(DrawableTransitionOptions.withCrossFade());
                    try {
                        PictureDrawable pictureDrawable = requestBuilder.load(Uri.parse(svgUri)).submit().get();
                        pictureDrawable.setBounds(0, 0, pictureDrawable.getIntrinsicWidth(), pictureDrawable.getIntrinsicHeight());
                        ImageSpan imageSpan = new ImageSpan(pictureDrawable);
                        builder.setSpan(imageSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (ExecutionException e) {
                        Log.e(TAG, "ExecutionException while loading image for symbol: " + symbol, e);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "InterruptedException while loading image for symbol: " + symbol, e);
                        Thread.currentThread().interrupt();
                    }
                } else {
                    Log.e(TAG, "svgUri is null for symbol: " + symbol);
                }
            } else {
                Log.e(TAG, "Symbol not found: " + symbol);
            }
        }
        setText(builder);
        Log.d(TAG, "SymbolizedTextReady called");
    }
}