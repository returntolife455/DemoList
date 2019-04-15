package com.returntolife.jjcode.mydemolist;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;


import com.tools.jj.tools.activity.permission.BasePermissionActivity;

public class MainActivity extends BasePermissionActivity {


    @Override
    public String[] setRequestString() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
    }



    @Override
    public void initActivity() {
        startActivity(new Intent(this,DemoListActivity.class));
        finish();
        View view=new View(this);

        //透明视图动画
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setStartOffset(300);
        alphaAnimation.setFillEnabled(true);
        alphaAnimation.setFillEnabled(true);
        view.startAnimation(alphaAnimation);

        /**
         * 动画集合 ： true使用同一个插值器，false使用每个动画独自的插值器
         */
        AnimationSet animationSet=new AnimationSet(true);
        AlphaAnimation aAnim=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setStartOffset(300);

        TranslateAnimation tAnim=new TranslateAnimation(0,200,0,300);
        tAnim.setDuration(3000);

        animationSet.addAnimation(aAnim);
        animationSet.addAnimation(tAnim);

        view.startAnimation(animationSet);

        //给动画设置监听
        RotateAnimation rAnim=new RotateAnimation(0,360);
        rAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
