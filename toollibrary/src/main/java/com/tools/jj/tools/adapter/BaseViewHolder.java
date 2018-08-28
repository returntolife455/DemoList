package com.tools.jj.tools.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jiajun he on 2018/3/28.
 * Email:1021661582@qq.com
 * des: 通用viewholder
 * version: 1.0.0
 */

public class BaseViewHolder {

    private final SparseArray<View> mViews;
    private View mConvertView;

    private BaseViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);

        //setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @return
     */
    public static BaseViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new BaseViewHolder(context, parent, layoutId);
        }
        return (BaseViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
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

    public View getConvertView() {
        return mConvertView;
    }


}
