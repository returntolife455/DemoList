package com.returntolife.jjcode.mydemolist.demo.function.aidl.binder;

import android.os.RemoteException;

import com.returntolife.jjcode.mydemolist.IOnNewBookArrivedListener;
import com.returntolife.jjcode.mydemolist.IPerson;
import com.returntolife.jjcode.mydemolist.bean.AIDLBook;

/*
 * Create by JiaJun He on 2019/6/5$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class BinderPersonImpl extends IPerson.Stub {
    @Override
    public void setName(String s) throws RemoteException {

    }

    @Override
    public String getName() throws RemoteException {
        return "person获取name";
    }

    @Override
    public void setBook(AIDLBook book) throws RemoteException {

    }

    @Override
    public AIDLBook getBook() throws RemoteException {
        return null;
    }

    @Override
    public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

    }

    @Override
    public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

    }
}
