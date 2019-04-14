import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HeJiaJun on 2019/3/3.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class MyViewGroup extends ViewGroup {


    public MyViewGroup(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量子view
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int wModeSpec=MeasureSpec.getMode(widthMeasureSpec);
        int wSizeSpec=MeasureSpec.getSize(widthMeasureSpec);

        //这里只做了宽的测量，高度的测量也是类似的
        int resuletW=myMeasureWidth(wModeSpec,wSizeSpec);

        setMeasuredDimension(resuletW,heightMeasureSpec);
    }


    private  int myMeasureWidth(int specMode,int specSize){
        int result=0;
        if(specMode==MeasureSpec.EXACTLY){
            //精准模式直接赋值
            result=specSize;
        }else if(specMode==MeasureSpec.AT_MOST){
            //这里一般根据viewgroup的类型来做处理，比如framelayout,linearlayout这些类型
            //对于linearlayout类可以如下
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                result += view.getMeasuredWidth();
            }

            //对于framelayout这种类型，我们只需取最大的就好
//            for (int i = 0; i < getChildCount(); i++) {
//                View view = getChildAt(i);
//                result = view.getMeasuredWidth() > result ? view.getMeasuredWidth() : result;
//            }
        }else {
            //剩余的这种模式就是没限制大小的模式了，一般用于recyclerview这种可以扩展的布局形式
            //具体的测量方法就是看实际需求了
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 子view数目
        int childCount = getChildCount();
        // 记录当前宽度位置
        int currentWidth = l;
        // 逐个摆放子view
        for (int i = 0;i<childCount;i++){
            View childView = getChildAt(i);
            int height = childView.getMeasuredHeight();
            int width = childView.getMeasuredWidth();
            // 摆放子view,参数分别是子view矩形区域的左、上，右，下。
            childView.layout(currentWidth, t, currentWidth + width, t + height);
            currentWidth += width;
        }
    }

    //onDraw()先于dispatchDraw()执行,用于本身控件的绘制,dispatchDraw()用于子控件的绘制
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }


}
