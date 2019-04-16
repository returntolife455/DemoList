package com.returntolife.jjcode.mydemolist.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/4/16.
 * des:
 * version:1.0.0
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    private Context mContext;


    public MainViewPagerAdapter(FragmentManager fragmentManager, Context context, List<Fragment> fragmentList){
        super(fragmentManager);
        mContext=context;
        this.fragmentList=fragmentList;
        this.mContext=context;

    }

    @Override
    public int getCount() {
        return fragmentList==null?0:fragmentList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

}
