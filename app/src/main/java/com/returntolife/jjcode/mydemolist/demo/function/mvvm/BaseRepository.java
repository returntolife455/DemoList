package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import org.reactivestreams.Subscription;



/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:455hejiajun@gmail
 * des: 升级rxjava2之后不方便使用
 */
@Deprecated
public class BaseRepository {

//    // 可以缓解Rx内存占用不能释放的问题
//    private CompositeSubscription mCompositeSubscription;

    public BaseRepository() {

    }

//    // 添加订阅
//    protected void addSubscribe(Subscription subscription) {
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(subscription);
//    }
//
//    // 移除订阅
//    public void unSubscribe() {
//        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
//            mCompositeSubscription.clear();
//        }
//    }
}
