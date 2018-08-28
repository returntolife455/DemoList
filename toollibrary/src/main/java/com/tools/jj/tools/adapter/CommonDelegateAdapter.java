package com.tools.jj.tools.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiajun on 2017/10/27.
 * E-mail  : 1021661582@qq.com
 * Desc    : delegateAdapter通用类
 * Version : 1.0.0
 */

public abstract class CommonDelegateAdapter<T> extends DelegateAdapter.Adapter {

    private Context mContext;
    private int mLayoutId;
    public List<T> mDatas;
    private LayoutInflater mInflater;
    private LayoutHelper helper;
    private int itemNum;


    public CommonDelegateAdapter(Context mContext, int mLayoutId, List<T> mDatas, LayoutHelper helper, int itemNum) {
        this.helper = helper;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        this.itemNum = itemNum;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseRecyclerViewHolder viewHolder = BaseRecyclerViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (mDatas != null && mDatas.size() > 0) {
            convert((BaseRecyclerViewHolder) holder, mDatas.get(position), position);
        } else {
            convert((BaseRecyclerViewHolder) holder, position);
        }
    }


    public abstract void convert(BaseRecyclerViewHolder holder, T t, int position);

    public abstract void convert(BaseRecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (itemNum == 0)
            if (mDatas != null && mDatas.size() > 0)
                return mDatas.size();

        return itemNum;
    }

    /**
     * 更新数据
     *
     * @param mDatas
     */
    public void upDateData(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /**
     * 添加更多的数据
     *
     * @param mMoreDatas
     */
    public void addMoreData(List<T> mMoreDatas) {
        if (mMoreDatas != null && mMoreDatas.size() > 0) {
            mDatas.addAll(mMoreDatas);
        }
        notifyDataSetChanged();
    }

    /**
     * 用于添加直播评论
     *
     * @param mDatas
     * @param position
     * @param count
     */
    public void addDataByPosition(List<T> mDatas, int position, int count) {
        int p = position;
        if (this.mDatas.size() > 10) {
            for (int i = 0; i < mDatas.size() - 10; i++) {
                removeFirstData();
            }
        }

        if (mDatas != null && mDatas.size() > 0) {
            for (int i = 0; i < count; i++) {
                this.mDatas.add(mDatas.get(p));
                p++;
                notifyItemInserted(this.mDatas.size() - 1);
            }
        }
    }

    /**
     * 尾部添加一条数据
     *
     * @param data
     */
    public void addOneData(T data) {
        if (data != null) {
            if(mDatas==null){
                mDatas=new ArrayList<>();
            }
            mDatas.add(data);
        }
        notifyItemInserted(mDatas.size() - 1);
    }

    /**
     * 移除第一条数据
     */
    public void removeFirstData() {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.remove(0);
        }
        notifyItemRemoved(0);
    }
}
