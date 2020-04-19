package com.returntolife.jjcode.mydemolist.demo.function.imageslider;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Create by JiaJunHe on 2020/4/12 13:05
 * Email 455hejiajun@gmail.com
 * Description:
 * Version: 1.0
 */
public class PageTransformer1 implements ViewPager.PageTransformer {

    private static final float ROT_MOD = -15f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        final float width = page.getWidth();
        final float rotation = ROT_MOD * position;

        page.setPivotX(width * 0.5f);
        page.setPivotX(width * 0.5f);
        page.setTranslationX(0f);
        page.setRotation(rotation);

    }
}
