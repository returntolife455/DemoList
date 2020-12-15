package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View {
    private Paint mPaint = new Paint();
    private int mWidth, mHeight;                //View 宽高
    private int radius;                         //半径
    private Path path = new Path();             //路径
    private int level = 5;                          //等级数

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        radius = Math.min(mWidth / 2, mHeight / 2) / level;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.translate(mWidth / 2, mHeight / 2);
        drawPolygon(canvas);
        drawLines(canvas);
    }

    /**
     * 多边形
     *
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        int curLevel = 1;
        while (curLevel <= level) {
            int curR = radius * curLevel;
            float y = (float) Math.sqrt(3) * curR / 2;
            float x = curR / 2;
            path.reset();
            path.moveTo(-x, y);
            path.lineTo(x, y);
            path.lineTo(curR, 0);
            path.lineTo(x, -y);
            path.lineTo(-x, -y);
            path.lineTo(-curR, 0);
            path.close();
            canvas.drawPath(path, mPaint);
            curLevel++;
        }
    }

    /**
     * 画交叉线
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        int curR = radius * level;
        float y = (float) Math.sqrt(3) * curR / 2;
        float x = curR / 2;
        canvas.save();
        for (int i = 0; i < 6; i++) {
            path.reset();
            path.moveTo(0, 0);
            path.lineTo(-x, y);
            canvas.drawPath(path, mPaint);
            canvas.rotate(60);
        }
        canvas.restore();
    }
}
