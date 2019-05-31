package com.returntolife.jjcode.mydemolist.demo.function.aidl;

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
import android.widget.EditText;
import android.widget.Toast;

import com.returntolife.jjcode.mydemolist.IPerson;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * Create by JiaJun He on 2019/5/31$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class AIDLClientAcitvity extends Activity {


    @BindView(R.id.btn_bindservice)
    Button btnBindservice;
    @BindView(R.id.btn_setname)
    Button btnSetname;
    @BindView(R.id.btn_getName)
    Button btnGetName;
    @BindView(R.id.et_name)
    EditText etName;


    private IPerson iPerson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_client);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_bindservice, R.id.btn_setname, R.id.btn_getName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bindservice:
                bindServiceByAidl();
                break;
            case R.id.btn_setname:
                setName();
                break;
            case R.id.btn_getName:
                getName();
                break;
        }
    }


    private void bindServiceByAidl() {
        Intent intent = new Intent(this, AIDLService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtil.d("onServiceConnected");
                iPerson = IPerson.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.d("onServiceDisconnected");
            }
        }, BIND_AUTO_CREATE);
    }


    private void setName() {
        if (iPerson != null) {
            try {
                iPerson.setName(etName.getText().toString().trim());
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(this, "setName error="+e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getName(){
        if(iPerson!=null){
            try {
                Toast.makeText(this, iPerson.getName(), Toast.LENGTH_SHORT).show();iPerson.getName();
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(this, "getName error="+e, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
