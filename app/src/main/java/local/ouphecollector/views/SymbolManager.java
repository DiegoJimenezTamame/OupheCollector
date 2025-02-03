package local.ouphecollector.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import local.ouphecollector.api.CardApiService;
import local.ouphecollector.api.RetrofitClient;
import local.ouphecollector.models.CardSymbol;
import local.ouphecollector.models.CardSymbolList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.caverock.androidsvg.glide.SvgSoftwareLayerSetter;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat;

public class SymbolManager {
    private static final String TAG = "SymbolManager";
    private static SymbolManager instance;
    private final Map<String, Drawable> symbolMap = new HashMap<>();
    private boolean isInitialized = false;
    private final List<SymbolManagerCallback> callbacks = new ArrayList<>();
    private Context context;

    public interface SymbolManagerCallback {
        void onSymbolsLoaded();
    }

    private SymbolManager(Context context) {
        Log.d(TAG, "SymbolManager constructor called");
        this.context = context;
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
        Call<CardSymbolList> call = apiService.getCardSymbols();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CardSymbolList> call, @NonNull Response<CardSymbolList> response) {
                Log.d(TAG, "loadSymbols onResponse called");
                if (response.isSuccessful() && response.body() != null) {
                    List<CardSymbol> symbols = response.body().getData();
                    for (CardSymbol symbol : symbols) {
                        String symbolName = symbol.getSymbol();
                        String svgUri = symbol.getSvg_uri(); // Corrected line
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
            public void onFailure(@NonNull Call<CardSymbolList> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching symbols", t);
            }
        });
    }

    private void downloadAndCacheSymbol(String symbolName, String svgUri) {
        RequestBuilder<Drawable> requestBuilder = Glide.with(context)
                .load(svgUri)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .listener(new SvgSoftwareLayerSetter<Drawable>());

        requestBuilder.into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @androidx.annotation.Nullable Transition<? super Drawable> transition) {
                symbolMap.put(symbolName, resource);
                Log.d(TAG, "Symbol downloaded and cached: " + symbolName);
            }

            @Override
            public void onLoadCleared(@androidx.annotation.Nullable Drawable placeholder) {
                Log.d(TAG, "Symbol download cleared: " + symbolName);
            }

            @Override
            public void onLoadFailed(@androidx.annotation.Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                Log.e(TAG, "Failed to download symbol: " + symbolName);
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