package local.ouphecollector;

import android.app.Application;
import android.util.Log;

import local.ouphecollector.views.SymbolManager;

public class OupheCollectorApplication extends Application {
    private static final String TAG = "OupheCollectorApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate called");
        SymbolManager.getInstance(this);
    }
}