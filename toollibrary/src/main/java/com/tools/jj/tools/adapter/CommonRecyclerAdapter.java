package com.tools.jj.tools.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jiajun He on 2017/11/22.
 * E-mail  : 1021661582@qq.com
 * Desc    :
 * Version : 1.0.0
 */

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;


    public CommonRecyclerAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder viewHolder = BaseRecyclerViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {

        if (mDatas != null && mDatas.size() > 0) {
            convert(holder, mDatas.get(position), position);
        } else {
            convert(holder, position);
        }

    }

    public List<T> getmDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 1 : mDatas.size();
    }


    protected void convert(BaseRecyclerViewHolder holder, int position) { }


    protected void convert(BaseRecyclerViewHolder holder, T t, int position) { }

}
