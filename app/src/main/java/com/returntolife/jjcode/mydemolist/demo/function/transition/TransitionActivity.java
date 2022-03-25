package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.returntolife.jjcode.mydemolist.R;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TransitionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);
    }




    private void toFade() {
        Intent intent = new Intent(TransitionActivity.this, FadeTransitionActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this).toBundle());
        }
    }

    private void toExplode() {
        Intent intent = new Intent(TransitionActivity.this, ExplodeTransitionActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this).toBundle());
        }
    }

    private void toSlide() {
        Intent intent = new Intent(TransitionActivity.this, SlideTransitionActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this).toBundle());
        }
    }

    @OnClick({R.id.btn_slide, R.id.btn_explode, R.id.btn_fade, R.id.btn_share_element,R.id.btn_transition_fragment,R.id.btn_share_element_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_slide:
                toSlide();
                break;
            case R.id.btn_explode:
                toExplode();
                break;
            case R.id.btn_fade:
                toFade();
                break;
            case R.id.btn_share_element:
                toShareElementByActivity();
                break;
            case R.id.btn_transition_fragment:
                toTransitionByFragment();
                break;
            case R.id.btn_share_element_fragment:
                toShareElementByFragment();
                break;
        }
    }

    private void toShareElementByFragment() {
        Intent intent = new Intent(TransitionActivity.this, ShareElementByFragmentActivity.class);
        startActivity(intent);
    }

    private void toShareElementByActivity() {
        Intent intent = new Intent(TransitionActivity.this, ShareElementActivity.class);
        View transition_view = findViewById(R.id.btn_share_element);
        String transition_name = "btn_share_element";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this, transition_view, transition_name).toBundle());
        }
    }

    private void toTransitionByFragment(){
        Intent intent = new Intent(TransitionActivity.this, TransitionByFragmentActivity.class);
        startActivity(intent);
    }
}
