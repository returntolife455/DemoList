package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:hejj@mama.cn
 * des:
 */
public class BaseViewModel<T extends BaseRepository> extends AndroidViewModel {

    public T mRepository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
//        if (mRepository != null) {
//            mRepository.unSubscribe();
//        }
    }
}
