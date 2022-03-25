package com.returntolife.jjcode.mydemolist.main.fragment;

import com.returntolife.jjcode.mydemolist.base.fragment.BaseDemoFragment;
import com.returntolife.jjcode.mydemolist.bean.DemoListBean;
import com.returntolife.jjcode.mydemolist.base.fragment.BaseDemoListFragment;
import com.returntolife.jjcode.mydemolist.manager.DemoListDataManager;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/4/16.
 * des:
 * version:1.0.0
 */
public class HomeDemoListFragment extends BaseDemoListFragment {

    public static BaseDemoFragment getInstance(){
        return new HomeDemoListFragment();
    }


    @Override
    public List<DemoListBean> initData() {
        return DemoListDataManager.getInstance().getDemoListBeanList();
    }
}
