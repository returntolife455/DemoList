package com.tools.jj.tools.activity.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tools.jj.tools.R;


/**
 * <p>PermissionsActivity类 权限获取页面</p>
 *
 * @author wangchenlong （from net https://github.com/SpikeKing/wcl-permission-demo）
 * @version 1.0
 *          1.1 update by zmingchun on 2016/2/17
 *          1.2 update by jiajun he on 2018/1/23
 */
public class PermissionsActivity extends AppCompatActivity {

    /**权限授权状态-0授权*/
    public static final int PERMISSIONS_GRANTED = 0;
    /**权限授权状态-1拒绝*/
    public static final int PERMISSIONS_DENIED = 1;

    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
    private static final String EXTRA_PERMISSIONS = "permission.extra_permission"; // 权限参数
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案

    private PermissionsChecker mChecker; // 权限检测器
    private boolean isRequireCheck; // 是否需要系统权限检测, 防止和系统提示框重叠

    /**
     * 启动当前权限页面的公开接口
     * @param activity 上下文
     * @param requestCode 请求码
     * @param permissions 请求权限集合
     */
    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("The PermissionsActivity needs to be started using static starticvityforresult.!");
        }
        ImageView backgroundView = new ImageView(this);
        //背景色
        backgroundView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        backgroundView.setAdjustViewBounds(true);
        backgroundView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(backgroundView);

        mChecker = new PermissionsChecker(this);
        isRequireCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = getPermissions();
            if (!mChecker.lacksPermissions(permissions)) {
                requestPermissions(permissions); // 请求权限
            } else {
                allPermissionsGranted(); // 全部权限都已获取
            }
        } else {
            isRequireCheck = true;
        }
    }

    // 返回传递的权限参数
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    // 请求权限兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    // 全部权限均已获取
    private void allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            allPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
    //    LogUtil.d("显示缺失权限提示");
//        new AlertDialog(this).builder()
//                .setTitle("帮助")
//                .setMsg(getString(R.string.string_help_text))
//                .setPositiveButton("设置", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startAppSettings();
//                    }
//                }).setNegativeButton("退出", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(PERMISSIONS_DENIED);
//                finish();
//            }
//        }).show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
