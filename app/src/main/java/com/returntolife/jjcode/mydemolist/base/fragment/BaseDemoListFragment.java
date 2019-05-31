package com.returntolife.jjcode.mydemolist.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.bean.DemoListBean;
import com.returntolife.jjcode.mydemolist.manager.DemoListDataManager;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;
import com.tools.jj.tools.utils.Dp2PxUtil;
import com.tools.jj.tools.utils.LogUtil;
import com.tools.jj.tools.utils.StringUtil;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/4/16.
 * des:
 * version:1.0.0
 */
public abstract  class BaseDemoListFragment extends BaseDemoFragment {

    protected RecyclerView mRecyvlerView;

    protected List<DemoListBean> data;

    @Override
    public void initView() {
        data=initData();

        mRecyvlerView=rootView.findViewById(R.id.rv_main);
        //将item以及空隙均分
        int screenWidth= Dp2PxUtil.getScreenWidth((Activity) context);
        int viewWidth=screenWidth/2;
        int space=viewWidth- (int) (context.getResources().getDimension(R.dimen.app_demolist_fragment_item_width));
        final int diff=space*2/3;
        GridLayoutManager manager=new GridLayoutManager(context,2);
        mRecyvlerView.setLayoutManager(manager);
        if (mRecyvlerView.getItemDecorationCount() == 0) {
            mRecyvlerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    if(parent.getChildAdapterPosition(view)%2==0){
                        outRect.left=diff;
                        outRect.right=diff/2;
                    }else {
                        outRect.left=diff/2;
                        outRect.right=diff;
                    }

                    outRect.top=diff;
                }
            });
        }
        mRecyvlerView.setAdapter(new CommonRecyclerAdapter<DemoListBean>(context,R.layout.item_demolist_fragment,data) {
            @Override
            public void convert(BaseRecyclerViewHolder holder, final DemoListBean demoListBean, int position) {
                holder.setText(R.id.tv_title,demoListBean.getTitle());
                holder.setText(R.id.tv_des,demoListBean.getDes());
                holder.setText(R.id.tv_time,demoListBean.getTime());
                if(demoListBean.getImageResource()!=0){
                    holder.setImageResource(R.id.iv_item_bg,demoListBean.getImageResource());
                }else if(!StringUtil.isEmpty(demoListBean.getImageUrl())){
                    holder.setImageUrl(R.id.iv_item_bg,demoListBean.getImageUrl());
                }
                holder.setOnClickListener(R.id.cardview_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        demoListBean.startActivity(context);
                    }
                });
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_basedemolist;
    }


    public abstract List<DemoListBean> initData();
}
