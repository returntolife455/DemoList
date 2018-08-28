package com.tools.jj.tools.imageload;

/**
 * Created by HeJiaJun on 2018/6/27.
 * des:图片加载接口
 * version:1.0.0
 */

public interface IImageLoader {

    void loadImage(ImageLoaderOptions options);

    void clearMemoryCache();

    void clearDiskCache();
}
