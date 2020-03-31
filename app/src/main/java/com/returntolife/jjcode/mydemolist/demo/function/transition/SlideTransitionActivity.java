package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;

import com.returntolife.jjcode.mydemolist.R;

import butterknife.ButterKnife;


public class SlideTransitionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            slide.setSlideEdge(Gravity.LEFT);
            //创建Activity时的动画
            getWindow().setEnterTransition(slide);
            //销毁Activity时的过渡动画
            getWindow().setReturnTransition(slide);
            //从前台切换到后台时的动画
            getWindow().setExitTransition(slide);
            //Activity从后台切换到前台的动画
            getWindow().setReenterTransition(slide);
        }
    }


}
