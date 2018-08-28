package com.tools.jj.tools.imageload;

import android.net.Uri;

import java.io.File;

/**
 * Created by HeJiaJun on 2018/6/27.
 * des:图片加载实体
 * version:1.0.0
 */

public class ImageLoader {

    private static IImageLoader sLoader;
    private static volatile ImageLoader sInstance;

    private ImageLoader() {
    }

    //单例模式
    public static ImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    //若切换其它图片加载框架，可以实现一键替换
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }


    //提供实时替换图片加载框架的接口
    public void setImageLoader(IImageLoader loader) {
        if (loader != null) {
            sLoader = loader;
        }
    }

    public ImageLoaderOptions load(String path) {
        return new ImageLoaderOptions(path);
    }

    public ImageLoaderOptions load(int drawable) {
        return new ImageLoaderOptions(drawable);
    }

    public ImageLoaderOptions load(File file) {
        return new ImageLoaderOptions(file);
    }

    public ImageLoaderOptions load(Uri uri) {
        return new ImageLoaderOptions(uri);
    }


    public void loadOptions(ImageLoaderOptions options) {
        sLoader.loadImage(options);
    }

    public void clearMemoryCache() {
        sLoader.clearMemoryCache();
    }

    public void clearDiskCache() {
        sLoader.clearDiskCache();
    }
}
