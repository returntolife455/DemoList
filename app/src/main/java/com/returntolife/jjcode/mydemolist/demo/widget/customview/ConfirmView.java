package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class ConfirmView extends View {
    private static final String TAG = ConfirmView.class.getSimpleName();
    private int radius;                         //圆半径
    private int width;                          //View 宽度
    private int height;                         //View 高度
    private RectF rect;                         //初始矩形
    private Paint paint;                        //背景画笔
    private Paint textPaint;                    //文字画笔
    private int circleAngle;                    //圆角
    private ValueAnimator set_rect_to_angle_animator;
    private String text = "确认完成";           //按钮文案
    private ValueAnimator set_rect_to_circle_animator;
    private int distance;                       //View 动态宽度
    private ObjectAnimator animator_move_to_up;
    private Path okPath;
    private Path copyOkPath;
    private Paint okPaint;
    private PathMeasure pathMeasure;
    private ValueAnimator set_ok_animation;

    public ConfirmView(Context context) {
        this(context, null);
    }

    public ConfirmView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConfirmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rect = new RectF();
        paint = new Paint();
        paint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        okPaint = new Paint();
        okPaint.setAntiAlias(true);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setColor(Color.WHITE);
        okPath = new Path();
        copyOkPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height);
        drawRoundRect(canvas);
        drawText(canvas);
        canvas.drawPath(copyOkPath, okPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        radius = 50;
        distance = width / 2;
        initOK();
        set_rect_to_angle_animation();
        set_rect_to_circle_animation();
        set_circle_translationY();
        set_ok_animation();
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        rect.left = -distance;
        rect.right = distance;
        rect.top = -2 * radius;
        rect.bottom = 0;
        paint.setColor(Color.RED);
        canvas.drawRoundRect(rect, circleAngle, circleAngle, paint);
    }

    /**
     * 绘制文章
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        textPaint.setTextSize(40);
        float textWidth = textPaint.measureText(text);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(text, -textWidth / 2, -radius + distance, textPaint);
    }

    private void initOK() {
        okPaint.setStrokeWidth(10);
        okPath.moveTo(-radius + 10, -radius - 5);
        okPath.lineTo(-5, -radius + 20);
        okPath.lineTo(radius - 15, -(float) (1.5 * radius));
        pathMeasure = new PathMeasure(okPath, false);
    }

    /**
     * 开始动画
     */
    public void confirm() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(set_rect_to_angle_animator,
                set_rect_to_circle_animator,
                animator_move_to_up,
                set_ok_animation);
        animatorSet.start();
    }

    private void set_rect_to_angle_animation() {
        set_rect_to_angle_animator = ValueAnimator.ofInt(0, radius);
        set_rect_to_angle_animator.setDuration(500);
        set_rect_to_angle_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    private void set_rect_to_circle_animation() {
        set_rect_to_circle_animator = ValueAnimator.ofInt(width / 2, radius);
        set_rect_to_circle_animator.setDuration(800);
        set_rect_to_circle_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                distance = (int) animation.getAnimatedValue();
                float result = animation.getAnimatedFraction();
                int alpha = (int) (255 - result * 255);
                textPaint.setAlpha(alpha);
                invalidate();
            }
        });
    }

    /**
     * 网上移
     */
    private void set_circle_translationY() {
        final float curTranslationY = this.getTranslationY();
        animator_move_to_up = ObjectAnimator.ofFloat(this, "translationY", curTranslationY, -height + 2 * radius);
        animator_move_to_up.setDuration(500);
        animator_move_to_up.setInterpolator(new AccelerateDecelerateInterpolator());
        animator_move_to_up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
            }
        });
    }

    /**
     * 钩子动画
     */
    private void set_ok_animation() {
        set_ok_animation = ValueAnimator.ofFloat(0.0f, 1.0f);
        set_ok_animation.setDuration(500);
        set_ok_animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float per = (float) animation.getAnimatedValue();
                float length = pathMeasure.getLength();
                pathMeasure.getSegment(0, length * per, copyOkPath, true);
                invalidate();
            }
        });
    }
}
