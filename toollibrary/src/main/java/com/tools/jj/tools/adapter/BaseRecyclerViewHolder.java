package com.tools.jj.tools.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Jiajun on 2017/10/27.
 * E-mail  : 1021661582@qq.com
 * Desc    : recycler通用型viewholder
 * Version : 1.0.0
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;


    public BaseRecyclerViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static BaseRecyclerViewHolder get(Context context, ViewGroup parent, int layoutId) {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(context, itemView, parent);
        return holder;
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public BaseRecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 加载本地资源图片
     * @param viewId
     * @param resId
     * @return
     */
    public BaseRecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);

        return this;
    }

    //加载网络图片
    public BaseRecyclerViewHolder setImageUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        return this;
    }



    public BaseRecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setCheckedChangeListener(int viewId, CheckBox.OnCheckedChangeListener listener) {
        CheckBox view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * 长按事件
     * @param viewId
     * @param listener
     * @return
     */
    public BaseRecyclerViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }



    public BaseRecyclerViewHolder setBtnText(int viewId, String text) {
        Button tv = getView(viewId);
        tv.setText(text);
        return this;
    }


    /**
     * 控件显示
     *
     * @param viewId
     * @return
     */
    public BaseRecyclerViewHolder setViewVisible(int viewId,boolean isShow) {
        View view = getView(viewId);
        if(isShow){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 对Linearlayout移除所有控件，并加入新的view
     *
     * @param viewId
     * @param viewList
     * @return
     */
    public BaseRecyclerViewHolder addViewToLinearLayout(int viewId, View[] viewList) {
        LinearLayout view = getView(viewId);
        view.removeAllViews();
        for (View view1 : viewList) {
            view.addView(view1);
        }
        return this;
    }


    public View getmConvertView() {
        return mConvertView;
    }
}
