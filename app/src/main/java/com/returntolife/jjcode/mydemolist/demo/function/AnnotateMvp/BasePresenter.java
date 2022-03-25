package com.returntolife.jjcode.mydemolist.demo.function.AnnotateMvp;

import android.content.Context;

import androidx.annotation.Nullable;


/**
 * 基础Presenter
 *
 * @author lzx
 * @date 2017/12/5
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;
    protected String volleyTag;
    protected Context mContext;

    @Override
    public void attachView(T view) {
        mView=view;
        mContext=view.getContext();
    }

    @Override
    public void detachView() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
