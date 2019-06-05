package com.returntolife.jjcode.mydemolist.demo.function.multiItem;

/**
 * Created by luojg on 2016/11/8.
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}