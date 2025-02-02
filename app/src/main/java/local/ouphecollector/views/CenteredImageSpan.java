package local.ouphecollector.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class CenteredImageSpan extends ImageSpan {
    private Drawable drawable;

    public CenteredImageSpan(View view) {
        super(getDrawableFromView(view));
        this.drawable = getDrawableFromView(view);
    }

    private static Drawable getDrawableFromView(View view) {
        if (view instanceof ManaSymbolView) {
            return ((ManaSymbolView) view).getDrawable();
        }
        return null;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        if (drawable == null) {
            return;
        }
        canvas.save();
        int transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public Drawable getDrawable() {
        if (drawable != null) {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            drawable.setBounds(0, 0, width, height);
            return drawable;
        }
        return super.getDrawable();
    }
}