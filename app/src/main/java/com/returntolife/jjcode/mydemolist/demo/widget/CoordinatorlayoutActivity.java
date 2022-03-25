package com.returntolife.jjcode.mydemolist.demo.widget;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HeJiaJun on 2018/9/30.
 * des:
 * version:1.0.0
 */

public class CoordinatorlayoutActivity extends Activity {


    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private List<String> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_widget_coordinator);
        ButterKnife.bind(this);

        data=new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            data.add("测试数据 i="+i);
        }

        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(new CommonRecyclerAdapter<String>(this,R.layout.item_demolist,data) {
            @Override
            public void convert(BaseRecyclerViewHolder holder, int position) {

            }

            @Override
            public void convert(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setBtnText(R.id.btn_demo,s);
            }
        });


    }
}
