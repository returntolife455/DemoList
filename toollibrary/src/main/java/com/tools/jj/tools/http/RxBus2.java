package com.tools.jj.tools.http;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by HeJiaJun on 2018/7/1.
 * des:
 * version:1.0.0
 */

public class RxBus2 {

    private volatile static RxBus2 defaultRxBus;
    private Subject bus;

    public RxBus2(){
        bus= PublishSubject.create().toSerialized();
    }

    public RxBus2 getInstance(){
        if(null==defaultRxBus){
            synchronized(RxBus2.class){
                if(null==defaultRxBus){
                    defaultRxBus=new RxBus2();
                }
            }
        }
        return defaultRxBus;
    }

    public void post(Object o){
        bus.onNext(o);
    }

    public boolean hasObervable(){
        return bus.hasObservers();
    }


    /*
    * 转换为特定类型的Obserbale
    */
    public <T> Observable<T> tObservable(Class<T> type){
        return bus.ofType(type);
    }

}
