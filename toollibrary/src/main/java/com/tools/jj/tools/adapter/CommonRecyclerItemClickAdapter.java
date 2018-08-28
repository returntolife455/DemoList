package com.tools.jj.tools.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiajun He on 2017/11/16.
 * E-mail  : 1021661582@qq.com
 * Desc    :recyclerview通用item点击处理适配器
 * Version : 1.0.0
 */

public abstract class CommonRecyclerItemClickAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private List<T> dataList;
    private List<Boolean> isClickList;
    private Context mContext;
    private int mLayoutId;

    private OnItemClickListener mOnItemClickListener = null;
    private int lastClickPosition=-1;

    public CommonRecyclerItemClickAdapter(List<T> dataList, Context mContext, int mLayoutId) {
        this.dataList = dataList;
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        isClickList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            isClickList.add(false);
        }
    }


    //设置默认点击位置
    public void setClickState(int index){
        isClickList.set(index,true);
        lastClickPosition=index;
        notifyDataSetChanged();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseRecyclerViewHolder viewHolder = BaseRecyclerViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerViewHolder holder, final int position) {

        if (dataList.size() > 0 && dataList != null) {
            convert(holder, dataList.get(position), position);
        } else {
            convert(holder, position);
        }
        if (isClickList.get(position)) {
            setCheckItemStatus(holder, dataList.get(position), position);
        } else {
            setUnCheckItemStatus(holder, dataList.get(position), position);
        }

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastClickPosition!=-1){
                        isClickList.set(lastClickPosition, false);
                    }
                    isClickList.set(position, true);
                    lastClickPosition=position;
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(holder, dataList.get(position), position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public interface OnItemClickListener<T> {
        void onItemClick(BaseRecyclerViewHolder holder, T t, int position);
    }

    public abstract void setCheckItemStatus(BaseRecyclerViewHolder holder, T t, int position);

    public abstract void setUnCheckItemStatus(BaseRecyclerViewHolder holder, T t, int position);

    public abstract void convert(BaseRecyclerViewHolder holder, T t, int position);

    public abstract void convert(BaseRecyclerViewHolder holder, int position);
}
