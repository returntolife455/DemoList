package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.returntolife.jjcode.mydemolist.R;

public class ConfirmView extends View {
    private static final String TAG = ConfirmView.class.getSimpleName();
    private int radius;                                         //圆半径
    private int width;                                          //View 宽度
    private int height;                                         //View 高度
    private int bgColor;                                        //背景颜色
    private RectF rect;                                         //初始矩形
    private Paint paint;                                        //背景画笔
    private Paint textPaint;                                    //文字画笔
    private float textSize;                                     //文字大小
    private int circleAngle;                                    //圆角
    private String text;                                        //按钮文案
    private int distance;                                       //View 动态宽度
    private Path okPath;                                        //绘制OK路径
    private Path copyOkPath;                                    //OK路径片段
    private Paint okPaint;                                      //绘制OK的画笔
    private PathMeasure pathMeasure;
    private ValueAnimator set_rect_to_angle_animator;
    private ValueAnimator set_rect_to_circle_animator;
    private ObjectAnimator animator_move_to_up;
    private ValueAnimator set_ok_animation;
    private boolean isStart;
    private onStatusListener listener;

    public void setListener(onStatusListener listener) {
        this.listener = listener;
    }

    public ConfirmView(Context context) {
        this(context, null);
    }

    public ConfirmView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConfirmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ConfirmView);
        radius = (int) array.getDimensionPixelSize(R.styleable.ConfirmView_radius, 50);
        text = array.getString(R.styleable.ConfirmView_text);
        textSize = array.getDimensionPixelSize(R.styleable.ConfirmView_text_size, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        bgColor = array.getColor(R.styleable.ConfirmView_bg_color, Color.RED);
        array.recycle();
    }

    private void initView() {
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
        paint.setColor(bgColor);
        canvas.drawRoundRect(rect, circleAngle, circleAngle, paint);
    }

    /**
     * 绘制文章
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        textPaint.setTextSize(textSize);
        float textWidth = textPaint.measureText(text);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(text, -textWidth / 2, -radius + distance, textPaint);
    }

    private void initOK() {
        okPaint.setStrokeWidth(10);
        okPath.moveTo(-(float) (0.7 * radius), -(float) (0.9 * radius));
        okPath.lineTo(-5, -(float) (radius * 0.5));
        okPath.lineTo((float) (0.7 * radius), -(float) (1.4 * radius));
        pathMeasure = new PathMeasure(okPath, false);
    }

    /**
     * 开始动画
     */
    public void confirm() {
        if (isStart) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(set_rect_to_angle_animator,
                set_rect_to_circle_animator,
                animator_move_to_up,
                set_ok_animation);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onFinish();
                }
            }
        });
        isStart = true;
    }

    private void set_rect_to_angle_animation() {
        set_rect_to_angle_animator = ValueAnimator.ofInt(0, radius);
        set_rect_to_angle_animator.setDuration(200);
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
        set_rect_to_circle_animator.setDuration(300);
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
        animator_move_to_up.setDuration(300);
        animator_move_to_up.setInterpolator(new AccelerateDecelerateInterpolator());
        animator_move_to_up.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

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

    public interface onStatusListener {
        void onFinish();
    }

    /**
     * 重置动画
     */
    public void reset() {
        isStart = false;
        textPaint.setAlpha(255);
        setTranslationY(0);
        distance = width / 2;
        circleAngle = 0;
        copyOkPath.reset();
        invalidate();
    }
}
