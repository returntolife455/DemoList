package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:hejj@mama.cn
 * des:
 */
public class BaseRepository {

    // 可以缓解Rx内存占用不能释放的问题
    private CompositeSubscription mCompositeSubscription;

    public BaseRepository() {

    }

    // 添加订阅
    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    // 移除订阅
    public void unSubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.clear();
        }
    }
}
