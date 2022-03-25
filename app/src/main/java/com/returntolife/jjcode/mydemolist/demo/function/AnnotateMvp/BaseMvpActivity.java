package com.returntolife.jjcode.mydemolist.demo.function.AnnotateMvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tools.jj.tools.utils.LogUtil;


public abstract class BaseMvpActivity<P extends BaseContract.BasePresenter> extends Activity implements BaseContract.BaseView {
    protected Context mContext;

    protected   P mBasePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;

        //注解生成P层
        CreatePresenter annotation = getClass().getAnnotation(CreatePresenter.class);
        Class<P> aClass = null;
        if (annotation != null) {
            aClass = (Class<P>) annotation.value();
            LogUtil.d("aClass="+aClass);
        }
        if(aClass!=null){
            try {
                mBasePresenter=aClass.newInstance();
                mBasePresenter.attachView(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        init();
    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showLoding() {

    }

    @Override
    public void hideLoding() {

    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public abstract int getLayoutId();

    public abstract void init();
}
