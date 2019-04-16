package com.returntolife.jjcode.mydemolist.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntolife.jjcode.mydemolist.R;

/**
 * Created by HeJiaJun on 2019/4/16.
 * des:
 * version:1.0.0
 */
public abstract class BaseDemoListFragment extends Fragment {

    protected View rootView;
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_basedemolist,container,false);
        }

        return  rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    public abstract void initView();
}
