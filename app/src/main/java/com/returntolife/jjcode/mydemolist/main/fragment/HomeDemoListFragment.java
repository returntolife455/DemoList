package com.returntolife.jjcode.mydemolist.main.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.base.fragment.BaseDemoFragment;
import com.returntolife.jjcode.mydemolist.bean.DemoListBean;
import com.returntolife.jjcode.mydemolist.base.fragment.BaseDemoListFragment;
import com.returntolife.jjcode.mydemolist.manager.DemoListDataManager;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;
import com.tools.jj.tools.utils.Dp2PxUtil;
import com.tools.jj.tools.utils.LogUtil;
import com.tools.jj.tools.utils.StringUtil;

import java.util.ArrayList;
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
