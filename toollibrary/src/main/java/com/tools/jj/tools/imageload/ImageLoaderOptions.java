package com.tools.jj.tools.imageload;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import java.io.File;

/**
 * Created by HeJiaJun on 2018/6/27.
 * Email:1021661582@qq.com
 * des:图片参数属性配置
 * version: 1.0.0
 */

public class ImageLoaderOptions {
    //加载类型
    public String url;
    public File file;
    public int drawableResId;
    public Uri uri;
    //错误和等待图片
    public int placeholderResId;
    public int errorResId;
    //缓存
    public boolean skipLocalCache;
    //属性
    public Bitmap.Config config = Bitmap.Config.RGB_565;
    public int targetWidth;
    public int targetHeight;
    public boolean isCenterCrop;
    public boolean isFitcenter;
    public float imageAngle;
    public IBitmapCallBack bitmapCallBack;
    //targetView展示图片
    public View targetView;

    public ImageLoaderOptions(String url) {
        this.url = url;
    }

    public ImageLoaderOptions(File file) {
        this.file = file;
    }

    public ImageLoaderOptions(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public ImageLoaderOptions(Uri uri) {
        this.uri = uri;
    }

    public ImageLoaderOptions placeholder(int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }

    public ImageLoaderOptions imageAngle(float imageAngle) {
        this.imageAngle = imageAngle;
        return this;
    }

    public ImageLoaderOptions error(int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public ImageLoaderOptions config(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    public ImageLoaderOptions resize(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    public ImageLoaderOptions skipLocalCache(boolean skipLocalCache) {
        this.skipLocalCache = skipLocalCache;
        return this;
    }

    public ImageLoaderOptions centerCrop() {
        isCenterCrop = true;
        return this;
    }

    public ImageLoaderOptions fitCenter() {
        isFitcenter = true;
        return this;
    }

    public ImageLoaderOptions bitmapCallBack(IBitmapCallBack bitmapCallBack) {
        this.bitmapCallBack=bitmapCallBack;
        return this;
    }

    public void into(View targetView) {
        this.targetView = targetView;
        ImageLoader.getInstance().loadOptions(this);
    }
}
