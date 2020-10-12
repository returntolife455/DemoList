package com.returntolife.jjcode.mydemolist.demo.function.aidl.binderpool;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.returntolife.jjcode.mydemolist.IBinderPool;
import com.returntolife.jjcode.mydemolist.IPerson;
import com.returntolife.jjcode.mydemolist.ITool;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.returntolife.jjcode.mydemolist.demo.function.aidl.binder.BinderPoolImpl.BINDER_CODE_PERSON;
import static com.returntolife.jjcode.mydemolist.demo.function.aidl.binder.BinderPoolImpl.BINDER_CODE_TOOL;

/*
 * Create by JiaJun He on 2019/5/31$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class AIDLBinderPoolClientActivity extends Activity {


    @BindView(R.id.btn_bindservice)
    Button btnBindservice;
    @BindView(R.id.btn_getName)
    Button btnGetName;
    @BindView(R.id.btn_getcolor)
    Button btnGetcolor;
    @BindView(R.id.btn_getdata)
    Button btnGetdata;


    private IBinderPool iBinderPool;
    private IPerson iPerson;
    private ITool iTool;

    private ServiceConnection conn;

    private boolean isConnected;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_poolclient);
        ButterKnife.bind(this);

        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtil.d("onServiceConnected");
                iBinderPool = IBinderPool.Stub.asInterface(service);
                try {
                    iPerson = IPerson.Stub.asInterface(iBinderPool.queryBinder(BINDER_CODE_PERSON));
                    iTool = ITool.Stub.asInterface(iBinderPool.queryBinder(BINDER_CODE_TOOL));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Toast.makeText(AIDLBinderPoolClientActivity.this, "onServiceConnected", Toast.LENGTH_SHORT).show();
                isConnected=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.d("onServiceDisconnected");
                isConnected=false;
            }
        };

    }


    @OnClick({R.id.btn_bindservice, R.id.btn_getName,R.id.btn_getcolor,R.id.btn_getdata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bindservice:
                bindServiceByAidl();
                break;
            case R.id.btn_getName:
                getName();
                break;
            case R.id.btn_getcolor:
                getToolColor();
                break;
            case R.id.btn_getdata:
                getToolData();
                break;

            default:
                break;
        }
    }

    private void getToolColor() {
        if(iTool!=null){
            try {
                Toast.makeText(this,"tool:color="+iTool.getColor(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void getToolData() {
        if(iTool!=null){
            try {
                Toast.makeText(this,"tool:data="+iTool.getData(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    private void bindServiceByAidl() {
        Intent intent = new Intent(this, AIDLBinderPoolService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }


    private void getName() {
        if (iPerson != null) {
            try {
                Toast.makeText(this, iPerson.getName(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(this, "getName error=" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isConnected){
            unbindService(conn);
            stopService(new Intent(this,AIDLBinderPoolService.class));
        }
    }

}
