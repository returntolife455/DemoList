package com.returntolife.jjcode.mydemolist.ui.fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.ui.base.BaseDemoListFragment;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;
import com.tools.jj.tools.utils.Dp2PxUtil;
import com.tools.jj.tools.utils.LogUtil;
import com.umeng.commonsdk.debug.D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJiaJun on 2019/4/16.
 * des:
 * version:1.0.0
 */
public class HomeDemoListFragment extends BaseDemoListFragment {

    private RecyclerView mRecyvlerView;

    private List<String>  data;




    @Override
    public void initView() {
        data=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("item :="+i);
        }



        final int spanCount=2;
        mRecyvlerView=rootView.findViewById(R.id.rv_main);

        GridLayoutManager manager=new GridLayoutManager(context,spanCount);
        mRecyvlerView.setLayoutManager(manager);
        mRecyvlerView.setAdapter(new CommonRecyclerAdapter<String>(context,R.layout.item_demolist_fragment,data) {
            @Override
            public void convert(BaseRecyclerViewHolder holder, int position) {

            }

            @Override
            public void convert(BaseRecyclerViewHolder holder, String s, int position) {

            }


        });
    }
}
