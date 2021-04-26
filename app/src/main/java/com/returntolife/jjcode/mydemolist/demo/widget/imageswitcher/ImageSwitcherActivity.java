package com.returntolife.jjcode.mydemolist.demo.widget.imageswitcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.returntolife.jjcode.mydemolist.R;

/**
 * @Author : hejiajun
 * @Time : 2021/4/26
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class ImageSwitcherActivity extends Activity implements ViewSwitcher.ViewFactory {

    private int[] imageId;
    private ImageSwitcher imageSwitcher;
    private int currentPosition = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imager_switcher);

        imageId=new int[]{R.drawable.test1,R.drawable.test2,
                R.drawable.test3,R.drawable.test4,R.drawable.test5};

        imageSwitcher = findViewById(R.id.sw);
        imageSwitcher.setFactory(this);
        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setImageResource(imageId[++currentPosition%imageId.length]);
            }
        });

        imageSwitcher.setImageResource(imageId[currentPosition]);

        //设置动画
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        imageSwitcher.setOutAnimation( AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
    }

    @Override
    public View makeView() {
        final ImageView i = new ImageView(this);
        i.setBackgroundColor(0xff000000);
        i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return i;
    }
}
