package com.tools.jj.tools;

import android.app.Application;
import android.content.Context;


import com.tools.jj.tools.http.Http;
import com.tools.jj.tools.imageload.ImageLoader;
import com.tools.jj.tools.utils.LogUtil;

/**
 * Created by jj on 2018/3/28.
 */

public class MyApplication extends Application {

    public static  Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;

        init();


    }

    private void init() {
        //接口请求初始化
        Http.initHttp(this);
        //图片框架初始化
        ImageLoader.getInstance().setImageLoader(new GlideLoader());

        //开启debug
        LogUtil.OPEN_LOG=true;
        //友盟
        //UMConfigure.init(this, "5a94ca46f43e481d9700015d", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        ImageLoader.getInstance().setImageLoader(new GlideLoader());
    }
}
