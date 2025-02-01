package local.ouphecollector.views;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;

public class SvgDataFetcher implements DataFetcher<SVG> {
    private static final String TAG = "SvgDataFetcher";
    private final InputStream stream;

    public SvgDataFetcher(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public void loadData(@NonNull Priority priority, DataCallback<? super SVG> callback) {
        try {
            SVG svg = SVG.getFromInputStream(stream);
            callback.onDataReady(svg);
        } catch (SVGParseException e) {
            Log.e(TAG, "Error parsing SVG", e);
            callback.onLoadFailed(e);
        }
    }

    @Override
    public void cleanup() {
        try {
            stream.close();
        } catch (IOException e) {
            Log.e(TAG, "Error closing stream", e);
        }
    }

    @Override
    public void cancel() {
        // Nothing to do here
    }

    @NonNull
    @Override
    public Class<SVG> getDataClass() {
        return SVG.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }
}