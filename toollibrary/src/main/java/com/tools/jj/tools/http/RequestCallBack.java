package com.tools.jj.tools.http;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by HeJiaJun on 2018/7/1.
 * des:网络请求结果处理
 * version:1.0.0
 */

public  class RequestCallBack<T> extends Subscriber<JsonResult<T>>{

    public static final int ERROR_NETWORK_LOCAL=-1;
    public static final int ERROR_NETWORK_SERVICE=-2;

    private Context mContext;
    private IRequestCallBack listener;


    public RequestCallBack(Context mContext,IRequestCallBack listener){
        if(null==mContext){
            throw new NullPointerException("mContext must be set");
        }
        this.mContext=mContext;
        this.listener=listener;
    }

    @Override
    public void onCompleted() {
        if(null!=listener){
            listener.requestComplete();
        }
    }

    @Override
    public void onError(Throwable e) {
        byNetworkTodo();
        e.printStackTrace();
    }

    private void byNetworkTodo() {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            if (null != listener) {
                listener.requestError(ERROR_NETWORK_SERVICE, "服务器异常，请稍后重试!");
            }
        } else {
            if(null!=listener){
                listener.requestError(ERROR_NETWORK_LOCAL,"当前网络不可用,请检查您的网络设置");
            }
        }
    }

    @Override
    public void onNext(JsonResult<T> tJsonResult) {
        //已经移除订阅，return
        if (isUnsubscribed()) {
            return;
        }

        try {
            if (tJsonResult == null) {
                byNetworkTodo();
            } else {
                if (tJsonResult.isOk()) {
                    if(null!=listener){
                        listener.requestSuccess(tJsonResult.data);
                    }
                } else {
                    if(null!=listener){
                        listener.requestError(tJsonResult.status,tJsonResult.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
