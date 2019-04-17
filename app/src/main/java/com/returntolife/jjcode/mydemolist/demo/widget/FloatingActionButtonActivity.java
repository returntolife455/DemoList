package com.returntolife.jjcode.mydemolist.demo.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HeJiaJun on 2018/10/8.
 * des:
 * version:1.0.0
 */

public class FloatingActionButtonActivity extends Activity {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.al_title)
    AppBarLayout alTitle;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.cl_root)
    CoordinatorLayout clRoot;

    private List<String> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_widget_fab);
        ButterKnife.bind(this);
        data = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            data.add("测试数据 i=" + i);
        }

        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(new CommonRecyclerAdapter<String>(this, R.layout.item_demolist, data) {
            @Override
            public void convert(BaseRecyclerViewHolder holder, int position) {

            }

            @Override
            public void convert(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setBtnText(R.id.btn_demo, s);
            }
        });

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Snackbar.make(clRoot, "点击Fab", Snackbar.LENGTH_LONG).show();
    }
}
