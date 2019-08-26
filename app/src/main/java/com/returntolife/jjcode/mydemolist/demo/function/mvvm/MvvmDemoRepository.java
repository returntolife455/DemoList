package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.returntolife.jjcode.mydemolist.AppApplication;
import com.tools.jj.tools.http.Http;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

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
