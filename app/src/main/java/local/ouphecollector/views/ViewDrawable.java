package local.ouphecollector.views;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.MeasureSpec;

import androidx.annotation.NonNull;

public class ViewDrawable extends Drawable {
    private final View view;

    public ViewDrawable(View view) {
        this.view = view;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        view.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        // Not supported
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // Not supported
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        // Use MeasureSpec to create a measurement specification
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        // Use MeasureSpec to create a measurement specification
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        view.layout(0, 0, right - left, bottom - top);
    }
}