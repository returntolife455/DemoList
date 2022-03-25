package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.returntolife.jjcode.mydemolist.AppApplication;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:455hejiajun@gmail
 * des:
 */
public class MvvmDemoRepository extends BaseRepository {


    @SuppressLint("CheckResult")
    public void getImage(SimpleTarget<Bitmap> target){
        Glide.with(AppApplication.pAppContext)
                .asBitmap()
                .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=140540105,4087994726&fm=26&gp=0.jpg")
                .into(target);
    }


}
