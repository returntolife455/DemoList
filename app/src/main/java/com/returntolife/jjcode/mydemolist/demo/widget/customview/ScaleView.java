package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.returntolife.jjcode.mydemolist.demo.widget.scaleview.ColorUtil;

public class ScaleView extends View {
    private static final String TAG = ScaleView.class.getSimpleName();

    private int with, height;
    private int maxLineHeight, midLineHeight, minLineHeight;            //长、中、短刻度的高度
    private int lineWidth = 24;                                         //刻度宽度
    private int startNum = 0, endNum = 50;                              //刻度范围
    private int lineSpacing;                                            //刻度间隔
    @ColorInt
    private int startColor = Color.parseColor("#ff3415b0");
    @ColorInt
    private int endColor = Color.parseColor("#ffcd0074");
    @ColorInt
    private int indicatorColor = startColor;                            //指示器颜色

    private Paint scalePaint;                                           //刻度画笔
    private int curScale = 20;                                          //当前刻度
    private Scroller mScroller;
    private VelocityTracker velocityTracker;
    private int minVelocityX;
    private int offsetStart;

    public ScaleView(Context context) {
        this(context, null);
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
        minVelocityX = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        initView();
    }

    private void initView() {
        scalePaint = new Paint();
        scalePaint.setAntiAlias(true);
        scalePaint.setColor(Color.RED);
        scalePaint.setStrokeCap(Paint.Cap.ROUND);
        scalePaint.setStrokeWidth(lineWidth);
        scalePaint.setTextSize(70);
        scalePaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        with = w;
        height = h;
        lineSpacing = 24;
        maxLineHeight = 150;
        midLineHeight = 110;
        minLineHeight = 60;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
        drawIndicator(canvas);
    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(with / 6, height / 2);
        int scaleCount = endNum - startNum + 1;
        for (int i = 0; i < scaleCount; i++) {
            int lineHeight = minLineHeight;
            scalePaint.setColor(ColorUtil.getColor(startColor, endColor, (float) i / scaleCount));
            if (i % 10 == 0) {
                lineHeight = maxLineHeight;
                //绘制刻度文字
                float textWidth = scalePaint.measureText("" + i);
                canvas.drawText("" + i, offsetStart + moveX + i * (lineSpacing + lineWidth) - textWidth / 2, lineHeight + 70, scalePaint);
            } else if (i % 5 == 0) {
                lineHeight = midLineHeight;
            }
            canvas.drawLine(offsetStart + moveX + i * (lineSpacing + lineWidth), 0, offsetStart + moveX + i * (lineSpacing + lineWidth), lineHeight, scalePaint);
        }
        canvas.restore();
    }

    /**
     * 绘制指示器
     *
     * @param canvas
     */
    private void drawIndicator(Canvas canvas) {
        canvas.save();
        canvas.translate(with / 2, height / 3);
        canvas.drawCircle(0, 50, 10, scalePaint);
        float curScaleTextWidth = scalePaint.measureText(curScale + " cm");
        canvas.drawText(curScale + " cm", -curScaleTextWidth / 2, 0, scalePaint);
        canvas.restore();
    }

    private float downX;
    private float moveX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScroller.forceFinished(true);
                downX = event.getX();
                moveX = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX() - downX;
                if (offsetStart + moveX > with / 3) {
                    moveX = with / 3 - offsetStart;
                }
                Log.d(TAG, "onTouchEvent: moveX" + moveX);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                //计算当前手指放开时的滑动速率
                if (offsetStart + moveX > with / 3) {
                    offsetStart = with / 3;
                } else {
                    offsetStart += moveX;
                }
                moveX = 0;
                postInvalidate();
                break;
        }
        return true;
    }
}