package com.returntolife.jjcode.mydemolist.demo.function.section;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.demo.function.multiItem.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:455hejiajun@gmail
 * des:
 */
public class SectionActivity extends Activity {

    private RecyclerView mRvType, mRvContent;
    private TypeAdapter mAdapterType;
    private ContentAdapter mAdapterContent;
    
    private List<TypeBean> typeBeanList=new ArrayList<>();
    private List<ContentBean> contentBeans=new ArrayList<>();

    /**
     * 标记是否主动点击圈子类型
     */
    private boolean isClickedType;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        initData();
        initView();
        initListener();

    }

    private void initData() {
        typeBeanList.add(new TypeBean(1,"饮料"));
        typeBeanList.add(new TypeBean(2,"米饭"));
        typeBeanList.add(new TypeBean(3,"面条"));
        typeBeanList.add(new TypeBean(4,"小吃"));
        typeBeanList.add(new TypeBean(5,"特价"));
        typeBeanList.add(new TypeBean(6,"套餐"));
        typeBeanList.add(new TypeBean(7,"优惠"));

        for (int i = 0; i < 10; i++) {
            contentBeans.add(new ContentBean(R.drawable.bg_md_test,"可乐",new TypeBean(1,"饮料")));
            contentBeans.add(new ContentBean(R.drawable.bg_md_test,"雪碧",new TypeBean(1,"饮料")));
        }

        for (int i = 0; i < 10; i++) {
            contentBeans.add(new ContentBean(R.drawable.bg_md_test,"叉烧饭",new TypeBean(2,"米饭")));
        }

        for (int i = 0; i < 10; i++) {
            contentBeans.add(new ContentBean(R.drawable.bg_md_test,"牛筋丸粿条",new TypeBean(3,"面条")));
        }

        for (int i = 0; i < 10; i++) {
            contentBeans.add(new ContentBean(R.drawable.bg_md_test,"鸡块",new TypeBean(4,"小吃")));
        }

        for (int i = 0; i < 10; i++) {
            contentBeans.add(new ContentBean(R.drawable.bg_md_test,"买一送二",new TypeBean(5,"特价")));
        }

        for (int i = 0; i < 10; i++) {
            contentBeans.add(new ContentBean(R.drawable.bg_md_test,"双人套餐",new TypeBean(6,"套餐")));
        }

        for (int i = 0; i < 10; i++) {
            contentBeans.add(new ContentBean(R.drawable.bg_md_test,"满100送200",new TypeBean(7,"优惠")));
        }
    }

    private void initListener() {
        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                ContentBean contentBean=mAdapterContent.getmDatas().get(firstVisibleItemPosition);
                if (isClickedType) {
                    isClickedType = false;
                } else {
                    mAdapterType.updateCurrentTypeId(contentBean.typeBean.typeId);
                }
                moveRecyclerViewToTargetPos(mRvType,mAdapterType.getcurrentPos());
            }
        });

        mAdapterType.setListener(new TypeAdapter.OnItemClickListener() {
            @Override
            public void onClick(ViewHolder holder, int typeId, int position) {
                int pos = mAdapterContent.getPosByItemType(typeId);
                if (pos >= 0) {
                    isClickedType=true;
                    mAdapterType.updateCurrentTypeId(typeId);
                    ((LinearLayoutManager) mRvContent.getLayoutManager()).scrollToPositionWithOffset(pos, 0);
                }
            }
        });
    }

    private void initView() {
        mRvType = (RecyclerView) findViewById(R.id.rv_type);
        mRvContent = (RecyclerView) findViewById(R.id.rv_content);


        mRvType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapterType = new TypeAdapter(this, typeBeanList);
        mRvType.setAdapter(mAdapterType);

        mRvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvContent.addItemDecoration(new PinnedSectionDecoration(this,new PinnedSectionDecoration.DecorationCallback() {
            @Override
            public int getGroupId(int position) {
                return mAdapterContent.getmDatas().get(position).typeBean.typeId;
            }

            @Override
            public String getGroupFirstLine(int position) {
                return mAdapterContent.getmDatas().get(position).typeBean.typeName;
            }
        }));

        mAdapterContent = new ContentAdapter(this, contentBeans);
        mRvContent.setAdapter(mAdapterContent);
    }


    private void moveRecyclerViewToTargetPos(@NonNull  RecyclerView recyclerView, int targetPos) {
        int firstItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int lastItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

        if (targetPos >= lastItem || targetPos < firstItem) {
            recyclerView.scrollToPosition(targetPos);
        }
    }
}
