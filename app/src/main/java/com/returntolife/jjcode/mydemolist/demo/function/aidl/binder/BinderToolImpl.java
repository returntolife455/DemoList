package com.returntolife.jjcode.mydemolist.demo.function.aidl.binder;

import android.os.RemoteException;

import com.returntolife.jjcode.mydemolist.ITool;

/*
 * Create by JiaJun He on 2019/6/5$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class BinderToolImpl extends ITool.Stub {
    @Override
    public String getData() throws RemoteException {
        return "tool获取data";
    }

    @Override
    public String getColor() throws RemoteException {
        return "tool获取color";
    }
}
