package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
    private Paint mPaint = new Paint();

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制点
        canvas.drawPoint(200, 200, mPaint);
        canvas.drawPoints(new float[]{500, 500, 500, 600, 500, 700}, mPaint);

        //绘制直线
        canvas.drawLine(300, 300, 500, 600, mPaint);// 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLines(new float[]{100, 200, 200, 200,
                100, 300, 200, 300}, mPaint);

        //绘制矩形
        //两者最大的区别就是精度不同，Rect是int(整形)的，而RectF是float(单精度浮点型)的
        canvas.drawRect(100, 100, 800, 400, mPaint);
        // 第二种
        Rect rect = new Rect(100, 200, 800, 500);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(rect, mPaint);
        // 第三种
        RectF rectF = new RectF(100, 300, 800, 600);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rectF, mPaint);

        //绘制圆角矩形
        RectF roundRectF = new RectF(100,800,800,1000);
        mPaint.setColor(Color.RED);
        canvas.drawRoundRect(roundRectF,30,30,mPaint);
    }

}
