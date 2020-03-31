package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;

import com.returntolife.jjcode.mydemolist.R;


public class ExplodeTransitionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            //创建Activity时的动画
            getWindow().setEnterTransition(explode);
            //销毁Activity时的过渡动画
            getWindow().setReturnTransition(explode);
            //从前台切换到后台时的动画
            getWindow().setExitTransition(explode);
            //Activity从后台切换到前台的动画
            getWindow().setReenterTransition(explode);
        }
    }


}
