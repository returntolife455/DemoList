package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;

import com.returntolife.jjcode.mydemolist.R;


public class FadeTransitionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(3000);
            //创建Activity时的动画
            getWindow().setEnterTransition(fade);
            //销毁Activity时的过渡动画
            getWindow().setReturnTransition(fade);
            //从前台切换到后台时的动画
            getWindow().setExitTransition(fade);
            //Activity从后台切换到前台的动画
            getWindow().setReenterTransition(fade);
        }
    }


}
