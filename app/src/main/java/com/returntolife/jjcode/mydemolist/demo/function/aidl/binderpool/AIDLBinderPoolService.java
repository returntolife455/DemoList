package com.returntolife.jjcode.mydemolist.demo.function.aidl.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.returntolife.jjcode.mydemolist.demo.function.aidl.binder.BinderPoolImpl;


/*
 * Create by JiaJun He on 2019/6/5$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class AIDLBinderPoolService extends Service {


    private IBinder iBinderPool=new BinderPoolImpl();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinderPool;
    }
}
