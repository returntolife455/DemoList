package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntolife.jjcode.mydemolist.R;

/**
 * Created by HeJiaJun on 2020/3/31.
 * Email:hejj@mama.cn
 * des:
 */
public class Transition1Fragment extends Fragment {




    public static Transition1Fragment newInstance() {
        Transition1Fragment fragment = new Transition1Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transition1, container, false);


        return view;
    }


}
