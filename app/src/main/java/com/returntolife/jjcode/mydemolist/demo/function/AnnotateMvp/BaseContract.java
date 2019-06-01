package com.returntolife.jjcode.mydemolist.demo.function.AnnotateMvp;


import android.content.Context;

public interface BaseContract {

  interface  BaseView{

      void showToast(String text);

      void showLoding();

      void hideLoding();

      Context getContext();

  }

  interface  BasePresenter<T>{

      void attachView(T view);

      void detachView();

      void onResume();

      void onDestroy();
  }
}
