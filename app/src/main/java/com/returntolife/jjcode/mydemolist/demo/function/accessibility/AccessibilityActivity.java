package com.returntolife.jjcode.mydemolist.demo.function.accessibility;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;

import java.util.List;

/**
 * Create by JiaJunHe on 2020/4/12 14:56
 * Email 455hejiajun@gmail.com
 * Description:
 * Version: 1.0
 */
public class AccessibilityActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String  APP_NAME="cn.xuexi.android";

    private TextView mTvConnectStatue;
    private Button mBtnGotoSetting,mBtnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        mTvConnectStatue=findViewById(R.id.tv_connect_statue);

        mBtnStart=findViewById(R.id.btn_start);
        mBtnGotoSetting=findViewById(R.id.btn_goto_setting);

        mBtnGotoSetting.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isStartService=isAccessibilitySettingsOn(this,MyAccessibilityService.class.getName());
        mTvConnectStatue.setText("无障碍服务是否开启："+isStartService);

        if(isStartService){
            mBtnStart.setEnabled(true);
        }else {
            mBtnStart.setEnabled(false);
        }
    }

    /**
     * 判断是否有辅助功能权限
     * @return true 已开启
     *          false 未开启
     */
    public  boolean isAccessibilitySettingsOn(Context context, String className){
        if (context == null){
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if(activityManager==null){
            return false;
        }

        List<ActivityManager.RunningServiceInfo> runningServices =
                activityManager.getRunningServices(100);// 获取正在运行的服务列表
        if (runningServices==null||runningServices.size()<=0){
            return false;
        }
        for (int i=0;i<runningServices.size();i++){
            ComponentName service = runningServices.get(i).service;
            if (service.getClassName().equals(className)){
                return true;
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_goto_setting:
                Intent localIntent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                startActivity(localIntent);
                break;
            case R.id.btn_start:
                Intent intent =getPackageManager().getLaunchIntentForPackage("cn.xuexi.android");

                startActivity(intent);
                break;
                default:
                    break;
        }
    }
}
