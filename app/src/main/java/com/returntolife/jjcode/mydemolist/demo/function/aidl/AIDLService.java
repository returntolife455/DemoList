package com.returntolife.jjcode.mydemolist.demo.function.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.returntolife.jjcode.mydemolist.IOnNewBookArrivedListener;
import com.returntolife.jjcode.mydemolist.IPerson;
import com.returntolife.jjcode.mydemolist.bean.AIDLBook;
import com.tools.jj.tools.utils.LogUtil;

import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Create by JiaJun He on 2019/5/31$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class AIDLService extends Service {

    private String name;
    private AIDLBook mybook;

    private RemoteCallbackList<IOnNewBookArrivedListener> mListener=new RemoteCallbackList<>();

    private Binder binder=new IPerson.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            LogUtil.d("onTransact code="+code);
            return super.onTransact(code, data, reply, flags);

        }

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
            //调用接口通知客户端
            if(mListener!=null){
                int size=mListener.beginBroadcast();
                for (int i = 0; i < size; i++) {
                    IOnNewBookArrivedListener listener=mListener.getBroadcastItem(i);
                    listener.onNewsBookArrived(book);
                }
                mListener.finishBroadcast();
            }
        }



        @Override
        public AIDLBook getBook() throws RemoteException {
            return mybook;
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            LogUtil.d("registerListener listener="+listener);
            mListener.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            LogUtil.d("unregisterListener listener="+listener);
            mListener.unregister(listener);
        }


        @Override
        public void addBookWithIn(AIDLBook book) throws RemoteException {
            LogUtil.d("service before addBookWithIn="+book);
            book.name="in tag";
            LogUtil.d("service after addBookWithIn="+book);
        }

        @Override
        public void addBookWithOut(AIDLBook book) throws RemoteException {
            LogUtil.d("service before addBookWithOut="+book);
            book.name="out tag";
            LogUtil.d("service after addBookWithOut="+book);
        }

        @Override
        public void addBookWithInOut(AIDLBook book) throws RemoteException {
            LogUtil.d("service before addBookWithInOut="+book);
            book.name="inout tag";
            LogUtil.d("service after addBookWithInOut="+book);
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
