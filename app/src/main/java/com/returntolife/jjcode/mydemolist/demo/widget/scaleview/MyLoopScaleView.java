package com.returntolife.jjcode.mydemolist.demo.widget.scaleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiajun He on 2019/4/9.
 * des:自定义调频控件
 * version:1.0.5
 *
 */

public class MyLoopScaleView extends View {
    private final static String TAG = MyLoopScaleView.class.getSimpleName();


    //滑动到左右边缘的标识
    private static final int EDGE_LEFT = -1;
    private static final int EDGE_RIGHT = 1;

    //handler事件处理标识
    private static final int HANDLER_FLAG_CHECK_EDG = 0;
    //惯性滑动
    private static final int HANDLER_FLAG_FING=1;


    //尺子控件总宽度
    private float viewWidth;
    //尺子控件总宽度
    private float viewHeight;


    //一个刻度表示的值的大小
    private float oneItemValue;
    //刻度表的最大值
    private int maxValue;
    //刻度表的最小值
    private int minValue;

    //设置屏幕宽度内最多显示的大刻度数
    private int showItemSize = 14;
    //设置刻度线间宽度,大小由 showItemSize确定
    private int scaleDistance;
    //刻度线的数量
    private int scaleItemCount;
    //大刻度的间距
    private final int spaceScaleCount=5;

    //刻度线宽
    private int scaleWidth = 5;
    //刻度高度
    private int scaleHeight = 20;
    //刻度文字的大小
    private int scaleTextSize = 48;
    //文字和刻度的间隙高度
    private int scaleTextSpaceHeight = 40;

    //刻度的颜色
    private int scaleColor = Color.GRAY;
    //刻度的高亮颜色
    private int scaleHighlightColor= Color.GRAY;
    //刻度文字的颜色
    private int scaleTextColor = Color.GRAY;
    //游标颜色
    private int currsorStartColor=scaleColor;
    private int currsorEndColor= Color.RED;


    //标尺值开始位置,可能理解为下方数值绘制的偏移量
    private float valueLocation = 0;
    //游标位置,(第几个刻度)
    private int currsorPos = 0;
    //游标的坐标位置
    private float currsorLocation;
    //游标前一次的位置
    private int lastCurrsorPos = -1;
    //游标固定位置
    private int currsorTargetPos;
    //由自动滑动回到固定位置所产生的偏移，用于矫正刻度高亮的位置
    private int currsorPosDiff;
    //点与游标中心位置的偏移量
    private float pointLocationDiff;
    //游标高亮位置突出的刻度的信息，（绘制位置，绘制颜色）
    private float[][] currsorGradientScaleInfo;

    //游标左右两边边缘事件触发的位置
    private final int currosrEdgeRight=showItemSize*spaceScaleCount-4;
    private final int currosrEdgeLeft=4;

    //游标左右两边位置，以及范围
    private int gradientLeftPos;
    private int gradientRightPos;
    //游标中心与最左/右的距离
    private int currsorGradientSize=6;

    //游标的pos指向的值
    private float currsorValue;

    private Paint paint;
    private Paint paintText;
    private Rect textRect;

    //手势解析器
    private GestureDetector mGestureDetector;
    //处理惯性滚动
    //private Scroller mScroller;

    private OnValueChangeListener onValueChangeListener;

    //回弹到目标位置动画的最大时长
    private int animatDurationResilience = 2000;
    //边缘滑动动画时长
    private int animatDurationEdge = 800;
    //滑动左右两边的时候响应检查滚动的时长
    private int handlerDelayTime = 500;
    //滚动到指定数值的动画最大时长
    private int animatDurationScrollToValue=1000;
    private List<Animator> animatorPlayingList;

    //是否正在播放动画，播放动画限制滑动
    private boolean isPlayAnimation = false;

