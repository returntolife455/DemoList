package com.returntolife.jjcode.mydemolist;


import android.Manifest;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.returntolife.jjcode.mydemolist.main.activity.MainActivity;
import com.tbruyelle.rxpermissions3.RxPermissions;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public class AppPermissionActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            startActivity(new Intent(AppPermissionActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        finish();
                    }

                    @Override
                    public void onComplete() {
                        finish();
                    }
                });
    }
}
