package local.ouphecollector;

import android.app.Application;
import android.util.Log;

import local.ouphecollector.views.SymbolManager;

public class OupheCollectorApplication extends Application {
    private static final String TAG = "OupheCollectorApp";
    private SymbolManager symbolManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate called");
        symbolManager = SymbolManager.getInstance();
        symbolManager.initialize(this, () -> Log.d(TAG, "Symbols loaded"));
    }

    public SymbolManager getSymbolManager() {
        return symbolManager;
    }
}