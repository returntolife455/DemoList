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

    private int width, height;
    private int maxLineHeight, midLineHeight, minLineHeight;            //长、中、短刻度的高度
    private int lineWidth = 24;                                         //刻度宽度
    private int startNum = 0, endNum = 100;                              //刻度范围
    private int lineSpacing;                                            //刻度间隔
    @ColorInt
    private int startColor = Color.parseColor("#ff3415b0");
    @ColorInt
    private int endColor = Color.parseColor("#ffcd0074");
    @ColorInt
    private int indicatorColor = startColor;                            //指示器颜色

    private Paint scalePaint;                                           //刻度画笔
    private Paint indicatorPaint;                                       //指示器画笔
    private int curScale = 20;                                          //当前刻度
    private Scroller mScroller;
    private VelocityTracker velocityTracker;
    private int minVelocityX;
    private int offsetStart;
    private int maxWidth;
    private OnSelectNumListener listener;

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
        scalePaint.setStrokeCap(Paint.Cap.ROUND);
        scalePaint.setStrokeWidth(lineWidth);
        scalePaint.setTextSize(70);
        scalePaint.setTypeface(Typeface.DEFAULT_BOLD);

        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStrokeCap(Paint.Cap.ROUND);
        indicatorPaint.setStrokeWidth(lineWidth);
        indicatorPaint.setTextSize(70);
        indicatorPaint.setTypeface(Typeface.DEFAULT_BOLD);
        indicatorPaint.setColor(indicatorColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
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
        canvas.translate(lineSpacing + width / 6, height / 2);
        int scaleCount = endNum - startNum + 1;
        maxWidth = (endNum - startNum) * (lineSpacing + lineWidth) - width / 3 + lineSpacing;
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
        indicatorColor=ColorUtil.getColor(startColor, endColor, (float) curScale / scaleCount);
        indicatorPaint.setColor(indicatorColor);
        canvas.restore();
    }

    /**
     * 绘制指示器
     *
     * @param canvas
     */
    private void drawIndicator(Canvas canvas) {
        canvas.save();
        canvas.translate(width / 2, height / 3);
        canvas.drawCircle(0, 50, 10, indicatorPaint);
        float curScaleTextWidth = scalePaint.measureText(curScale + " cm");
        canvas.drawText(curScale + " cm", -curScaleTextWidth / 2, 0, indicatorPaint);
        canvas.restore();
    }

    int getSelectedNum() {
        return (int) (Math.abs((lineSpacing - width / 3 + offsetStart + moveX)) / (lineWidth + lineSpacing));
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
                moveX = (moveX / (lineSpacing + lineWidth)) * (lineWidth + lineSpacing);
                if (offsetStart + moveX > width / 3) {
                    offsetStart = width / 3;
                    moveX = 0;
                } else if (offsetStart + moveX < -maxWidth) {
                    offsetStart = -maxWidth;
                    moveX = 0;
                }
                curScale = getSelectedNum();
                if (listener != null) listener.onSelectNum(curScale);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (offsetStart + moveX > width / 3) {
                    offsetStart = width / 3;
                } else if (offsetStart + moveX < -maxWidth) {
                    offsetStart = -maxWidth;
                } else {
                    offsetStart += moveX;
                    offsetStart = (offsetStart / (lineSpacing + lineWidth)) * (lineWidth + lineSpacing);
                }
                moveX = 0;
                curScale = getSelectedNum();
                if (listener != null) listener.onSelectNum(curScale);
                //计算当前手指放开时的滑动速率
                velocityTracker.computeCurrentVelocity(300); //越小滑动距离越远
                float velocityX = velocityTracker.getXVelocity();
                if (Math.abs(velocityX) > minVelocityX) {
                    mScroller.fling(0, 0, (int) velocityX, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                }
                postInvalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            if (mScroller.getCurrX() == mScroller.getFinalX()) {//滑动停止，磁吸效果和边界控制
                if (offsetStart + moveX > width / 3) {
                    offsetStart = width / 3;
                } else if (offsetStart + moveX < -maxWidth) {
                    offsetStart = -maxWidth;
                } else {
                    offsetStart += moveX;
                    offsetStart = (offsetStart / (lineSpacing + lineWidth)) * (lineWidth + lineSpacing);
                }
                moveX = 0;
            } else { //继续惯性滑动
                moveX = mScroller.getCurrX() - mScroller.getStartX();
                //滑动结束:边界控制
                if (offsetStart + moveX > width / 3) {
                    moveX = 0;
                    offsetStart = width / 3;
                    mScroller.forceFinished(true);
                } else if (offsetStart + moveX < -maxWidth) {
                    offsetStart = -maxWidth;
                    moveX = 0;
                    mScroller.forceFinished(true);
                }
                offsetStart += moveX;
                offsetStart = (offsetStart / (lineSpacing + lineWidth)) * (lineWidth + lineSpacing);
            }
        } else {
            if (offsetStart + moveX >= width / 3) {
                offsetStart = width / 3;
                moveX = 0;
            }
        }
        curScale = getSelectedNum();
        if (listener != null) listener.onSelectNum(curScale);
        postInvalidate();
    }

    private interface OnSelectNumListener {
        void onSelectNum(int num);
    }

    public OnSelectNumListener getListener() {
        return listener;
    }
}
