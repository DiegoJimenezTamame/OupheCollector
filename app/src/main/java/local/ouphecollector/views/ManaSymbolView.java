package local.ouphecollector.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;

import local.ouphecollector.R;
import local.ouphecollector.models.CardSymbol;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import local.ouphecollector.api.RetrofitClient;


public class ManaSymbolView extends AppCompatImageView {
    private static final String TAG = "ManaSymbolView";

    public ManaSymbolView(Context context) {
        super(context);
        init();
    }

    public ManaSymbolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ManaSymbolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int width = 64;
        int height = 64;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        setLayoutParams(params);
    }

    public void setSymbol(String symbol) {
        Log.d(TAG, "setSymbol called for: " + symbol);
        CardSymbol cardSymbol = SymbolManager.getSymbol(symbol);
        if (cardSymbol != null) {
            String svgUri = cardSymbol.getSvg_uri();
            Log.d(TAG, "Symbol found: " + symbol + ", SVG URI: " + svgUri);
            // Load the SVG using Glide
            Glide.with(getContext())
                    .load(svgUri)
                    .into(this);
        } else {
            Log.e(TAG, "Symbol not found: " + symbol);
        }
    }

    private void loadSvgFromUrl(String svgUrl) {
        Call<ResponseBody> call = RetrofitClient.getRetrofitInstance().create(local.ouphecollector.api.CardApiService.class).getSvg(svgUrl);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody == null) {
                            Log.e(TAG, "Response body is null for SVG: " + svgUrl);
                            setImageResource(R.drawable.no_image);
                            return;
                        }
                        try (InputStream inputStream = responseBody.byteStream()) {
                            SVG svg = SVG.getFromInputStream(inputStream);
                            Bitmap bitmap = Bitmap.createBitmap((int) svg.getDocumentWidth(), (int) svg.getDocumentHeight(), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(bitmap);
                            svg.renderToCanvas(canvas);
                            setImageDrawable(new BitmapDrawable(getContext().getResources(), bitmap));
                        } catch (SVGParseException e) {
                            Log.e(TAG, "Error parsing SVG: " + svgUrl, e);
                            setImageResource(R.drawable.no_image);
                        } catch (IOException e) {
                            Log.e(TAG, "Error loading SVG: " + svgUrl, e);
                            setImageResource(R.drawable.no_image);
                        } catch (Exception e) {
                            Log.e(TAG, "Unexpected error loading SVG: " + svgUrl, e);
                            setImageResource(R.drawable.no_image);
                        }
                    }
                } else {
                    Log.e(TAG, "Failed to download SVG: " + svgUrl);
                    setImageResource(R.drawable.no_image);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, "Error downloading SVG: " + svgUrl, t);
                setImageResource(R.drawable.no_image);
            }
        });
    }

    private void loadPlaceholder(String placeholderUri) {
        try (InputStream inputStream = getContext().getContentResolver().openInputStream(Uri.parse(placeholderUri))) {
            if (inputStream == null) {
                Log.e(TAG, "Failed to open input stream for placeholder: " + placeholderUri);
                setImageResource(R.drawable.no_image);
                return;
            }
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            setImageDrawable(drawable);
        } catch (IOException e) {
            Log.e(TAG, "Error loading placeholder: " + placeholderUri, e);
            setImageResource(R.drawable.no_image);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error loading placeholder: " + placeholderUri, e);
            setImageResource(R.drawable.no_image);
        }
    }
}