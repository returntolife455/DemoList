package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.returntolife.jjcode.mydemolist.R;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:455hejiajun@gmail
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
