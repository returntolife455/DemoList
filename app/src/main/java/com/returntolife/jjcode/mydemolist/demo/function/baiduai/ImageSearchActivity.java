package com.returntolife.jjcode.mydemolist.demo.function.baiduai;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.ImageSearchBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJiaJun on 2019/8/1.
 * Email:455hejiajun@gmail
 * des:
 */
public class ImageSearchActivity extends AppCompatActivity {

    private Button mBtnHandler;
    private RecyclerView mRv;
    private ImageSearchAdapter mAdapter;
    private List<ImageSearchBean> mDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagesearch);

        mBtnHandler=findViewById(R.id.btn_handle);
        mRv=findViewById(R.id.rv_imagesearch);

        initListener();

        initData();
    }

    private void initListener() {
        mBtnHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAdapter!=null){
                    mAdapter.handleImage();
//                    new ImageSearchHelper().searchByRetrofit();
                }
            }
        });
    }

    private void initData() {
        mDataList=new ArrayList<>();
        mDataList.add(new ImageSearchBean(R.drawable.ic_imagesearch_anima,false,ImageSearchBean.SEARCH_TYPE_ANIMAL));
        mDataList.add(new ImageSearchBean(R.drawable.ic_imagesearch_car,false,ImageSearchBean.SEARCH_TYPE_CAR));
        mDataList.add(new ImageSearchBean(R.drawable.ic_imagesearch_ingredient,false,ImageSearchBean.SEARCH_TYPE_INGREDIENT));

        mAdapter=new ImageSearchAdapter(this,R.layout.item_imagesearch,mDataList);

        mRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRv.setAdapter(mAdapter);

    }



}
