package com.tools.jj.tools.http;

/**
 * Created by HeJiaJun on 2018/7/1.
 * des:网络请求回调接口
 * version:1.0.0
 */

public interface IRequestCallBack<T> {

    void requestComplete();

    void requestError(int errorType, String errorMsg);

    void requestSuccess(T object);
}
