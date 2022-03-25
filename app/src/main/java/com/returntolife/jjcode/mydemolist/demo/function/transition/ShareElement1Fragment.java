package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;

/**
 * Created by HeJiaJun on 2020/3/31.
 * Email:hejj@mama.cn
 * des:
 */
public class ShareElement1Fragment extends Fragment {


    public static ShareElement1Fragment newInstance() {
        ShareElement1Fragment fragment = new ShareElement1Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transition1, container, false);

        final Button btnNext=view.findViewById(R.id.btn_next);
        final TextView tvText=view.findViewById(R.id.tv_text);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                ShareElement2Fragment sharedElementFragment2 = ShareElement2Fragment.newInstance();

                Slide slideTransition = new Slide(Gravity.START);
                slideTransition.setDuration(1000);

                ChangeBounds changeBoundsTransition = new ChangeBounds();
                changeBoundsTransition.setDuration(1000);

                sharedElementFragment2.setEnterTransition(slideTransition);
                sharedElementFragment2.setAllowEnterTransitionOverlap(false);
                sharedElementFragment2.setAllowReturnTransitionOverlap(false);
                sharedElementFragment2.setSharedElementEnterTransition(changeBoundsTransition);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, sharedElementFragment2)
                        .addToBackStack(null)
                        .addSharedElement(btnNext, "fragment_btn_next")
                        .addSharedElement(tvText,"fragment_text").commit();
            }
        });

        return view;
    }


}
