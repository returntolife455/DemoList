package com.returntolife.jjcode.mydemolist.demo.function.mvvm;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

/**
 * Created by HeJiaJun on 2019/7/4.
 * Email:455hejiajun@gmail
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
