package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.returntolife.jjcode.mydemolist.R;

public class PasswordView extends AppCompatEditText {
    private static final String TAG = PasswordView.class.getSimpleName();
    private int width, height;                              //View高宽
    private int pwdCount;                                   //密码最大长度
    private RectF rectF;                                    //外边框
    private int roundRect;                                  //边框圆角
    private Paint paint;                                    //边框画笔
    private int radius;                                     //密码圆点大小
    private Paint circlePaint;                              //密码圆点画笔
    private int bgStrokeWidth;                              //边框宽度
    private int textLength;                                 //输入密码长度
    private int bgColor;                                    //背景颜色
    private int lineColor;                                  //边框颜色
    private int circleColor;                                //密码圆点颜色
    private onInputListener listener;
    private boolean isShowCursor;                           //是否显示光标

    public void setListener(onInputListener listener) {
        this.listener = listener;
    }

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView();
        setBackgroundColor(Color.TRANSPARENT);
        setCursorVisible(false);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView);
        bgColor = typedArray.getColor(R.styleable.PasswordView_bg_color, Color.WHITE);
        lineColor = typedArray.getColor(R.styleable.PasswordView_line_color, Color.BLACK);
        circleColor = typedArray.getColor(R.styleable.PasswordView_circle_color, Color.BLACK);
        radius = (int) typedArray.getDimension(R.styleable.PasswordView_circle_radius, 10);
        roundRect = (int) typedArray.getDimension(R.styleable.PasswordView_corners, 10);
        pwdCount = typedArray.getInteger(R.styleable.PasswordView_pwd_count, 6);
        bgStrokeWidth = (int) typedArray.getDimension(R.styleable.PasswordView_stroke_width, 2);
        isShowCursor = typedArray.getBoolean(R.styleable.PasswordView_is_show_cursor, false);
        typedArray.recycle();
    }

    private void initView() {
        rectF = new RectF();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(bgStrokeWidth);
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setColor(circleColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w - bgStrokeWidth * 2;
        height = h - bgStrokeWidth * 2;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        textLength = text.length();
        if (textLength > pwdCount) {
            return;
        }
        if (textLength == pwdCount) {
            if (listener != null) {
                listener.onFinish(text.toString());
            }
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawColor(bgColor);
        rectF.left = bgStrokeWidth;
        rectF.right = width - bgStrokeWidth;
        rectF.top = bgStrokeWidth;
        rectF.bottom = height - bgStrokeWidth;
        canvas.drawRoundRect(rectF, roundRect, roundRect, paint);
        drawDivider(canvas);
        drawPwdCircle(canvas);
    }

    private void drawDivider(Canvas canvas) {
        float space = width / pwdCount;
        for (int i = 1; i < pwdCount; i++) {
            canvas.drawLine(space * i, bgStrokeWidth, space * i, height - bgStrokeWidth, paint);
        }
    }

    /**
     * 绘制圆点
     *
     * @param canvas
     */
    private void drawPwdCircle(Canvas canvas) {
        float space = width / pwdCount;
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(space * i + space / 2, height / 2, radius, circlePaint);
        }
        if (isShowCursor) { //是否显示光标
            canvas.drawLine(space * textLength + space / 2, height / 4, space * textLength + space / 2, height / 4 * 3, paint);
        }
    }

    public interface onInputListener {
        void onFinish(String text);
    }
}
