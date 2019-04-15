package com.returntolife.jjcode.mydemolist.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJiaJun on 2019/4/8.
 * des:
 * version:1.0.0
 */
public class RecyclerViewActivity extends Activity {


    private RecyclerView mRvMain;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);


        List<String> data=new ArrayList<>();

        data.add("aaaa");
        data.add("bbbb");
        data.add("bbbb3");
        data.add("bbbb4");
        data.add("bbbb5");
        data.add("bbbb6");
        data.add("bbbb7");
        data.add("bbbb8");
        data.add("bbbb9");
        data.add("bbbb11");
        data.add("bbbb12");
        data.add("bbbb13");
        data.add("bbbb14");
        data.add("bbbb15");



        List<Integer> images=new ArrayList<>();

        images.add(R.drawable.test1);
        images.add(R.drawable.test2);
        images.add(R.drawable.test3);
        images.add(R.drawable.test4);
        images.add(R.drawable.test5);

        mRvMain=findViewById(R.id.rv_main);



//        mAdapter=new RecyclerViewAdapter(this,data);
//        mRvMain.setLayoutManager(new LinearLayoutManager(this));
//        mRvMain.setAdapter(mAdapter);
//        ItemTouchHelper helper = new ItemTouchHelper(new MyItemTouchHelper(mAdapter));
//        helper.attachToRecyclerView(mRvMain);
        mAdapter=new CardRecyclerAdapter(this,images);
        ItemTouchHelper helper=new ItemTouchHelper(new CardItemTouchHelper(images, (CardRecyclerAdapter) mAdapter));
        mRvMain.setLayoutManager(new CardLayoutManager(mRvMain,helper));
        mRvMain.setAdapter(mAdapter);
        helper.attachToRecyclerView(mRvMain);
    }
}
