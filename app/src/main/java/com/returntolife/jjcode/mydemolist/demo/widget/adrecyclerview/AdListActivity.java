package com.returntolife.jjcode.mydemolist.demo.widget.adrecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdListActivity extends AppCompatActivity {
    private static final String TAG = "AdListActivity";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_recyclerview);
        ButterKnife.bind(this);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(i+"");
        }
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(new AdListAdapter(this,data));
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fPos = layoutManager.findFirstVisibleItemPosition();
                int lPos = layoutManager.findLastCompletelyVisibleItemPosition();
                for (int i = fPos; i < lPos; i++) {
                    View view  = layoutManager.findViewByPosition(i);
                    AdImageViewVersion1 adImageView = view.findViewById(R.id.id_iv_ad);
                    if(adImageView.getVisibility()==View.VISIBLE){
                        Log.d(TAG, "position: "+i);
                        Log.d(TAG, "layoutManager.getHeight(): "+layoutManager.getHeight());
                        Log.d(TAG, "view.getTop(): "+view.getTop());
                        adImageView.setDy(layoutManager.getHeight()-view.getTop());
                    }
                }
            }
        });
    }
}
