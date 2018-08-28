package com.returntolife.jjcode.mydemolist.drawlockscreen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 455 on 2017/8/10.
 *
 * @author hj455
 *         version 1.0.0
 * @ClassName: ${type_name}$
 * @Description:
 */

public class DrawLockScreenView extends View {

    List<Pattern> patterns;     //所有圆的信息
    List<Integer> numbers;      //选中那几个圆

    float firstCenter;               //第一个圆的圆心

    Paint paint;               //画笔
    Path path;                  //绘制路径

    float endLineX;            //线条结束x坐标
    float endLineY;            //线条结束y坐标

    int   index=-1;            //选中第几个圆，-1为没选中

    String password;         //当前绘制的图案密码
    boolean isTowDraw=false;  //是否是第二次绘制

    IOnDrawFinish iOnDrawFinish;

    public DrawLockScreenView(Context context) {
        super(context);
        init();
    }

    public DrawLockScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawLockScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    /**
    * @methodName: init
    * @Description: 初始化参数
    */
    private void init(){
        patterns=new ArrayList<Pattern>();
        numbers=new ArrayList<Integer>();
        paint=new Paint();
        path=new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int width=getMeasuredWidth();
        int hight=getMeasuredHeight();

        int size= Math.min(width,hight);      //确保整体是个正方形

        firstCenter=size/3f/2f;

        initPattern();              //给每个圆的信息初始化

    }


    /**
    * @methodName: initPattern
    * @Description:以第一个圆心为基础，每三个换行
    */
    private void initPattern() {

        float x=firstCenter;
        float y=firstCenter;

        for(int i=1;i<=9;i++){
            Pattern pattern=new Pattern();
            pattern.bigR=firstCenter-firstCenter/2f;
            pattern.smallR=firstCenter-firstCenter/1.15f;
            pattern.patternX=x;
            pattern.patternY=y;

            patterns.add(pattern);

            if(i%3==0){
                x=firstCenter;
                y+=2*firstCenter;
            }else{
                x+=2*firstCenter;
            }

        }
    }

    private void drawBigPattern(Canvas canvas) {

        for(int i=0;i<patterns.size();i++){
            if(patterns.get(i).isClick){                //根据是否选中设置画笔颜色
                paint.setColor(Color.YELLOW);
            }else{
                paint.setColor(Color.GRAY);
            }

            path.addCircle(
                    patterns.get(i).patternX,       //圆心x坐标
                    patterns.get(i).patternY,       //圆心y坐标
                    patterns.get(i).bigR,           //半径
                    Path.Direction.CW);             //绘制圆的方向
            canvas.drawPath(path,paint);
            path.reset();
        }
    }

    private void drawSmallPattern(Canvas canvas) {

        paint.setColor(Color.WHITE);

        for(int i=0;i<patterns.size();i++){
            path.addCircle(patterns.get(i).patternX,patterns.get(i).patternY,patterns.get(i).smallR, Path.Direction.CW);
            canvas.drawPath(path,paint);
            path.reset();
        }
    }

    private void drawLine(Canvas canvas) {
        if(index==-1){
            return;
        }
        paint.setColor(Color.RED);
        //画笔的宽度
        paint.setStrokeWidth(20);

        //绘制已经选中的圆形图案之间的直线
        for(int i=0;i<numbers.size()-1;i++){
            canvas.drawLine(
                    patterns.get(numbers.get(i)).patternX,
                    patterns.get(numbers.get(i)).patternY,
                    patterns.get(numbers.get(i+1)).patternX,
                    patterns.get(numbers.get(i+1)).patternY,paint);
        }

        //绘制触摸时候的直线
        canvas.drawLine(
                patterns.get(index).patternX,
                patterns.get(index).patternY,
                endLineX, endLineY,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBigPattern(canvas);
        drawLine(canvas);
        drawSmallPattern(canvas);
    }

    public  void setTowDraw(boolean isOk){
        isTowDraw=isOk;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:          //按下的时候，我们要清空之前的信息然后重新绘制
                cancel();
                updatePattern(event.getX(),event.getY());  //用于判断是否选中圆并更新信息
                break;
            case MotionEvent.ACTION_MOVE:
                updatePattern(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:

                //是否选中了圆
                if(index!=-1){

                    endLineX=patterns.get(index).patternX;
                    endLineY=patterns.get(index).patternY;
                    if(iOnDrawFinish!=null){
                        //是否第二次绘制
                        if(isTowDraw){
                            isOK(password.equals(numbers.toString()));
                        }else{
                            password=numbers.toString();
                            iOnDrawFinish.oneDraw();
                        }
                    }

                }

                break;
        }

        invalidate();

        return true;
    }
    /**
    * @methodName: cancel
    * @Description: 清空图案选中信息
    */
    public  void cancel(){

        index=-1;
        numbers.clear();

        for(int i=0;i<patterns.size();i++){
            patterns.get(i).isClick=false;
        }

        invalidate();

    }

        /**
        * @methodName: updatePattern
        * @Description:判断是否触摸到圆形图案，并更新圆的信息
        */
    private void updatePattern(float x, float y) {

        endLineX=x;
        endLineY=y;

        for(int i=0;i<patterns.size();i++){
            if(x>=(patterns.get(i).patternX-patterns.get(i).bigR) &&
                    x<=(patterns.get(i).patternX+patterns.get(i).bigR) &&
                    y<=(patterns.get(i).patternY+patterns.get(i).bigR) &&
                    y>=(patterns.get(i).patternY-patterns.get(i).bigR) &&
                    !patterns.get(i).isClick){
                patterns.get(i).isClick=true;

                index=i;              //当前选中第几个圆
                numbers.add(index);   //将选中的圆形图案加入集合，方便后续判断
                return;
            }
        }
    }


    public  void setInterFace(IOnDrawFinish iOnDrawFinish){
        this.iOnDrawFinish=iOnDrawFinish;
    }

    public interface IOnDrawFinish{

        public  void oneDraw();            //第一次绘制完后要做的事情

        public  void towDraw(boolean isOK); //第二次绘制后传入两次是否输入相同

    }

    private void isOK(boolean equals) {
        iOnDrawFinish.towDraw(equals);
    }

    private class  Pattern{

        float patternX;   //圆心X坐标
        float patternY;   //圆心Y坐标

        float bigR;       //外圆的半径
        float smallR;     //内圆的半径

        boolean isClick;   //是否选中
    }
}
