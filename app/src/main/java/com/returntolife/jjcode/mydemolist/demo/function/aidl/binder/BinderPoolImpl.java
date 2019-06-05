package com.returntolife.jjcode.mydemolist.demo.function.aidl.binder;

import android.os.IBinder;
import android.os.RemoteException;

import com.returntolife.jjcode.mydemolist.IBinderPool;

/*
 * Create by JiaJun He on 2019/6/5$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class BinderPoolImpl extends IBinderPool.Stub{

    public static final int BINDER_CODE_PERSON=1;
    public static final int BINDER_CODE_TOOL=2;

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder=null;

        switch (binderCode) {
            case BINDER_CODE_PERSON:
                binder = new BinderPersonImpl();
                break;
            case BINDER_CODE_TOOL:
                binder = new BinderToolImpl();
                break;
            default:
                break;
        }

        return binder;
    }
}
