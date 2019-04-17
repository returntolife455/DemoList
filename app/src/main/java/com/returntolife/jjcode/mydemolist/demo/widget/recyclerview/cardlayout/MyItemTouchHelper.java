package com.returntolife.jjcode.mydemolist.demo.widget.recyclerview.cardlayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;

/**
 * Created by HeJiaJun on 2019/4/8.
 * des:
 * version:1.0.0
 */
public class MyItemTouchHelper extends ItemTouchHelper.Callback {


    private RecyclerViewAdapter adapter;

    public MyItemTouchHelper(RecyclerViewAdapter adapter){
        this.adapter=adapter;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag=1;
        int swipFlag=1;

        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            dragFlag=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            swipFlag=ItemTouchHelper.END;
        }

        return makeMovementFlags(dragFlag,swipFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPos=viewHolder.getAdapterPosition();
        int toPos=target.getAdapterPosition();

        if(toPos>fromPos){
            for (int i = fromPos; i < toPos; i++) {
                Collections.swap(adapter.getDataList(),i,i+1);
            }
        }else {
            for (int i = toPos; i < fromPos; i++) {
                Collections.swap(adapter.getDataList(), i, i + 1);
            }
        }

        recyclerView.getAdapter().notifyItemMoved(fromPos,toPos);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction==ItemTouchHelper.END){
            int pos=viewHolder.getAdapterPosition();
            adapter.getDataList().remove(pos);
            adapter.notifyItemRemoved(pos);
        }
    }
}
