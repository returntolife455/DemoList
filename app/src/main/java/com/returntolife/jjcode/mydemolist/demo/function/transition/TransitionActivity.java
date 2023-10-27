package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.returntolife.jjcode.mydemolist.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class TransitionActivity extends AppCompatActivity {

    Scene mScene1, mScene2;
    boolean isScene1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);

        Slide fade = new Slide();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        fade.setDuration(50000);
        fade.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.d("hjj_Test", "onTransitionStart");
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Log.d("hjj_Test", "onTransitionEnd");
            }

            @Override
            public void onTransitionCancel(Transition transition) {
                Log.d("hjj_Test", "onTransitionCancel");
            }

            @Override
            public void onTransitionPause(Transition transition) {
                Log.d("hjj_Test", "onTransitionPause");
            }

            @Override
            public void onTransitionResume(Transition transition) {
                Log.d("hjj_Test", "onTransitionResume");
            }
        });

        getWindow().setExitTransition(fade);


    }


    private void toFade() {
        Intent intent = new Intent(TransitionActivity.this, FadeTransitionActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this).toBundle());
    }


    private void toExplode() {
        Intent intent = new Intent(TransitionActivity.this, ExplodeTransitionActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this).toBundle());
    }

    private void toSlide() {
        Intent intent = new Intent(TransitionActivity.this, SlideTransitionActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this).toBundle());
    }

    @OnClick({R.id.btn_slide, R.id.btn_explode, R.id.btn_fade, R.id.btn_share_element, R.id.btn_transition_fragment, R.id.btn_share_element_fragment, R.id.btn_scene})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_slide) {
            toSlide();
        } else if (view.getId() == R.id.btn_explode) {
            toExplode();
        } else if (view.getId() == R.id.btn_fade) {
            toFade();
        } else if (view.getId() == R.id.btn_share_element) {
            toShareElementByActivity();
        } else if (view.getId() == R.id.btn_transition_fragment) {
            toTransitionByFragment();
        } else if (view.getId() == R.id.btn_share_element_fragment) {
            toShareElementByFragment();
        } else if (view.getId() == R.id.btn_scene) {
            toScene();
        }
    }

    private void toScene() {
        ViewGroup root = findViewById(R.id.fl_scene);
        mScene1 = Scene.getSceneForLayout(root, R.layout.layout_scene_1, this);
        mScene2 = Scene.getSceneForLayout(root, R.layout.layout_scene_2, this);

//        AutoTransition changeBounds = new AutoTransition();
//
//        changeBounds.setDuration(1000);
        if (isScene1) {
            TransitionManager.go(mScene2, new CustomTransition());
        } else {
            TransitionManager.go(mScene1, new CustomTransition());
        }

        isScene1 = !isScene1;
    }

    private void toShareElementByFragment() {
        Intent intent = new Intent(TransitionActivity.this, ShareElementByFragmentActivity.class);
        startActivity(intent);
    }

    private void toShareElementByActivity() {
        Intent intent = new Intent(TransitionActivity.this, ShareElementActivity.class);
        View transition_view = findViewById(R.id.btn_share_element);
        String transition_name = "btn_share_element";
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this, transition_view, transition_name).toBundle());
    }

    private void toTransitionByFragment() {
        Intent intent = new Intent(TransitionActivity.this, TransitionByFragmentActivity.class);
        startActivity(intent);
    }
}
