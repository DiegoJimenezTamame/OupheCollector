package local.ouphecollector.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;

public class SymbolManager {
    private static final String TAG = "SymbolManager";
    private static final Map<String, Drawable> symbolCache = new HashMap<>();
    private static final String SYMBOL_PATTERN = "\\{([^}]+)\\}";
    private static final String BASE_URL = "https://gatherer.wizards.com/Handlers/Image.ashx?size=medium&name=";
    private static Context appContext;
    private static final CoroutineDispatcher ioDispatcher = Dispatchers.getIO();
    private static final CoroutineDispatcher mainDispatcher = Dispatchers.getMain();

    public static void initialize(Context context) {
        appContext = context.getApplicationContext();
    }

    public interface SymbolLoadCallback {
        void onSymbolLoaded(Drawable drawable);

        void onSymbolLoadFailed(String symbol, Exception e);
    }

    public static void getManaSymbolDrawable(Context context, String symbol, SymbolLoadCallback callback) {
        if (symbolCache.containsKey(symbol)) {
            callback.onSymbolLoaded(symbolCache.get(symbol));
            return;
        }

        Job job = BuildersKt.launch(GlobalScope.INSTANCE, ioDispatcher, CoroutineStart.DEFAULT, (coroutineScope, continuation) -> {
            String imageUrl = BASE_URL + symbol;
            try {
                InputStream inputStream = new URL(imageUrl).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                symbolCache.put(symbol, drawable);
                BuildersKt.launch(GlobalScope.INSTANCE, mainDispatcher, CoroutineStart.DEFAULT, (coroutineScope1, continuation1) -> {
                    callback.onSymbolLoaded(drawable);
                    return Unit.INSTANCE;
                });
            } catch (IOException e) {
                Log.e(TAG, "Error loading image for symbol: " + symbol, e);
                BuildersKt.launch(GlobalScope.INSTANCE, mainDispatcher, CoroutineStart.DEFAULT, (coroutineScope1, continuation1) -> {
                    callback.onSymbolLoadFailed(symbol, e);
                    return Unit.INSTANCE;
                });
            }
            return Unit.INSTANCE;
        });
    }

    public static void replaceSymbolsWithImages(Context context, String text, final SymbolizedTextView.SymbolizedTextCallback callback) {
        if (text == null || text.isEmpty()) {
            callback.onSymbolizedTextReady(new SpannableString(""));
            return;
        }

        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(text);

        BuildersKt.launch(GlobalScope.INSTANCE, mainDispatcher, CoroutineStart.DEFAULT, (coroutineScope, continuation) -> {
            while (matcher.find()) {
                String symbol = matcher.group(1);
                final int start = matcher.start();
                final int end = matcher.end();
                getManaSymbolDrawable(context, symbol, new SymbolLoadCallback() {
                    @Override
                    public void onSymbolLoaded(Drawable drawable) {
                        if (drawable != null) {
                            ImageSpan imageSpan = new ImageSpan(drawable);
                            spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            callback.onSymbolizedTextReady(spannableString);
                        }
                    }

                    @Override
                    public void onSymbolLoadFailed(String symbol, Exception e) {
                        Log.e(TAG, "Error loading symbol: " + symbol, e);
                        callback.onSymbolizedTextReady(spannableString);
                    }
                });
            }
            callback.onSymbolizedTextReady(spannableString);
            return Unit.INSTANCE;
        });
    }
}