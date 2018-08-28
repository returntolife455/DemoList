package com.tools.jj.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Jiajun he on 2018/3/28.
 * Email:1021661582@qq.com
 * des: 通用adapter
 * version: 1.0.0
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BaseViewHolder viewHolder = BaseViewHolder.get(mContext, convertView, parent, mItemLayoutId);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }


    public abstract void convert(BaseViewHolder helper, T item, int position);
}
