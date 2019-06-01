package com.returntolife.jjcode.mydemolist.demo.function.AnnotateMvp;

import android.widget.Toast;


public class AnnotatePresenter extends BasePresenter<BaseContract.BaseView> {

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(mContext, "AnnotatePresenter onResume", Toast.LENGTH_SHORT).show();
    }

}