    //是否是FM调频
    private boolean isFM=true;

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public MyLoopScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyLoopScaleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mGestureDetector = new GestureDetector(context, gestureListener);
     //   mScroller=new Scroller(context);


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(scaleWidth);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        textRect = new Rect();



        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyLoopScaleView);
        isFM = ta.getBoolean(R.styleable.MyLoopScaleView_isfm, isFM);

        scaleTextColor=ta.getColor(R.styleable.MyLoopScaleView_scaleTextColor,scaleTextColor);
        scaleTextSize=ta.getInteger(R.styleable.MyLoopScaleView_scaleTextSize,scaleTextSize);
        scaleTextSpaceHeight=ta.getInteger(R.styleable.MyLoopScaleView_scaleTextTopSpace,scaleTextSpaceHeight);

        scaleColor=ta.getColor(R.styleable.MyLoopScaleView_scaleItemColor,scaleColor);
        scaleHighlightColor=ta.getColor(R.styleable.MyLoopScaleView_scaleItemHighlightColor,scaleHighlightColor);
        scaleHeight=ta.getInt(R.styleable.MyLoopScaleView_scaleItemHeight,scaleHeight);
        scaleWidth=ta.getInt(R.styleable.MyLoopScaleView_scaleItemWidth,scaleWidth);

        currsorStartColor=ta.getColor(R.styleable.MyLoopScaleView_currsorStartColor,currsorStartColor);
        currsorEndColor=ta.getColor(R.styleable.MyLoopScaleView_currsorEndColor,currsorEndColor);

        showItemSize=ta.getInteger(R.styleable.MyLoopScaleView_showItemSize,showItemSize);

        animatDurationResilience=ta.getInteger(R.styleable.MyLoopScaleView_anmationResilienceDuration,animatDurationResilience);
        animatDurationEdge=ta.getInteger(R.styleable.MyLoopScaleView_animatEdgeDuration,animatDurationEdge);
        animatDurationScrollToValue=ta.getInteger(R.styleable.MyLoopScaleView_animatScrollToValueDuration,animatDurationScrollToValue);
        ta.recycle();


        //计算游标位置突出的各个高度
        currsorGradientScaleInfo=new float[currsorGradientSize+1][3];
        for (int i = 0; i < currsorGradientScaleInfo.length; i++) {
            if(currsorGradientSize==i){
                currsorGradientScaleInfo[i][0] = scaleHeight * 3.2f;
                currsorGradientScaleInfo[i][1] = scaleHeight * 2;
                currsorGradientScaleInfo[i][2] = getLinearColor(currsorStartColor,currsorEndColor,1);
            }else {
               float rate=(float) (Math.sin((i - currsorGradientSize)) / ((i - currsorGradientSize)));
                currsorGradientScaleInfo[i][0] = scaleHeight * rate * 3;
                currsorGradientScaleInfo[i][1] = scaleHeight * rate * 2;
                currsorGradientScaleInfo[i][2] = getLinearColor(currsorStartColor,currsorEndColor,rate);
            }
            Log.d(TAG,"currsorGradientScaleInfo top="+ currsorGradientScaleInfo[i][0]);
        }

        animatorPlayingList=new ArrayList<>();
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        //初始化位置，数值
        initPosAndLocation();
    }


    /**
     * 初始化默认值
     */
    private void initPosAndLocation() {
        //maxValue多加的数值是了保证scaleItemCount为10的倍数
        if (isFM) {
            oneItemValue = 1;
            maxValue = (int) (1080+spaceScaleCount*oneItemValue);
            minValue = 875;
        } else {
            oneItemValue = 9;
            maxValue = (int) (1602+oneItemValue);
            minValue = 531;
        }

        //一个小刻度的宽度
        scaleDistance = getMeasuredWidth() / (showItemSize * spaceScaleCount);

        scaleItemCount = (int) (((maxValue - minValue) / oneItemValue));

        Log.d(TAG,"scaleItemCount="+scaleItemCount+"---showItemSize="+(showItemSize*spaceScaleCount));

        //尺子长度=总的个数*一个的宽度
        viewWidth = scaleItemCount * scaleDistance;

        currsorTargetPos = (showItemSize/2) * spaceScaleCount;
        updatecurrsorPos(currsorTargetPos);
        currsorLocation = currsorTargetPos*scaleDistance;
        valueLocation=0;
        currsorPosDiff=0;
        pointLocationDiff=0;
        updateCurrsorValue(false);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipRect(getPaddingStart(), getPaddingTop(), getWidth() - getPaddingRight(), viewHeight - getPaddingBottom());

        //绘制下方刻度值，一个轮盘,每2个大刻度绘制一次数值
        for (int i = 0; i < scaleItemCount; i += spaceScaleCount*2) {
            drawScaleValue(canvas, i, -1);
        }
        for (int i = 0; i < scaleItemCount; i += spaceScaleCount*2) {
            drawScaleValue(canvas, i, 1);
        }

        if(scaleItemCount/(showItemSize*spaceScaleCount)<2){
            //调频范围较小多绘制一页来过渡
            for (int i = scaleItemCount; i < scaleItemCount*2; i+=spaceScaleCount*2) {
                drawScaleValue(canvas, i, 1);
            }
        }

        //绘制上方刻度，只绘制可视范围
        for (int i = 0; i < showItemSize * spaceScaleCount; i++) {
            drawScale(canvas, i);
        }

        //绘制圆点
        paint.setColor(currsorEndColor);
        canvas.drawCircle(currsorLocation,  viewHeight - scaleTextSpaceHeight - getPaddingBottom() - textRect.height(), scaleWidth*2, paint);
    }



    private void drawScale(Canvas canvas, int index) {
        float location = index * scaleDistance;

        float drawBottom = viewHeight - scaleTextSpaceHeight - getPaddingBottom() - textRect.height();

        //当次绘制的与普通scale的高度差值
        float tempTopDiff = 0;
        float tempBottomDiff = 0;

        //与前一个的高度差值
        float tempLastTopDiff = 0;
        float tempLastBottomDiff = 0;

        if (index == currsorPos) {
            tempTopDiff=currsorGradientScaleInfo[currsorGradientSize][0];
            tempBottomDiff=currsorGradientScaleInfo[currsorGradientSize][1];

            float lastTop =currsorGradientScaleInfo[currsorGradientSize-1][0];
            float lastBottom =currsorGradientScaleInfo[currsorGradientSize-1][1];
            //根据当前滑动的位置在两个item间的占比，再计算出一个动态位置
            tempLastTopDiff = (tempTopDiff - lastTop) * (pointLocationDiff / scaleDistance);
            tempLastBottomDiff = (tempBottomDiff - lastBottom) * (pointLocationDiff / scaleDistance);
            paint.setColor((int) currsorGradientScaleInfo[currsorGradientSize][2]);

            canvas.drawLine(location, drawBottom -scaleHeight- tempTopDiff + tempLastTopDiff, location, drawBottom - tempBottomDiff + tempLastBottomDiff, paint);

        } else if (index >= gradientLeftPos && index <= currsorPos) {
            tempTopDiff=currsorGradientScaleInfo[index-currsorPos+currsorGradientSize][0];
            tempBottomDiff=currsorGradientScaleInfo[index-currsorPos+currsorGradientSize][1];

            float lastTop = 0;
            float lastBottom = 0;
            if (index > gradientLeftPos) {
                lastTop=currsorGradientScaleInfo[index-currsorPos+currsorGradientSize-1][0];
                lastBottom=currsorGradientScaleInfo[index-currsorPos+currsorGradientSize-1][1];
            }
            tempLastTopDiff = (tempTopDiff - lastTop) * (pointLocationDiff / scaleDistance);
            tempLastBottomDiff = (tempBottomDiff - lastBottom) * (pointLocationDiff / scaleDistance);
            paint.setColor((int) currsorGradientScaleInfo[index-currsorPos+currsorGradientSize][2]);

            canvas.drawLine(location, drawBottom - scaleHeight - tempTopDiff + tempLastTopDiff, location, drawBottom - tempBottomDiff + tempLastBottomDiff, paint);
        } else if (index > currsorPos && index <= gradientRightPos) {

            tempTopDiff=currsorGradientScaleInfo[currsorGradientSize-(index - currsorPos)][0];
            tempBottomDiff=currsorGradientScaleInfo[currsorGradientSize-(index - currsorPos)][1];

            float lastTop = 0;
            float lastBottom = 0;
            if(index==currsorPos+1){
                lastTop  = currsorGradientScaleInfo[currsorGradientSize][0];
                lastBottom  =currsorGradientScaleInfo[currsorGradientSize][1];
            }else {
                lastTop = currsorGradientScaleInfo[currsorGradientSize-(index - currsorPos)+1][0];
                lastBottom = currsorGradientScaleInfo[currsorGradientSize-(index - currsorPos)+1][1];
            }
            tempLastTopDiff = (lastTop - tempTopDiff) * (pointLocationDiff / scaleDistance);
            tempLastBottomDiff = (lastBottom - tempBottomDiff) * (pointLocationDiff / scaleDistance);
            paint.setColor((int) currsorGradientScaleInfo[currsorGradientSize-(index - currsorPos)][2]);
            canvas.drawLine(location, drawBottom - scaleHeight - tempTopDiff - tempLastTopDiff, location, drawBottom - tempBottomDiff - tempLastBottomDiff, paint);
        } else {
            if ((index - currsorPosDiff) >= 0 && (index - currsorPosDiff) % spaceScaleCount == 0) {
                paint.setColor(scaleHighlightColor);
                canvas.drawLine(location, drawBottom - scaleHeight, location, drawBottom, paint);
            } else {
                paint.setColor(scaleColor);
                canvas.drawLine(location, drawBottom - scaleHeight, location, drawBottom, paint);
            }
        }
    }


    /**
     * 更新当前选中的位置，以及选中位置附近特定刻度的范围
     *
     * @param pos
     */
    private void updatecurrsorPos(int pos) {
        currsorPos = pos;
        gradientLeftPos = currsorPos - currsorGradientSize;
        gradientRightPos = currsorPos + currsorGradientSize;

    }

    /**
     * 绘制刻度值
     *
     * @param canvas 画布
     * @param index  刻度值位置
     * @param type   正向绘制还是逆向绘制
     */
    private void drawScaleValue(Canvas canvas, int index, int type) {
//        if(scaleItemCount/(showItemSize*spaceScaleCount)<2){
//            //分屏显示页数小于2的情况，即AM调频范围较小的情况
//            if (valueLocation + showItemSize/2 * spaceScaleCount * scaleDistance >= viewWidth) {
//                valueLocation =-showItemSize/2 * spaceScaleCount * scaleDistance;
//                Log.d(TAG,"drawScaleValue valueLocation="+valueLocation);
//            } else if (valueLocation - showItemSize/2 * spaceScaleCount * scaleDistance <= -viewWidth) {
//                valueLocation = showItemSize/2 * spaceScaleCount * scaleDistance;
//                Log.d(TAG,"drawScaleValue 2 valueLocation="+valueLocation);
//            }
//        }else {
//            if (valueLocation + showItemSize * spaceScaleCount * scaleDistance >= viewWidth) {
//                valueLocation =-showItemSize * spaceScaleCount * scaleDistance;
//            } else if (valueLocation - showItemSize * spaceScaleCount * scaleDistance <= -viewWidth) {
//                valueLocation = showItemSize * spaceScaleCount * scaleDistance;
//            }
//        }


        float location = -valueLocation + index * scaleDistance * type;

        float textValue;
        paintText.setColor(scaleTextColor);
        paintText.setTextSize(scaleTextSize);
        if (type < 0) {
            textValue = (maxValue / oneItemValue - index) * oneItemValue;
           // textValue =(index+spaceScaleCount) * oneItemValue + minValue;
        } else {
            if(index>=scaleItemCount){
                index-=scaleItemCount;
            }
            textValue = index * oneItemValue + minValue;
        }

        if (textValue >= maxValue) {
            textValue = minValue;
        }

        String drawStr;
        if(isFM){
             drawStr = String.valueOf(textValue/10f);
        }else {
             drawStr = String.valueOf((int)textValue);
        }



        paintText.getTextBounds(drawStr, 0, drawStr.length(), textRect);
        canvas.drawText(drawStr, location - textRect.width() * 1f / 2, viewHeight - getPaddingBottom(), paintText);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                adjustPointPos();

                break;
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }


    /**
     * 校准点的与最近的刻度位置
     */
    private void adjustPointPos() {
        if(currsorLocation==currsorPos*scaleDistance){
            scrollTargetCurrsor();
            return;
        }

        int tempPos=currsorPos;
        float diff=currsorLocation-tempPos*scaleDistance;
        if(diff>=scaleDistance/2f){
            //向前一个目标滑动大于刻度间距的一半
            tempPos+=1;
        }else if(diff<=-scaleDistance/2f){
            tempPos-=1;
        }

        Log.d(TAG,"adjustPointPos currsorPos="+currsorPos+"--tempPos="+tempPos+"---diff="+diff);

        final ValueAnimator valueAnimator= ValueAnimator.ofFloat(currsorLocation,tempPos*scaleDistance);
        valueAnimator.setDuration(100);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x= (float) animation.getAnimatedValue();
                scrollCurrsor(x-currsorLocation);
            }
        });

        final int finalTempPos = tempPos;
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                scrollTargetCurrsor();
                Log.d(TAG,"adjustPointPos currsorPos="+currsorPos+"--tempPos="+ finalTempPos);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                animatorPlayingList.add(valueAnimator);
                isPlayAnimation=true;
            }
        });

        valueAnimator.start();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * 更新当前游标所指的数值
     */
    private void updateCurrsorValue(boolean isNotify) {
        float tempValueLocation=valueLocation;
        if(scaleItemCount/(showItemSize*spaceScaleCount)<2){
            float tempValue=showItemSize/3*2 * spaceScaleCount * scaleDistance;
            if (tempValueLocation + tempValue >= viewWidth) {
                tempValueLocation -=viewWidth;
            } else if (tempValueLocation - tempValue <= -viewWidth) {
                tempValueLocation += viewWidth;
            }
        }
        int currentItem = (int) (tempValueLocation / scaleDistance);

        if ((currsorPos + currentItem) * oneItemValue < 0) {
            currsorValue = (currsorPos + currentItem) * oneItemValue + maxValue;
        } else {
            currsorValue = (currsorPos + currentItem) * oneItemValue + minValue;
        }

        //避免显示的showItem过多，导致adjustValueLocationByAnimator滑动很后才触发位置调整
        //这是一种规避处理
        if(currsorValue>=maxValue){
            currsorValue=minValue+(currsorValue-maxValue);
        }

        if (onValueChangeListener != null && isNotify) {
            onValueChangeListener.OnValueChange(isFM?currsorValue/10f:currsorValue);
        }

        Log.d(TAG, "updateCurrsorValue currsorValue=" + currsorValue + "---currentItem=" + currentItem);
    }

    /**
     * 滑动手势处理
     */
    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!isPlayAnimation) {
                scrollCurrsor(-distanceX);
            }
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //惯性滑动 ，由于可能边缘动画冲突，暂不处理
//            if (!mScroller.computeScrollOffset()) {
//                mScroller.fling((int) currsorLocation, 0, (int) (-velocityX / 1.spaceScaleCount), 0, , showItemSize*spaceScaleCount*scaleDistance, 0, 0);
//                sendFingMessage();
//            }
            return false;
        }
    };


    /**
     * 处理游标滑动的距离
     * @param x  当次的滑动距离
     */
    private void scrollCurrsor(float x) {
        currsorLocation+=x;
        if(currsorLocation<0){
            currsorLocation=0;
        }else if(currsorLocation>showItemSize*spaceScaleCount*scaleDistance){
            currsorLocation=showItemSize*spaceScaleCount*scaleDistance;
        }
        float realPos = currsorLocation / scaleDistance;
        currsorPos = (int) realPos;
        if (currsorPos > showItemSize * spaceScaleCount) {
            currsorPos = showItemSize * spaceScaleCount - 1;
        } else if (currsorPos < 1) {
            currsorPos = 1;
        }
        //计算点与游标中心的偏移，用于平滑过渡
        pointLocationDiff = Math.abs(currsorLocation - currsorPos * scaleDistance);
        updatecurrsorPos(currsorPos);

        if (lastCurrsorPos != currsorPos) {
            if (currsorPos >=currosrEdgeRight ) {
                Log.d(TAG, "slide to the right edge ");
                sendEdgeMessage(EDGE_RIGHT);
            } else if (currsorPos <= currosrEdgeLeft) {
                Log.d(TAG, "slide to the left edge");
                sendEdgeMessage(EDGE_LEFT);
            } else {
                loopScaleHandler.removeMessages(HANDLER_FLAG_CHECK_EDG);
            }
        }

        lastCurrsorPos = currsorPos;

        invalidate();
    }


    /**
     * 发送边缘检测
     *
     * @param edgeRight
     */
    private void sendEdgeMessage(int edgeRight) {
        loopScaleHandler.removeMessages(HANDLER_FLAG_CHECK_EDG);
        Message message = loopScaleHandler.obtainMessage();
        message.what = HANDLER_FLAG_CHECK_EDG;
        message.obj = edgeRight;
        loopScaleHandler.sendMessageDelayed(message, handlerDelayTime);
    }


    /**
     * 惯性滑动处理
     */
    private void sendFingMessage(){
        loopScaleHandler.removeMessages(HANDLER_FLAG_FING);
        loopScaleHandler.sendEmptyMessage(HANDLER_FLAG_FING);
    }




