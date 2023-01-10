package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.returntolife.jjcode.mydemolist.R;

/**
 * Created by HeJiaJun on 2020/3/31.
 * Email:hejj@mama.cn
 * des:
 */
public class Transition3Fragment extends Fragment {


    private View root;
    private ImageView ivTest;

    public static Transition3Fragment newInstance() {
        Transition3Fragment fragment = new Transition3Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.fragment_transition3, container, false);
         ivTest = root.findViewById(R.id.iv_test);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {

        ValueAnimator valueAnimator=ValueAnimator.ofFloat(0f,1f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ivTest.setScaleX((Float) animation.getAnimatedValue());
                ivTest.setScaleY((Float) animation.getAnimatedValue());

            }
        });

        return valueAnimator;
    }

//    @Nullable
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {//表示是一个进入动作，比如add.show等
//            if (enter) {//普通的进入的动作
//                return AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_in);
//            } else {//比如一个已经Fragmen被另一个replace，是一个进入动作，被replace的那个就是false
//                return AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_out);
//            }
//        } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {//表示一个退出动作，比如出栈，hide，detach等
//            if (enter) {//之前被replace的重新进入到界面或者Fragment回到栈顶
//                return AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_out);
//            } else {//Fragment退出，出栈
//                return AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_out);
//            }
//        }
//        return super.onCreateAnimation(transit, enter, nextAnim);
//    }
}
