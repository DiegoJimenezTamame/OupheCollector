package local.ouphecollector.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.CardSymbol;
import local.ouphecollector.models.CardSymbolList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;

public class SymbolManager {
    private static final String TAG = "SymbolManager";
    private static Map<String, CardSymbol> symbolMap = new HashMap<>();
    private static boolean isInitialized = false;

    // Callback interface
    public interface SymbolManagerCallback {
        void onSymbolsLoaded();
    }

    public static void initialize(Context context, SymbolManagerCallback callback) {
        Log.d(TAG, "initialize called");
        if (!isInitialized) {
            Log.d(TAG, "initialize isInitialized = false");
            fetchSymbols(context, callback);
            isInitialized = true;
        } else {
            Log.d(TAG, "SymbolManager already initialized");
            callback.onSymbolsLoaded(); // Call the callback immediately if already initialized
        }
    }

    private static void fetchSymbols(Context context, SymbolManagerCallback callback) {
        Log.d(TAG, "fetchSymbols called");
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        Call<CardSymbolList> call = apiService.getCardSymbols();
        Log.d(TAG, "fetchSymbols call created");
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CardSymbolList> call, @NonNull Response<CardSymbolList> response) {
                Log.d(TAG, "fetchSymbols onResponse called");
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "fetchSymbols onResponse isSuccessful");
                    CardSymbolList symbolList = response.body();
                    List<CardSymbol> symbols = symbolList.getData();
                    for (CardSymbol symbol : symbols) {
                        symbolMap.put(symbol.getSymbol(), symbol);
                        Log.d(TAG, "Stored symbol: " + symbol.getSymbol() + ", SVG URI: " + symbol.getSvg_uri() + ", Placeholder URI: " + symbol.getLoose_variant());
                    }
                    Log.d(TAG, "Symbols fetched and stored successfully. Count: " + symbolMap.size());
                    callback.onSymbolsLoaded(); // Call the callback when symbols are loaded
                } else {
                    Log.e(TAG, "Error fetching symbols: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CardSymbolList> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching symbols", t);
                Log.e(TAG, "Error fetching symbols: " + t.getMessage());
            }
        });
    }

    public static CardSymbol getSymbol(String symbol) {
        Log.d(TAG, "getSymbol called for: " + symbol);
        if (symbolMap.containsKey(symbol)) {
            Log.d(TAG, "Symbol found: " + symbol + ", SVG URI: " + Objects.requireNonNull(symbolMap.get(symbol)).getSvg_uri());
            return symbolMap.get(symbol);
        } else {
            Log.e(TAG, "Symbol not found: " + symbol);
            return null;
        }
    }

    public static void replaceSymbolsWithImages(Context context, String text, SymbolizedTextView.SymbolizedTextCallback callback) {
        Log.d(TAG, "replaceSymbolsWithImages called for: " + text);
        if (text == null || text.isEmpty()) {
            callback.onSymbolizedTextReady(new SpannableString(""));
            return;
        }

        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String symbol = matcher.group(1);
            Log.d(TAG, "replaceSymbolsWithImages found symbol: " + symbol);
            CardSymbol cardSymbol = getSymbol(symbol);
            if (cardSymbol != null) {
                Log.d(TAG, "replaceSymbolsWithImages symbol found: " + symbol + ", SVG URI: " + cardSymbol.getSvg_uri());
                int start = matcher.start();
                int end = matcher.end();
                ManaSymbolView manaSymbolView = new ManaSymbolView(context);
                manaSymbolView.setSymbol(symbol);
                int width = 64;
                int height = 64;
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                manaSymbolView.layout(0, 0, width, height);
                manaSymbolView.draw(canvas);
                Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(drawable);
                spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        callback.onSymbolizedTextReady(spannableString);
    }
}