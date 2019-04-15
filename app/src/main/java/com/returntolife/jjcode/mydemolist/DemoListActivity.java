package com.returntolife.jjcode.mydemolist;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.returntolife.jjcode.mydemolist.changetheme.ChangeThemeActivity;
import com.returntolife.jjcode.mydemolist.drawlockscreen.DrawLockScreenActivity;
import com.returntolife.jjcode.mydemolist.editimage.InvertedImageActivity;
import com.returntolife.jjcode.mydemolist.editimage.PickingPictureActivity;
import com.returntolife.jjcode.mydemolist.mdwidget.MDWidgetActivity;
import com.returntolife.jjcode.mydemolist.mycontentprovider.ContentProviderClientActivity;
import com.returntolife.jjcode.mydemolist.putorefresh.PutoRefreshActivity;
import com.returntolife.jjcode.mydemolist.recyclerview.RecyclerViewActivity;
import com.returntolife.jjcode.mydemolist.resumedownload.ResumeDownloadActivity;
import com.returntolife.jjcode.mydemolist.widget.WidgetActivity;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonDelegateAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HeJiaJun on 2018/8/28.
 * des:
 * version:1.0.0
 */

public class DemoListActivity extends AppCompatActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private DelegateAdapter adapter;
    private CommonDelegateAdapter<String> commonDelegateAdapter;
    private List<String> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demolist);
        ButterKnife.bind(this);

        initData();

        initAdapter();
    }

    private void initAdapter() {
        VirtualLayoutManager manager=new VirtualLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        adapter=new DelegateAdapter(manager);
        LinearLayoutHelper helper=new LinearLayoutHelper();
        commonDelegateAdapter=new CommonDelegateAdapter<String>(this,R.layout.item_demolist,dataList,helper,0) {
            @Override
            public void convert(BaseRecyclerViewHolder holder, final String s, int position) {
                 holder.setBtnText(R.id.btn_demo,s);
                 holder.setOnClickListener(R.id.btn_demo, new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         startDemoActivity(s);
                     }
                 });
            }

            @Override
            public void convert(BaseRecyclerViewHolder holder, int position) {

            }
        };
        adapter.addAdapter(commonDelegateAdapter);
        recyclerview.setAdapter(adapter);
    }



    private void initData() {
        String[] data=getResources().getStringArray(R.array.demo_list);
        dataList=new ArrayList<>();
        Collections.addAll(dataList, data);
    }

    private void startDemoActivity(String s) {
            switch (s){
                case"测试用":
                    startActivity(new Intent(DemoListActivity.this, TestActivity.class));
                    break;
                case "下拉刷新":
                    startActivity(new Intent(DemoListActivity.this, PutoRefreshActivity.class));
                    break;
                case "绘制锁屏":
                    startActivity(new Intent(DemoListActivity.this, DrawLockScreenActivity.class));
                    break;
                case "断点续传":
                    startActivity(new Intent(DemoListActivity.this, ResumeDownloadActivity.class));
                    break;
                case "图片处理_倒影":
                    startActivity(new Intent(DemoListActivity.this, InvertedImageActivity.class));
                    break;
                case "图片处理_取色":
                    startActivity(new Intent(DemoListActivity.this, PickingPictureActivity.class));
                   break;
                case "MD_控件":
                    startActivity(new Intent(DemoListActivity.this, MDWidgetActivity.class));
                    break;
                case"内置主题切换":
                    startActivity(new Intent(DemoListActivity.this, ChangeThemeActivity.class));
                    break;
                case"contentProvider":
                    startActivity(new Intent(DemoListActivity.this, ContentProviderClientActivity.class));
                    break;
                case"recyclerview":
                    startActivity(new Intent(DemoListActivity.this, RecyclerViewActivity.class));
                    break;
                case "widgets":
                    startActivity(new Intent(DemoListActivity.this, WidgetActivity.class));
                    break;
                default:
                    break;
            }
    }

}
