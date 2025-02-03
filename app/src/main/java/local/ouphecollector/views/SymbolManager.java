package local.ouphecollector.views;

import android.content.Context;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.CardSymbol;
import local.ouphecollector.models.CardSymbolList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SymbolManager {
    private static final String TAG = "SymbolManager";
    private static SymbolManager instance;
    private final Map<String, Drawable> symbolMap = new HashMap<>();
    private boolean isInitialized = false;
    private final List<SymbolManagerCallback> callbacks = new ArrayList<>();
    private final Context context;
    private final OkHttpClient okHttpClient;

    public interface SymbolManagerCallback {
        void onSymbolsLoaded();
    }

    private SymbolManager(Context context) {
        Log.d(TAG, "SymbolManager constructor called");
        this.context = context.getApplicationContext();
        this.okHttpClient = new OkHttpClient();
        loadSymbols();
    }

    public static synchronized SymbolManager getInstance(Context context) {
        Log.d(TAG, "getInstance called");
        if (instance == null) {
            instance = new SymbolManager(context);
        }
        return instance;
    }

    private void loadSymbols() {
        Log.d(TAG, "loadSymbols called");
        CardApiService apiService = RetrofitClient.getRetrofitInstance().create(CardApiService.class);
        retrofit2.Call<CardSymbolList> call = apiService.getCardSymbols();
        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<CardSymbolList> call, @NonNull retrofit2.Response<CardSymbolList> response) {
                Log.d(TAG, "loadSymbols onResponse called");
                if (response.isSuccessful() && response.body() != null) {
                    List<CardSymbol> symbols = response.body().getData();
                    for (CardSymbol symbol : symbols) {
                        String symbolName = symbol.getSymbol();
                        String svgUri = symbol.getSvg_uri();
                        Log.d(TAG, "Found symbol: " + symbolName + " with URI: " + svgUri);
                        downloadAndCacheSymbol(symbolName, svgUri);
                    }
                    isInitialized = true;
                    notifySymbolsLoaded();
                    Log.d(TAG, "loadSymbols onResponse isSuccessful, symbols size: " + symbols.size());
                } else {
                    Log.e(TAG, "Error fetching symbols: " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<CardSymbolList> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching symbols", t);
            }
        });
    }

    private void downloadAndCacheSymbol(String symbolName, String svgUri) {
        Request request = new Request.Builder().url(svgUri).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Failed to download symbol: " + symbolName, e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try (InputStream inputStream = response.body().byteStream()) {
                        SVG svg = SVG.getFromInputStream(inputStream);
                        Picture picture = svg.renderToPicture();
                        Drawable drawable = new PictureDrawable(picture);
                        symbolMap.put(symbolName, drawable);
                        Log.d(TAG, "Symbol downloaded and cached: " + symbolName);
                    } catch (SVGParseException e) {
                        Log.e(TAG, "Failed to parse SVG for symbol: " + symbolName, e);
                    }
                } else {
                    Log.e(TAG, "Failed to download symbol: " + symbolName + " - Response not successful");
                }
            }
        });
    }

    public Drawable getSymbol(String symbol) {
        Log.d(TAG, "getSymbol called for: " + symbol);
        Drawable drawable = symbolMap.get(symbol);
        if (drawable == null) {
            Log.e(TAG, "Symbol not found: " + symbol);
        }
        return drawable;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void addCallback(SymbolManagerCallback callback) {
        callbacks.add(callback);
    }

    private void notifySymbolsLoaded() {
        Log.d(TAG, "notifySymbolsLoaded called");
        for (SymbolManagerCallback callback : callbacks) {
            callback.onSymbolsLoaded();
        }
        callbacks.clear();
    }
}