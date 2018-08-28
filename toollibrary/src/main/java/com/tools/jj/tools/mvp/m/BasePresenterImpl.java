package com.tools.jj.tools.mvp.m;

import com.tools.jj.tools.mvp.v.IBaseView;

/**
 * Created by HeJiaJun on 2018/6/27.
 * des:p层基础实体类
 * version:1.0.0
 */

public class BasePresenterImpl<T extends IBaseView> implements IBasePresenter {

    protected T mView;

    public BasePresenterImpl(T mView){
        attachView(mView);
    }


    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        detachView();
    }

    //获取view
    protected T getView(){
        return mView;
    }


    //绑定view
    private void attachView(T mView){
        this.mView=mView;
    }

    //解绑view
    private void detachView(){
        if(mView != null){
            mView = null;
        }
    }

    //是否绑定
    public boolean isViewAttached(){
        return mView != null;
    }

}
