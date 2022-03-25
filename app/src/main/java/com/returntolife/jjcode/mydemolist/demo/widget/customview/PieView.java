package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PieView extends View {
    private Paint mPaint = new Paint();
    private int width, height;
    private List<PieData> dataList = new ArrayList<>();
    private int radius;                                         //半径
    private int[] color = new int[]{R.color.lineColor_click, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent, R.color.lineColor_unclick};
    private RectF circle;
    private int curAngle;
    private Random random;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        random = new Random();
        initPaint();
    }

    private void initPaint() {
        mPaint.setAntiAlias(true);
    }

    public void updateData(List<PieData> dataList) {
        if (dataList != null) {
            this.dataList = dataList;
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.radius = Math.min(getMeasuredHeight(), getMeasuredWidth()) / 2;
        circle = new RectF(-radius, -radius, radius, radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, radius, mPaint);
        curAngle = 0;
        for (PieData data : dataList) {
            if (data.getValue() == 0) {
                continue;
            }
            mPaint.setColor(getResources().getColor(color[random.nextInt(color.length)]));
            mPaint.setStyle(Paint.Style.FILL);
            int percentage = (int) ((data.getValue() / 100) * 360);
            canvas.drawArc(circle, curAngle, percentage, true, mPaint);
            curAngle += percentage;
        }
    }
}