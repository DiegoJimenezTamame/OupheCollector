package local.ouphecollector.views;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import local.ouphecollector.models.CardSymbol;

public class SymbolManager {
    private static final String TAG = "SymbolManager";
    private Map<String, CardSymbol> symbolMap;
    private boolean initialized = false;
    private List<SymbolManagerCallback> callbacks = new ArrayList<>();
    private static SymbolManager instance;

    public interface SymbolManagerCallback {
        void onSymbolsLoaded();
    }

    private SymbolManager() {
        symbolMap = new HashMap<>();
    }

    public static synchronized SymbolManager getInstance() {
        if (instance == null) {
            instance = new SymbolManager();
        }
        return instance;
    }

    public void initialize(Context context, SymbolManagerCallback callback) {
        if (initialized) {
            Log.d(TAG, "SymbolManager already initialized");
            callback.onSymbolsLoaded();
            return;
        }
        Log.d(TAG, "initialize called");
        new Thread(() -> {
            try {
                loadSymbolsFromAssets(context);
                initialized = true;
                notifySymbolsLoaded();
            } catch (IOException e) {
                Log.e(TAG, "Error loading symbols", e);
            }
        }).start();
    }

    private void loadSymbolsFromAssets(Context context) throws IOException {
        String[] symbolNames = context.getAssets().list("symbols");
        if (symbolNames != null) {
            for (String symbolName : symbolNames) {
                String symbolKey = symbolName.substring(0, symbolName.lastIndexOf('.'));
                InputStream inputStream = context.getAssets().open("symbols/" + symbolName);
                String svgString = convertStreamToString(inputStream);
                inputStream.close();
                CardSymbol cardSymbol = new CardSymbol();
                cardSymbol.setSvg_uri("data:image/svg+xml;base64," + android.util.Base64.encodeToString(svgString.getBytes(), android.util.Base64.DEFAULT));
                symbolMap.put(symbolKey, cardSymbol);
            }
        } else {
            Log.e(TAG, "No symbols found in assets/symbols");
        }
    }

    public void addSymbol(CardSymbol symbol) {
        if (symbol != null && symbol.getSymbol() != null && symbol.getSvg_uri() != null) {
            symbolMap.put(symbol.getSymbol(), symbol);
        } else {
            Log.e(TAG, "Error adding symbol: symbol or symbolKey or svgUri is null");
        }
    }

    public CardSymbol getSymbol(String symbol) {
        if (symbolMap.containsKey(symbol)) {
            return symbolMap.get(symbol);
        } else {
            Log.e(TAG, "Symbol not found: " + symbol);
            return null;
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void addCallback(SymbolManagerCallback callback) {
        callbacks.add(callback);
    }

    private void notifySymbolsLoaded() {
        for (SymbolManagerCallback callback : callbacks) {
            callback.onSymbolsLoaded();
        }
        callbacks.clear();
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}