package com.returntolife.jjcode.mydemolist.demo.function.test;

import org.reactivestreams.Subscriber;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
public class UserPresenterImpl implements UserPresenter {

    UserService userService;
    UserView    userView;

    // 让外部传入UserService & UserView
    public UserPresenterImpl(UserService userService, UserView userView) {
        this.userService = userService;
        this.userView = userView;
    }


    @Override
    public void loadUser(int uid) {
        // 异步网络请求User数据，并在onNext(user)返回
        userService.loadUser(uid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<User>() {

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(User user) {
                    user.uid=user.uid+1;
                    userView.onUserLoaded(user);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
    }
}
