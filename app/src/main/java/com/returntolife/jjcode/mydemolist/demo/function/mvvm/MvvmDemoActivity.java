package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.returntolife.jjcode.mydemolist.R;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:hejj@mama.cn
 * des:
 */
public class MvvmDemoActivity extends BaseActivity<MvvmDemoViewModel> {

    private Button mBtnGetData;
    private ImageView ivAvatar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvmdemo);

        mBtnGetData=findViewById(R.id.btn_getdata);
        ivAvatar=findViewById(R.id.iv_avatar);

        mViewModel.getBitmapMutableLiveData().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                ivAvatar.setImageBitmap(bitmap);
            }
        });

        mBtnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getImage();
            }
        });
    }

    @Override
    protected Class<MvvmDemoViewModel> getViewModelClass() {
        return MvvmDemoViewModel.class;
    }
}
