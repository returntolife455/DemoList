package com.returntolife.jjcode.mydemolist.demo.function.imageslider;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

import java.util.ArrayList;

/**
 * Create by JiaJunHe on 2020/4/12 10:08
 * Email 455hejiajun@gmail.com
 * Description:
 * Version: 1.0
 */
public class ImageSliderActivity extends AppCompatActivity {

    ArrayList<Integer>mGoodsList;
    ArrayList<ImageView> mivGoodsList;
    GoodsAdapter mAdapter;
    private ViewPager mVp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        mVp=findViewById(R.id.vp_content);

        initView();
    }

    private void initView(){
        //准备数据源
        mGoodsList=new ArrayList<>();
        mGoodsList.add(R.drawable.test1);
        mGoodsList.add(R.drawable.test2);
        mGoodsList.add(R.drawable.test3);
        mGoodsList.add(R.drawable.test4);
        mGoodsList.add(R.drawable.test5);

        mivGoodsList=new ArrayList<>();
        for(int i=0;i<mGoodsList.size();i++){
            ImageView iv=new ImageView(this);
            iv.setImageResource(mGoodsList.get(i));
            mivGoodsList.add(iv);
        }
        mAdapter=new GoodsAdapter(this,mivGoodsList);
        mVp.setAdapter(mAdapter);
        mVp.setPageTransformer(true,new PageTransformer1());
        mVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(position%2==0){
                    LogUtil.d("onPageSelected PageTransformer1");
                    mVp.setPageTransformer(true,new PageTransformer1());
                }else {
                    mVp.setPageTransformer(true,new PageTransformer2());
                    LogUtil.d("onPageSelected PageTransformer2");
                }
            }
        });
    }


    //准备适配器类
    class GoodsAdapter extends PagerAdapter {
        Context context;
        ArrayList<ImageView> ivGoodsList;
        public GoodsAdapter(Context context, ArrayList<ImageView> ivGoodsList){
            this.context=context;
            this.ivGoodsList=ivGoodsList;
        }

        @Override
        public int getCount() {
            return ivGoodsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            ImageView imageView=ivGoodsList.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView imageView=(ImageView)object;
            container.removeView(imageView);
        }
    }
}
