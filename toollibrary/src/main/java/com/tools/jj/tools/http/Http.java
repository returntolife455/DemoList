package com.tools.jj.tools.http;

import android.content.Context;


import com.tools.jj.tools.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HeJiaJun on 2018/7/1.
 * des:网络请求客户端
 * version:1.0.0
 */

public class Http {

    public final static long DEFAULT_TIMEOUT = 30;

    public static String user_session ;
    public static String user_token = "";

    public static String baseUrl = "http://119.29.157.217:8111/api/";
    public static String baseImageUrl="http://119.29.157.217:8111";

    public static Http http;

    private static Retrofit mRetrofit;


    public static void initHttp(Context context) {
        http = new Http(context);
    }

    public Http(Context context) {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();

        if(BuildConfig.DEBUG){
            builder.addInterceptor(new RequestLogInterceptor());
        }

        builder.addInterceptor(new RequestHeaderInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static  <T> T createApi(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }
}
