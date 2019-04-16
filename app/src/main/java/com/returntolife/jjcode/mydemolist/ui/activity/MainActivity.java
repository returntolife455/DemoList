package com.returntolife.jjcode.mydemolist.ui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.ui.adapter.MainViewPagerAdapter;
import com.returntolife.jjcode.mydemolist.ui.fragment.FunctionDemoListFragment;
import com.returntolife.jjcode.mydemolist.ui.fragment.ImageDemoListFragment;
import com.returntolife.jjcode.mydemolist.ui.fragment.HomeDemoListFragment;
import com.returntolife.jjcode.mydemolist.ui.fragment.WidgetDemoListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ActionBarDrawerToggle mDrawerToggle;

    List<Fragment> fragmentList;
    List<String> titleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initToolBar();
        initDrawerListener();

        initData();

        initListener();
    }

    private void initListener() {
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));


    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeDemoListFragment());
        fragmentList.add(new FunctionDemoListFragment());
        fragmentList.add(new ImageDemoListFragment());
        fragmentList.add(new WidgetDemoListFragment());

        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.ic_home));
        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.ic_image));
        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.ic_widget));
        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.ic_function));

        viewpager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(),this,fragmentList));

    }

    private void initDrawerListener() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        drawer.addDrawerListener(mDrawerToggle);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawer.removeDrawerListener(mDrawerToggle);
    }
}
