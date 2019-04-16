package com.returntolife.jjcode.mydemolist;

import android.Manifest;
import android.content.Intent;

import com.tools.jj.tools.activity.permission.BasePermissionActivity;

public class AppPermissionActivity extends BasePermissionActivity {
    @Override
    public String[] setRequestString() {
        return new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    @Override
    public void initActivity() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
