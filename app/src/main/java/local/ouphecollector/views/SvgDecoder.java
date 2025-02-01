package local.ouphecollector.views;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decodes an SVG internal representation from an {@link InputStream}.
 */
public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {
    private static final String TAG = "SvgDecoder";

    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) {
        // TODO: Can we tell if it's an SVG?
        return true;
    }

    @Override
    public Resource<SVG> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            return new SimpleResource<>(svg);
        } catch (SVGParseException ex) {
            Log.e(TAG, "Cannot load SVG from stream", ex);
            throw new IOException("Cannot load SVG from stream", ex);
        }
    }

    /**
     * Factory for creating {@link SvgDecoder}s.
     */
    public static class SvgDecoderFactory implements ModelLoaderFactory<InputStream, SVG> {

        @NonNull
        @Override
        public ModelLoader<InputStream, SVG> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new ModelLoader<>() {
                @Override
                public boolean handles(@NonNull InputStream inputStream) {
                    return true;
                }

                @NonNull
                @Override
                public LoadData<SVG> buildLoadData(@NonNull InputStream inputStream, int width, int height, @NonNull Options options) {
                    return new LoadData<>(new com.bumptech.glide.signature.ObjectKey(inputStream), new SvgDataFetcher(inputStream));
                }
            };
        }

        @Override
        public void teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}