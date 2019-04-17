package com.returntolife.jjcode.mydemolist;

import android.app.Application;
import android.content.Context;

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

    }
}
