package com.returntolife.jjcode.mydemolist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.airbnb.lottie.animation.content.Content;
import com.tools.jj.tools.utils.Dp2PxUtil;

/**
 * Created by HeJiaJun on 2019/2/25.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class MyView extends View {

    private Context mContext;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resultWidth;
        int resultHeight;

        int specWMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWSize = MeasureSpec.getSize(widthMeasureSpec);
        resultWidth=myMeasure(specWMode,specWSize, Dp2PxUtil.dip2px(mContext,200));

        int specHMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHSize = MeasureSpec.getSize(heightMeasureSpec);

        resultHeight=myMeasure(specHMode,specHSize,Dp2PxUtil.dip2px(mContext,300));
        setMeasuredDimension(resultWidth,resultHeight);
    }

    /**
     *
     * @param specMode 测量模式
     * @param specSize 测量大小
     * @param result  在非精确测量模式中用来约束的大小
     * @return
     */
    private  int myMeasure(int specMode,int specSize,int result){
        if(specMode==MeasureSpec.EXACTLY){
            result=specSize;
        }else if(specMode==MeasureSpec.AT_MOST){
            result=Math.min(specSize,result);
        }else {

        }
        return result;
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
