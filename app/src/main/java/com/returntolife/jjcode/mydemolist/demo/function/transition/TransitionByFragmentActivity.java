package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.Button;

import com.returntolife.jjcode.mydemolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * des fragment之间的转场动画
 */
public class TransitionByFragmentActivity extends AppCompatActivity {


    @BindView(R.id.btn_switch)
    Button btnSwitch;
    private Transition1Fragment fragment1;
    private Transition2Fragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_fragment);
        ButterKnife.bind(this);

        fragment1 = Transition1Fragment.newInstance();
        fragment2 = Transition2Fragment.newInstance();


    }

    private boolean isSwitch;

    @OnClick(R.id.btn_switch)
    public void onViewClicked() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if(!isSwitch){
                Fade fadeTransition = new Fade();
                fadeTransition.setDuration(1000);
                fragment1.setEnterTransition(fadeTransition);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, fragment1)
                        .commit();
            }else {
                Slide slideTransition = new Slide(Gravity.BOTTOM);
                slideTransition.setDuration(1000);
                fragment2.setEnterTransition(slideTransition);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, fragment2)
                        .commit();

            }
        }

        isSwitch=!isSwitch;
    }
}
