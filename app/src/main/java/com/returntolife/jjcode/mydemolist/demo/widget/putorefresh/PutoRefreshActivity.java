package com.returntolife.jjcode.mydemolist.demo.widget.putorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.widget.SimpleAdapter;

import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HeJiaJun on 2018/8/28.
 * des:
 * version:1.0.0
 */

public class PutoRefreshActivity extends Activity implements PutoRefreshListView.IRefreshen{


    @BindView(R.id.myview)
    PutoRefreshListView myview;

    private SimpleAdapter simpleAdapter;

    private List<Map<String,Object>> data;

    private  Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putorefresh);
        ButterKnife.bind(this);

        myview.setInterface(this);

        initData();

        initListView();
    }

    private void initData() {
        data=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("text",i);
            map.put("image",R.mipmap.ic_launcher);
            data.add(map);
        }
    }


    private void initListView() {

        simpleAdapter=new SimpleAdapter(
                this,                                //上下文
                data,                                //数据集
                R.layout.putorefresh_listview_item,             //item布局文件
                new String[]{"text","image"},       //map集合中的键值
                new int[]{R.id.listview_text,R.id.listview_image}  //item布局文件中的控件id
        );

        myview.setAdapter(simpleAdapter);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("text","刷新");
                map.put("image",R.mipmap.ic_launcher);
                data.add(0,map);

                myview.setAdapter(simpleAdapter);
                simpleAdapter.notifyDataSetChanged();

                myview.refreshComplete();

            }
        },2000);
    }
}
