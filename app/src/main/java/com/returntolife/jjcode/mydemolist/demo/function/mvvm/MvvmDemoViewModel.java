package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.tools.jj.tools.imageload.IBitmapCallBack;
import com.tools.jj.tools.imageload.ImageLoader;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:hejj@mama.cn
 * des:
 */
public class MvvmDemoViewModel extends ViewModel {


    private MvvmDemoRepository repository;

    private MutableLiveData<Bitmap> bitmapMutableLiveData;

    public MvvmDemoViewModel(){
        repository=new MvvmDemoRepository();
        bitmapMutableLiveData=new MutableLiveData<>();
    }

    public MutableLiveData<Bitmap> getBitmapMutableLiveData() {
        return bitmapMutableLiveData;
    }


    public void getImage(){
        //只是做模拟请求
       repository.getImage(new SimpleTarget<Bitmap>() {
           @Override
           public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
               bitmapMutableLiveData.setValue(resource);
           }
       });
    }
}
