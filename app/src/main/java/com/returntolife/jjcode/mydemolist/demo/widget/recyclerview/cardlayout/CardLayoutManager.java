package com.returntolife.jjcode.mydemolist.demo.widget.recyclerview.cardlayout;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tools.jj.tools.utils.LogUtil;

/**
 * Created by HeJiaJun on 2019/4/8.
 * des:
 * version:1.0.0
 */
public class CardLayoutManager extends RecyclerView.LayoutManager {


    private int DEFAULT_SHOW_ITEM=3;
    private float DEFAULT_SCALE=0.1f;
    private float DEFAULT_TRANSLATE_Y=14f;

    private RecyclerView recyclerView;
    private ItemTouchHelper helper;

    public CardLayoutManager(RecyclerView recyclerView, ItemTouchHelper helper){
        this.recyclerView=recyclerView;
        this.helper=helper;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        LogUtil.d("onLayoutChildren state="+state);
        removeAllViews();

        detachAndScrapAttachedViews(recycler);

        int count=getItemCount();

        if(count<DEFAULT_SHOW_ITEM){
            for (int i = count-1; i>=0; i--) {
                View view=recycler.getViewForPosition(i);

                addView(view);

                measureChildWithMargins(view,0,0);

                int viewWidth=getDecoratedMeasuredWidth(view);
                int viewHeight=getDecoratedMeasuredHeight(view);
                int widthSpace = getWidth() - viewWidth;
                int heightSpace = getHeight() -viewHeight;

                layoutDecoratedWithMargins(view,widthSpace/2,heightSpace/2,widthSpace/2+viewWidth,heightSpace/2+viewHeight);

                if(i>0){
                    view.setScaleX(1 - i * DEFAULT_SCALE);
                    view.setScaleY(1 - i * DEFAULT_SCALE);
                    view.setTranslationY(i * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                }else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }else {
            for (int i = DEFAULT_SHOW_ITEM-1; i >=0; i--) {
                View view=recycler.getViewForPosition(i);

                addView(view);

                measureChildWithMargins(view,0,0);

                int viewWidth=getDecoratedMeasuredWidth(view);
                int viewHeight=getDecoratedMeasuredHeight(view);
                int widthSpace = getWidth() - viewWidth;
                int heightSpace = getHeight() -viewHeight;

                layoutDecoratedWithMargins(view,widthSpace/2,heightSpace/2,widthSpace/2+viewWidth,heightSpace/2+viewHeight);

                if (i == DEFAULT_SHOW_ITEM) {
                    // 按照一定的规则缩放，并且偏移Y轴。
                    // CardConfig.DEFAULT_SCALE 默认为0.1f，CardConfig.DEFAULT_TRANSLATE_Y 默认为14
                    view.setScaleX(1 - (i - 1) * DEFAULT_SCALE);
                    view.setScaleY(1 - (i - 1) * DEFAULT_SCALE);
                    view.setTranslationY((i - 1) * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                } else if (i > 0) {
                    view.setScaleX(1 - i * DEFAULT_SCALE);
                    view.setScaleY(1 - i * DEFAULT_SCALE);
                    view.setTranslationY(i * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                } else {
                    // 设置 mTouchListener 的意义就在于我们想让处于顶层的卡片是可以随意滑动的
                    // 而第二层、第三层等等的卡片是禁止滑动的
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }
    }




    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(v);
            // 把触摸事件交给 mItemTouchHelper，让其处理卡片滑动事件
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                helper.startSwipe(childViewHolder);
            }

            return false;
        }
    };
}
