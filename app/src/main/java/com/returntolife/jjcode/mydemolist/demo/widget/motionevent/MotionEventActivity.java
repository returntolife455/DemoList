package com.returntolife.jjcode.mydemolist.demo.widget.motionevent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.bean.DemoListBean;
import com.returntolife.jjcode.mydemolist.manager.DemoListDataManager;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;
import com.tools.jj.tools.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MotionEventActivity extends AppCompatActivity {
    private static final String TAG = "Activity";
    @BindView(R.id.sticky_header)
    TextView head;
    @BindView(R.id.sticky_content)
    RecyclerView list;
    @BindView(R.id.parent)
    StickyLayout parent;
    private List<DemoListBean> data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        list.setLayoutManager(new LinearLayoutManager(this));
        data = DemoListDataManager.getInstance().getDemoListBeanList();
        list.setAdapter(new CommonRecyclerAdapter<DemoListBean>(this,R.layout.item_demolist_fragment, data) {
            @Override
            public void convert(BaseRecyclerViewHolder holder, final DemoListBean demoListBean, int position) {
                holder.setText(R.id.tv_title,demoListBean.getTitle());
                holder.setText(R.id.tv_des,demoListBean.getDes());
                holder.setText(R.id.tv_time,demoListBean.getTime());
                if(demoListBean.getImageResource()>0){
                    holder.setImageResource(R.id.iv_main,demoListBean.getImageResource());
                }else if(!StringUtil.isEmpty(demoListBean.getImageUrl())){
                    holder.setImageUrl(R.id.iv_main,demoListBean.getImageUrl());
                }
                holder.setOnClickListener(R.id.cardview_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        demoListBean.startActivity(MotionEventActivity.this);
                    }
                });
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
