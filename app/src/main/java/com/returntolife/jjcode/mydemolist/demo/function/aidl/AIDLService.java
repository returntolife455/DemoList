package com.returntolife.jjcode.mydemolist.demo.function.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.returntolife.jjcode.mydemolist.IPerson;
import com.returntolife.jjcode.mydemolist.bean.AIDLBook;
import com.tools.jj.tools.utils.LogUtil;

/*
 * Create by JiaJun He on 2019/5/31$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class AIDLService extends Service {

    private String name;
    private AIDLBook mybook;

    private Binder binder=new IPerson.Stub() {
        @Override
        public void setName(String s) throws RemoteException {
            name=s;
        }

        @Override
        public String getName() throws RemoteException {
            return name;
        }

        @Override
        public void setBook(AIDLBook book) throws RemoteException {
            LogUtil.d("setBook="+book);
            mybook=book;
        }



        @Override
        public AIDLBook getBook() throws RemoteException {
            return mybook;
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d("onUnbind");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");
    }
}
