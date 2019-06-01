package com.returntolife.jjcode.mydemolist.demo.function.AnnotateMvp;


import com.returntolife.jjcode.mydemolist.R;

@CreatePresenter(AnnotatePresenter.class)
public class AnnotateActivity extends BaseMvpActivity<BaseContract.BasePresenter> implements BaseContract.BaseView{

    @Override
    public int getLayoutId() {
        return R.layout.activity_annotate;
    }

    @Override
    public void init() {
        mBasePresenter.onResume();
    }


}
