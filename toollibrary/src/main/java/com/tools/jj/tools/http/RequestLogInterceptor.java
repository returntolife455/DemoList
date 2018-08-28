package com.tools.jj.tools.http;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by HeJiaJun on 2018/7/1.
 * des:日志拦截器
 * version:1.0.0
 */

public class RequestLogInterceptor implements Interceptor {

    private static final String TAG="RequestLogInterceptor";

    private static final Charset UTF8 = Charset.forName("UTF-8");


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间

        String method = request.method();
        //打印请求信息
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                Log.i(TAG,String.format("request: %s on %s %n%s %nRequestParams:{%s}",
                        request.url(), chain.connection(), request.headers(), sb.toString()));
            }
        } else {
            Log.i(TAG,String.format("request: %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
        }

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();//收到响应的时间
        long tookMs = TimeUnit.NANOSECONDS.toMillis(t2- t1);
        //打印请求耗时
        Log.i(TAG,"requestTime:"+tookMs+"ms");
        //使用response获得headers(),可以更新本地Cookie。
        Headers headers = response.headers();
        Log.i(TAG,"responseHeaders:"+headers.toString());

        //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
        ResponseBody responseBody = response.body();

        //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        //获得返回的数据
        Buffer buffer = source.buffer();
        //使用前clone()下，避免直接消耗
        Log.d(TAG,"response:" + buffer.clone().readString(UTF8));
        return response;
    }
}
