package com.returntolife.jjcode.mydemolist.recyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/4/8.
 * des:
 * version:1.0.0
 */
public class CardItemTouchHelper extends ItemTouchHelper.Callback {

    private List<Integer> data;
    private CardRecyclerAdapter adapter;
    private int DEFAULT_ROTATE_DEGREE=15;
    private int DEFAULT_SHOW_ITEM=3;
    private float DEFAULT_SCALE=0.1f;
    private float DEFAULT_TRANSLATE_Y=14f;

    public CardItemTouchHelper(List<Integer> data,CardRecyclerAdapter adapter){
        this.data=data;
        this.adapter=adapter;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlag=0;
        int dragFlag=0;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof CardLayoutManager) {
            swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
         viewHolder.itemView.setOnTouchListener(null);
         if(direction==ItemTouchHelper.LEFT||direction==ItemTouchHelper.RIGHT){
             int layoutPosition = viewHolder.getLayoutPosition();
             data.remove(layoutPosition);
             adapter.notifyDataSetChanged();
         }
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if(actionState!=ItemTouchHelper.ACTION_STATE_SWIPE){
            return;
        }

        View itemView=viewHolder.itemView;
        float ratio = dX / getThreshold(recyclerView, viewHolder);
        // ratio 最大为 1 或 -1
        if (ratio > 1) {
            ratio = 1;
        } else if (ratio < -1) {
            ratio = -1;
        }
        // 默认最大的旋转角度为 15 度
        itemView.setRotation(ratio * DEFAULT_ROTATE_DEGREE);
        int childCount = recyclerView.getChildCount();
        // 当数据源个数大于最大显示数时
        if (childCount > DEFAULT_SHOW_ITEM) {
            for (int position = 1; position < childCount - 1; position++) {
                int index = childCount - position - 1;
                View view = recyclerView.getChildAt(position);
                // 和之前 onLayoutChildren 是一个意思，不过是做相反的动画
                view.setScaleX(1 - index * DEFAULT_SCALE + Math.abs(ratio) * DEFAULT_SCALE);
                view.setScaleY(1 - index * DEFAULT_SCALE + Math.abs(ratio) * DEFAULT_SCALE);
                view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
            }
        } else {
            // 当数据源个数小于或等于最大显示数时
            for (int position = 0; position < childCount - 1; position++) {
                int index = childCount - position - 1;
                View view = recyclerView.getChildAt(position);
                view.setScaleX(1 - index * DEFAULT_SCALE + Math.abs(ratio) * DEFAULT_SCALE);
                view.setScaleY(1 - index * DEFAULT_SCALE + Math.abs(ratio) * DEFAULT_SCALE);
                view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
            }
        }
    }

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

//    @Override
//    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        super.clearView(recyclerView, viewHolder);
//        viewHolder.itemView.setRotation(0f);
//    }
}
