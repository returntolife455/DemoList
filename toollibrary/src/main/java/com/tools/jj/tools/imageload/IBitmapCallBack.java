package com.tools.jj.tools.imageload;

import android.graphics.Bitmap;

/**
 * Created by HeJiaJun on 2018/6/27.
 * des:图片加载自定义view回调接口
 * version:1.0.0
 */
public interface IBitmapCallBack {

	void onBitmapLoaded(Bitmap bitmap);

	void onBitmapFailed(Exception e);

}
