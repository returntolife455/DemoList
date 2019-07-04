package com.returntolife.jjcode.mydemolist.demo.function.mvvm;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:hejj@mama.cn
 * des:
 */
public abstract class BaseActivity<T extends BaseViewModel> extends FragmentActivity {

    protected T mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel= ViewModelProviders.of(this).get(getViewModelClass());
    }



    /**
     * 创建 自定义的 ViewModel
     */
    protected abstract Class<T> getViewModelClass();


}
