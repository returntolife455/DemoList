package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:455hejiajun@gmail
 * des:
 */
public class MvvmDemoViewModel extends BaseViewModel<MvvmDemoRepository> {

    private MutableLiveData<Bitmap> bitmapMutableLiveData;

    public MvvmDemoViewModel(@NonNull Application application) {
        super(application);
        mRepository=new MvvmDemoRepository();
        bitmapMutableLiveData=new MutableLiveData<>();
    }


    public MutableLiveData<Bitmap> getBitmapMutableLiveData() {
        return bitmapMutableLiveData;
    }


    public void getImage(){
        //只是做模拟请求
        mRepository.getImage(new SimpleTarget<Bitmap>() {
           @Override
           public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
               bitmapMutableLiveData.setValue(resource);
           }
       });
    }
}
