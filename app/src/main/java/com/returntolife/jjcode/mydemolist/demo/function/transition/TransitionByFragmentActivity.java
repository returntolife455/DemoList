package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
    private Transition3Fragment fragment3;

    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_fragment);
        ButterKnife.bind(this);

        fragment1 = Transition1Fragment.newInstance();
        fragment2 = Transition2Fragment.newInstance();
        fragment3 = Transition3Fragment.newInstance();


    }


    @OnClick(R.id.btn_switch)
    public void onViewClicked() {

        if(index==0){
            Fade fadeTransition = new Fade();
            fadeTransition.setDuration(1000);
            fragment1.setEnterTransition(fadeTransition);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment1)
                    .commit();
        }else if(index==1){
            Slide exitTransition = new Slide(Gravity.TOP);
            exitTransition.setDuration(1000);
            fragment1.setExitTransition(exitTransition);

            Slide slideTransition = new Slide(Gravity.BOTTOM);
            slideTransition.setDuration(1000);


            fragment2.setEnterTransition(slideTransition);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment2)
                    .commit();
        }else if(index==2){
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment, fragment3)
                    .commit();
        }

        if(++index>=3){
            index=index%3;
        }


    }
}
