package com.returntolife.jjcode.mydemolist.demo.widget.superedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;

import java.lang.reflect.Field;

public class SuperEditText extends AppCompatEditText {
    private Paint mPaint;//画笔
    private int ic_deleteResID;//删除图标 资源ID
    private Drawable ic_delete;//删除图标
    private int delete_x, delete_y, delete_width, delete_height;// 删除图标起点(x,y)、删除图标宽、高（px）

    private int ic_left_clickResID, ic_leftunclickResID;//左侧图标 资源ID(点击&无点击)
    private Drawable ic_left_click, ic_left_unclick;//左侧图片(点击&未点击)
    private int left_x, left_y, left_width, left_height;//左侧图标起点（x,y）、左侧图标宽、高（px）

    private int cursor;//光标

    //分割线变量
    private int lineColor_click, lineColor_unclick;//点击时&未点击颜色
    private int color;
    private int linePosition;

    public SuperEditText(Context context) {
        super(context);
    }

    public SuperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SuperEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperEditText);
        /**
         * 初始化左侧图标（点击 & 未点击）
         */
        ic_left_clickResID = typedArray.getResourceId(R.styleable.SuperEditText_ic_left_click, R.drawable.ic_left_click);
        ic_left_click = getResources().getDrawable(ic_left_clickResID);

        left_x = typedArray.getInteger(R.styleable.SuperEditText_left_x, 0);
        left_y = typedArray.getInteger(R.styleable.SuperEditText_left_y, 0);
        left_width = typedArray.getInteger(R.styleable.SuperEditText_left_width, 60);
        left_height = typedArray.getInteger(R.styleable.SuperEditText_left_height, 60);

        ic_left_click.setBounds(left_x, left_y, left_width, left_height);


        ic_leftunclickResID = typedArray.getResourceId(R.styleable.SuperEditText_ic_left_unclick, R.drawable.ic_left_unclick);
        ic_left_unclick = getResources().getDrawable(ic_leftunclickResID);
        ic_left_unclick.setBounds(left_x, left_y, left_width, left_height);

        /**
         * 初始化删除图标
         */
        ic_deleteResID = typedArray.getResourceId(R.styleable.SuperEditText_ic_delete, R.drawable.delete_click);
        ic_delete = getResources().getDrawable(ic_deleteResID);
        delete_x = typedArray.getInteger(R.styleable.SuperEditText_delete_x, 0);
        delete_y = typedArray.getInteger(R.styleable.SuperEditText_delete_y, 0);
        delete_width = typedArray.getInteger(R.styleable.SuperEditText_delete_width, 60);
        delete_height = typedArray.getInteger(R.styleable.SuperEditText_delete_height, 60);
        ic_delete.setBounds(delete_x, delete_y, delete_width, delete_height);

        /**
         * 设置EditText左侧 & 右侧的图片（初始状态仅有左侧图片））
         */
        setCompoundDrawables(ic_left_unclick, null, null, null);

        cursor = typedArray.getResourceId(R.styleable.SuperEditText_cursor, R.drawable.cursor);
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(this, cursor);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        /**
         * 设置分割线（颜色、粗细、位置）
         */
        mPaint = new Paint();
        mPaint.setStrokeWidth(2.0f);//分割线粗细
        int lineColorClick_default = context.getResources().getColor(R.color.lineColor_click);// 默认 = 蓝色#1296db
        int lineColorunClick_default = context.getResources().getColor(R.color.lineColor_unclick);
        lineColor_click = typedArray.getColor(R.styleable.SuperEditText_lineColor_click, lineColorClick_default);
        lineColor_unclick = typedArray.getColor(R.styleable.SuperEditText_lineColor_unclick, lineColorunClick_default);
        color = lineColor_unclick;

        mPaint.setColor(lineColor_unclick);
        setTextColor(color);

        linePosition = typedArray.getInteger(R.styleable.SuperEditText_linePosition, 1);
        setBackground(null);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setDeleteIconVisible(hasFocus() && length() > 0, hasFocus());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = ic_delete;
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight()) &&
                        event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 关注1
     * 作用：判断是否显示删除图标 & 设置分割线颜色
     */
    private void setDeleteIconVisible(boolean deleteVisible, boolean leftVisible) {
        setCompoundDrawables(leftVisible ? ic_left_click : ic_left_unclick, null,
                deleteVisible ? ic_delete : null, null);
        color = leftVisible ? lineColor_click : lineColor_unclick;
        setTextColor(color);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(color);
        setTextColor(color);
        int x = this.getScrollX();//获取延伸后的长度
        int w = this.getMeasuredWidth();//获取控件长度

        canvas.drawLine(0, this.getMeasuredHeight() - linePosition,
                w + x, this.getMeasuredHeight() - linePosition, mPaint);
    }
}
