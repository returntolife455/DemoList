package com.tools.jj.tools.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HeJiaJun on 2018/7/1.
 * des:头部拦截器
 * version:1.0.0
 */

public class RequestHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request.Builder builder=request.newBuilder();

//        //覆盖相同健值
//        builder.header("token","");
//        //追加健值
//        builder.addHeader("token","");


        return chain.proceed(builder.build());
    }
}
