package local.ouphecollector.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import local.ouphecollector.models.CardSymbol;

public class SymbolizedTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String TAG = "SymbolizedTextView";

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
        Log.d(TAG, "setSymbolizedText called for: " + text);
        SymbolManager.replaceSymbolsWithImages(getContext(), text, symbolizedText -> {
            Log.d(TAG, "SymbolizedTextReady called");
            setText(symbolizedText);
        });
    }

    public void setSymbolizedText(String text, SymbolizedTextCallback callback) {
        Log.d(TAG, "setSymbolizedText called for: " + text);
        SymbolManager.initialize(getContext(), () -> {
            Log.d(TAG, "onSymbolsLoaded called");
            SymbolManager.replaceSymbolsWithImages(getContext(), text, callback);
        });
    }

    public static void replaceSymbolWithImage(Context context, SpannableString spannableString, int start, int end, CardSymbol cardSymbol) {
        Log.d(TAG, "replaceSymbolWithImage called for: " + cardSymbol.getSymbol());
        if (cardSymbol.getSvg_uri() == null) {
            Log.e(TAG, "replaceSymbolWithImage svg_uri is null");
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(cardSymbol.getSvg_uri())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d(TAG, "onResourceReady called for: " + cardSymbol.getSymbol());
                        Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        ImageSpan imageSpan = new ImageSpan(drawable);
                        spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        Log.d(TAG, "onLoadCleared called for: " + cardSymbol.getSymbol());
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        Log.e(TAG, "onLoadFailed called for: " + cardSymbol.getSymbol());
                        super.onLoadFailed(errorDrawable);
                    }
                });
    }
}