package com.tools.jj.tools.mvp.v;

import android.content.Context;

/**
 * Created by HeJiaJun on 2018/6/27.
 * des:v层基类
 * version:1.0.0
 */

public interface IBaseView {

    void showToast(String text);

    void showLoding();

    void hideLoding();

    Context getContext();
}
