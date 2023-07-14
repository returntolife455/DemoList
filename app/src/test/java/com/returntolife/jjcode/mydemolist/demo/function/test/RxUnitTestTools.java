package com.returntolife.jjcode.mydemolist.demo.function.test;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
public class RxUnitTestTools {

    /**
     * 单元测试的时候，利用RxJavaPlugins将io线程转换为trampoline
     * trampoline应该是立即执行的意思（待商榷），替代了Rx1的immediate。
     */
    public static void asyncToSync() {
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxJavaPlugins.setNewThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });

//        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
//            @Override
//            public Scheduler apply(Scheduler scheduler) throws Exception {
//                return Schedulers.trampoline();
//            }
//        });
//
//       RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
//           @Override
//           public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
//               return Schedulers.trampoline();
//           }
//       });


    }
}