//    /**
//     * 提供给外部滑动下方转盘的方法
//     *
//     * @param count 滑动的刻度数量
//     */
//    public void scrollScaleItem(int count) {
//        float tempOld = valueLocation;
//        float targetLocation = valueLocation + count * scaleDistance;
//        ValueAnimator animator = ValueAnimator.ofFloat(tempOld, targetLocation);
//        animator.setDuration(600);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float location = (float) animation.getAnimatedValue();
//                Log.d(TAG, "2 onAnimationUpdate location=" + location + "---viewWidth=" + viewWidth);
//                valueLocation = location;
//                invalidate();
//            }
//        });
//        animator.start();
//    }


    /**
     * 滑动到指定数值的位置
     * @param value
     */
    public void scrollToTargetValue(float value){
//        if(isPlayAnimation){
//
//           // return;
//        }
        stopAnimator();
        initPosAndLocation();

        if(isFM){
            value=value*10f;
        }

        if(value<minValue){
            value=minValue;
        }else if(value>maxValue){
            value=maxValue;
        }

        if(value%oneItemValue!=0){
            value+=oneItemValue-value%oneItemValue;
        }

//        if(value==currsorValue){
//            return;
//        }

        float tempDiff=currsorValue-value;
        float tempDiffLocation=tempDiff/oneItemValue*scaleDistance;

        currsorPosDiff= (int) ((tempDiff/oneItemValue%spaceScaleCount)+currsorPosDiff)%spaceScaleCount;


        Log.d(TAG,"scrollToTargetValue value"+value+"--currsorValue="+currsorValue);



        ValueAnimator animator = ValueAnimator.ofFloat(valueLocation, valueLocation-tempDiffLocation);
        animator.setDuration((int)(Math.abs(tempDiff/(maxValue-minValue))*animatDurationScrollToValue));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                adjustValueLocationByAnimator((Float) animation.getAnimatedValue());
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                updateCurrsorValue(true);
                isPlayAnimation=false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isPlayAnimation=true;
            }
        });
        animator.start();
    }


    /**
     * 滑动到指定数值的位置
     * @param value  频点数值
     * @param isfm   fm还是am的频点
     */
    public void scrollToTargetValue(float value,boolean isfm){
//        if(isPlayAnimation){
//            return;
//        }

        isFM=isfm;
//        if(isFM!=isfm){
//            isFM=isfm;
////
////            initPosAndLocation();
////            invalidate();
//        }
        scrollToTargetValue(value);
    }


    public float getCurrsorValue() {
        return isFM?currsorValue/10f:currsorValue;
    }

    /**
     * 滑动回目标位置
     */
    private void scrollTargetCurrsor() {
        loopScaleHandler.removeCallbacksAndMessages(null);
        stopAnimator();

        final float tempOld = valueLocation;
        final float target = valueLocation - (currsorTargetPos - currsorPos) * scaleDistance;
        if (currsorPosDiff != 0 && (currsorTargetPos - currsorPos) % spaceScaleCount == 0) {

        } else {
            currsorPosDiff =((currsorTargetPos - currsorPos) % spaceScaleCount + currsorPosDiff) % spaceScaleCount;
        }

        Log.d(TAG, "scrollTargetCurrsor currsorPosDiff=" + currsorPosDiff);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(currsorLocation, currsorTargetPos*scaleDistance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                scrollCurrsor(value-currsorLocation);
            }
        });

        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(tempOld, target);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                adjustValueLocationByAnimator((float) animation.getAnimatedValue());
            }
        });

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator, valueAnimator1);
        animatorSet.setInterpolator(new OvershootInterpolator());
        //根据拖动位置距离目标位置的大小来设置回弹时间
        int time = (int) (Math.abs(currsorTargetPos - currsorPos) * 1f / (showItemSize * spaceScaleCount) * animatDurationResilience);
        animatorSet.setDuration(time);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                updateCurrsorValue(true);
                isPlayAnimation = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                animatorPlayingList.add(animatorSet);
                isPlayAnimation = true;

            }
        });
        animatorSet.start();
    }

    private void stopAnimator(){
        if(animatorPlayingList!=null&&animatorPlayingList.size()>0){
            for (Animator animator : animatorPlayingList) {
                if(animator.isRunning()){
                    animator.end();
                }
            }
            animatorPlayingList.clear();
        }
    }


    public boolean isFM() {
        return isFM;
    }

    /**
     * 通过属性动画滑动下方数值的时候需要矫正滑动的距离
     */
    private void adjustValueLocationByAnimator(float toValueLocation) {
        valueLocation = toValueLocation;

        float tempValue;
        if(scaleItemCount/(showItemSize*spaceScaleCount)<2){
            tempValue=showItemSize/3*2 * spaceScaleCount * scaleDistance;
        }else {
            tempValue=showItemSize * spaceScaleCount * scaleDistance;
        }
        if (valueLocation + tempValue >= viewWidth) {
            Log.d(TAG,"adjustValueLocationByAnimator 2 valueLocation="+valueLocation+"--tempValue="+tempValue+"--viewWidth="+viewWidth);
            valueLocation -=viewWidth;
        } else if (valueLocation - tempValue <= -viewWidth) {
            valueLocation += viewWidth;
            Log.d(TAG,"adjustValueLocationByAnimator 3 valueLocation="+valueLocation+"--tempValue="+tempValue+"--viewWidth="+viewWidth);
        }

    }


    //边缘检测
    private Handler loopScaleHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == HANDLER_FLAG_CHECK_EDG) {
                final int slideType = (int) msg.obj;
                Log.d(TAG, "loopScaleHandler slideType=" + slideType + "---currsorPos=" + currsorPos + "--total=" + showItemSize * spaceScaleCount);
                float targetLocation = 0;
                if (slideType == 1) {
                    if (currsorPos >=currosrEdgeRight) {
                        targetLocation = valueLocation + 2 * spaceScaleCount * scaleDistance;
                    }
                } else {
                    if (currsorPos <= currosrEdgeLeft) {
                        targetLocation = valueLocation - 2 * spaceScaleCount * scaleDistance;
                    }
                }
                if (targetLocation != 0) {
                    final ValueAnimator valueAnimator = ValueAnimator.ofFloat(valueLocation, targetLocation);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            adjustValueLocationByAnimator((float)animation.getAnimatedValue());
                            invalidate();
                        }
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //滑动一次结束重新发送
                            sendEdgeMessage(slideType);
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            animatorPlayingList.add(valueAnimator);
                            loopScaleHandler.removeMessages(HANDLER_FLAG_CHECK_EDG);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            super.onAnimationCancel(animation);
                        }
                    });
                    valueAnimator.setDuration(animatDurationEdge);
                    valueAnimator.start();
                }

            }else if(msg.what==HANDLER_FLAG_FING){
//                mScroller.computeScrollOffset();
//                int currX = mScroller.getCurrX();
//                //float delta = currX - valueLocation;
//                scrollCurrsor(currX);
//                Log.d(TAG,"HANDLER_FLAG_FING currx="+currX+"---valueCuronLocation="+valueLocation);
//                // 滚动还没有完成
//                if (!mScroller.isFinished()) {
//                    sendFingMessage();
//                } else {
//                    Log.d(TAG,"HANDLER_FLAG_FING finish");
//                }
            }
            return false;
        }
    });


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        loopScaleHandler.removeCallbacksAndMessages(null);
        stopAnimator();
    }




    /**
     * 获取颜色渐变中的值
     * @param mStartColor
     * @param mEndColor
     * @param radio  0~1 百分比
     * @return
     */
    private  int getLinearColor(int mStartColor,int mEndColor, float radio){
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();

        return (int)(argbEvaluator.evaluate(radio,mStartColor, mEndColor));
    }

    public interface OnValueChangeListener {
        void OnValueChange(float newValue);
    }



}
