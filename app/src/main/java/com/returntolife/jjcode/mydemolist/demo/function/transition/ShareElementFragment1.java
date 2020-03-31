package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.returntolife.jjcode.mydemolist.R;


public class ShareElementFragment1 extends Fragment {


    public static ShareElementFragment1 newInstance(String name) {

        Bundle args = new Bundle();

        args.putString("name", name);
        ShareElementFragment1 fragment = new ShareElementFragment1();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_element_1, container, false);
        final String name =  getArguments().getString("name");



        return view;
    }
}
