package com.returntolife.jjcode.mydemolist.demo.widget.adrecyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

public class AdImageViewVersion1 extends AppCompatImageView {
    private static final String TAG = "AdImageViewVersion1";
    private RectF mBitmapRectF;
    private Bitmap mBitmap;
    private int mMinDy;
    private int mDy;

    public AdImageViewVersion1(Context context) {
        this(context, null);
    }

    public AdImageViewVersion1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDy(int dy) {
        if (getDrawable() == null) {
            return;
        }
        mDy = dy - mMinDy;
        if (mDy <= 0) {
            mDy = 0;
        }
        if (mDy > mBitmapRectF.height() - mMinDy) {
            mDy = (int) (mBitmapRectF.height() - mMinDy);
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDy = h;
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        mBitmap = drawableToBitmap(drawable);
        mBitmapRectF = new RectF(0, 0, w, mBitmap.getHeight() * w / mBitmap.getWidth());
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap == null) {
            return;
        }
        canvas.save();
        canvas.translate(0, -mDy);
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        canvas.restore();
    }
}
