package com.returntolife.jjcode.mydemolist;

import android.app.Application;
import android.content.Context;

import com.returntolife.jjcode.mydemolist.utils.GlideLoader;
import com.tools.jj.tools.http.Http;
import com.tools.jj.tools.imageload.ImageLoader;
import com.tools.jj.tools.utils.LogUtil;

/**
 * Created by HeJiaJun on 2019/4/17.
 * des:
 * version:1.0.0
 */
public class AppApplication extends Application {

    private static final String TAG = "AppApplication";
    public static Context pAppContext=null;

    @Override
    public void onCreate() {
        super.onCreate();
        pAppContext=this;
        init();

    }

    private void init() {
        //开启debug
        LogUtil.OPEN_LOG=true;
        //图片框架初始化
        ImageLoader.getInstance().setImageLoader(new GlideLoader());
        //接口请求初始化
        Http.initHttp(this);
    }

}
