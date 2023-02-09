package com.tools.jj.tools.utils;

import static io.reactivex.rxjava3.internal.disposables.DisposableHelper.DISPOSED;

import java.util.concurrent.TimeUnit;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * @Author : hejiajun
 * @Time : 2021/5/25
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class Trigger {

    public boolean mRepeat;

    private final TriggerExecutor mExecutor;

    private Disposable mDisposable = DISPOSED;

    Scheduler mScheduler = null;
    /**
     * 默认执行再主线程
     *
     * @param exe     TriggerExecutor
     * @param isConti mRepeat
     */
    public Trigger(TriggerExecutor exe, boolean isConti) {
        this(exe, isConti, true);
    }

    /**
     * 是否执行主线程
     *
     * @param exe     TriggerExecutor
     * @param isConti mRepeat
     */
    public Trigger(TriggerExecutor exe, boolean isConti, boolean isMainThread) {
        mExecutor = exe;
        mRepeat = isConti;
        mScheduler = isMainThread ? AndroidSchedulers.mainThread() : Schedulers.single();
    }

    public final void cancel() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    /**
     * 设置超时检查的时间.
     *
     * @param time 超时时间
     */
    public void delayed(long time) {
        cancel();
        Observable<Long> observable = mRepeat ?  Observable.interval(time, time, TimeUnit.MILLISECONDS)
                : Observable.timer(time, TimeUnit.MILLISECONDS);

        mDisposable = observable .subscribeOn(Schedulers.io())
                .observeOn(mScheduler)
                .map(new Function<Long, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Long aLong) throws Exception {
                        return mExecutor.execute();
                    }
                })
                //捕捉运行时异常，不退出线程
                .onErrorReturn(new Function<Throwable, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Throwable throwable) throws Exception {
                        LogUtil.e( "Trigger.execute error! ="+throwable);
                        return false;
                    }
                })
                //返回false时，停止定时任务
                .takeUntil(new Predicate<Boolean>() {
                    @Override
                    public boolean test(@NonNull Boolean aBoolean) throws Exception {
                        return !aBoolean;
                    }
                })
                .subscribe();
    }

    public boolean b() {
        return mDisposable.isDisposed();
    }

    @Override
    protected void finalize() {
        cancel();
    }

    public interface TriggerExecutor {
        boolean execute();
    }
}
