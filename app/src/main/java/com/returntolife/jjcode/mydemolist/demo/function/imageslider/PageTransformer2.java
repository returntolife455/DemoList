package com.returntolife.jjcode.mydemolist.demo.function.imageslider;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

/**
 * Create by JiaJunHe on 2020/4/12 13:05
 * Email 455hejiajun@gmail.com
 * Description:
 * Version: 1.0
 */
public class PageTransformer2 implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setPivotX(position < 0f ? page.getWidth() : 0f);
        page.setPivotY(page.getHeight() * 0.5f);
        page.setRotationY(90f * position);

    }
}
