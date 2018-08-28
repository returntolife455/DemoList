package com.tools.jj.tools;

import android.annotation.SuppressLint;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

import com.tools.jj.tools.imageload.IBitmapCallBack;
import com.tools.jj.tools.imageload.IImageLoader;
import com.tools.jj.tools.imageload.ImageLoaderOptions;


/**
 * Created by HeJiaJun on 2018/6/27.
 * Email:1021661582@qq.com
 * des:glide图片加载实体
 * version: 1.0.0
 */

public class GlideLoader implements IImageLoader {

    @SuppressLint("StaticFieldLeak")
    private volatile static RequestManager sRequestManager;

    private static RequestManager getGlideRequestManager() {
        if (sRequestManager == null) {
            synchronized (GlideLoader.class) {
                if (sRequestManager == null) {
                    sRequestManager = Glide.with(MyApplication.mContext);
                }
            }
        }
        return sRequestManager;
    }

    @Override
    public void loadImage(ImageLoaderOptions options) {
        RequestBuilder requestBuilder = null;

        //设置加载类型
        if (options.url != null) {
            requestBuilder = getGlideRequestManager().load(options.url);
        } else if (options.file != null) {
            requestBuilder = getGlideRequestManager().load(options.file);
        }else if (options.drawableResId != 0) {
            requestBuilder = getGlideRequestManager().load(options.drawableResId);
        } else if (options.uri != null){
            requestBuilder = getGlideRequestManager().load(options.uri);
        }

        if (requestBuilder == null) {
            throw new NullPointerException("requestBuilder must not be null");
        }

        RequestOptions requestOptions=new RequestOptions();
        //属性配置
        if (options.targetHeight > 0 && options.targetWidth > 0) {
            requestOptions.override(options.targetWidth, options.targetHeight);
        }
        if (options.isCenterCrop) {
            requestOptions.centerCrop();
        } else if (options.isFitcenter) {
            requestOptions.fitCenter();
        }

        if (options.errorResId != 0) {
            requestOptions.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            requestOptions.placeholder(options.placeholderResId);
        }
        if (options.imageAngle > 0) {

          //  requestOptions.transform(new GlideRoundTransform(MyApplication.mContext, options.imageAngle));
        }
        //缓存
        requestOptions.skipMemoryCache(options.skipLocalCache);

        requestBuilder.apply(requestOptions);

        //加载
        if (options.targetView instanceof ImageView) {
            requestBuilder.into(((ImageView)options.targetView));
        }else if(null!=options.bitmapCallBack&&null!=options.targetView){
            //requestBuilder.into(new GlideViewTarget(options.targetView,options.bitmapCallBack));
        }
    }

    @Override
    public void clearMemoryCache() {
        Glide.get(MyApplication.mContext).clearMemory();
    }

    @Override
    public void clearDiskCache() {
        Glide.get(MyApplication.mContext).clearDiskCache();
    }


    //加载定制view
    private class GlideViewTarget extends ViewTarget<View,Bitmap> {

        private IBitmapCallBack bitmapCallBack;

        public GlideViewTarget(View view, IBitmapCallBack bitmapCallBack) {
            super(view);
            this.bitmapCallBack=bitmapCallBack;
        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            bitmapCallBack.onBitmapLoaded(resource);
        }
    }



}
