package com.tools.jj.tools.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.tools.jj.tools.mvp.m.IBasePresenter;
import com.tools.jj.tools.mvp.v.IBaseView;
import com.tools.jj.tools.utils.AppManager;
import com.tools.jj.tools.view.LoadingByLottieDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;


/**
 * Created by HeJiaJun on 2018/6/27.
 * Email:1021661582@qq.com
 * des:页面基类
 * version: 2.0.0
 *
 * 2.0将evebus改为rxbus
 */

public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {

    protected T mBasePresenter;
    protected Context mContext;
    protected Activity mActivity;
    protected boolean mCreate;

    private Toast mToast;
    private Dialog mLoadingDialog;

    protected List<Subscriber<?>> mSubscribers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mActivity = this;
        //创建视图
        int layoutId=getLayoutId();
        setContentView(layoutId);
        initView();
        initToolBar();
        registerListener();
        mBasePresenter = createPresenter();
        //保存当前Activity实体
        AppManager.getInstance().addActivity(mActivity);
        mCreate=true;


    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
        MobclickAgent.onResume(this);
        if (mCreate) {
            initData();
            mCreate = false;
        }
        if (mBasePresenter != null) {
            mBasePresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        releaseMemory();
    }

    @Override
    protected void onDestroy() {
        if (mBasePresenter != null) {
            mBasePresenter.onDestroy();
        }
        unsubscribe();
        super.onDestroy();
    }



    public void toast(final String msg) {
        //限制toast，避免用户疯狂点击出现问题
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, msg + "", Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(msg + "");
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
            }
        });
    }


    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mLoadingDialog==null){
                    mLoadingDialog=new LoadingByLottieDialog(mContext);
                }
                if(!mLoadingDialog.isShowing()&&!isFinishing()){
                    mLoadingDialog.show();
                }
            }
        });
    }


    public void hideLoading() {
        if(mLoadingDialog!=null&&mLoadingDialog.isShowing()&&!isFinishing())
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.dismiss();
                    mLoadingDialog=null;
                }
            });
    }




    public Context getContext() {
        return this;
    }


    /**
     * 添加订阅消息
     * @param subscriber
     */
    protected void addSubscriber(Subscriber<?> subscriber) {
        if(null==mSubscribers){
            mSubscribers=new ArrayList<>();
        }
        mSubscribers.add(subscriber);
    }

    /**
     * 移除指定订阅消息
     * @param subscriber
     */
    protected void removeSubscriber(Subscriber<?> subscriber) {
        if (mSubscribers == null || subscriber == null) {
            return;
        }
        if (!subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
        mSubscribers.remove(subscriber);
    }

    /**
     * 移除所有订阅消息
     */
    protected void unsubscribe() {
        if(null==mSubscribers){
            return;
        }
        for (Iterator<Subscriber<?>> iterator = mSubscribers.iterator(); iterator.hasNext(); ) {
            Subscriber<?> sub = iterator.next();
            if (sub != null && !sub.isUnsubscribed()) {
                sub.unsubscribe();
            }
            iterator.remove();
        }
    }


    /**
     * 订阅消息的基类
     * 为了确定activity销毁的时候移除该订阅避免内存泄漏
     * @param <T>
     */
    public class BaseSubscriber<T> extends Subscriber<T> {

        @CallSuper
        @Override
        public void onStart() {
            super.onStart();
            addSubscriber(this);
        }


        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(T t) {

        }
    }


    /**
     *
     * @return
     */
    protected abstract T createPresenter();

    /**
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     *
     */
    protected abstract void initView();

    /**
     *
     */
    protected abstract void initToolBar();

    /**
     *
     */
    protected abstract void initData();

    /**
     *
     */
    protected abstract void registerListener();

    /**
     *
     */
    protected abstract void releaseMemory();
}
