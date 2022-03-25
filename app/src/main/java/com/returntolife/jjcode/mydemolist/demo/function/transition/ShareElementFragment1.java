package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
