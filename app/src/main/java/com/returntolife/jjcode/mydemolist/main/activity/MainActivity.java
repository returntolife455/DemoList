package com.returntolife.jjcode.mydemolist.main.activity;


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
import com.returntolife.jjcode.mydemolist.main.adapter.MainViewPagerAdapter;
import com.returntolife.jjcode.mydemolist.main.fragment.FunctionDemoListFragment;
import com.returntolife.jjcode.mydemolist.main.fragment.ImageDemoListFragment;
import com.returntolife.jjcode.mydemolist.main.fragment.HomeDemoListFragment;
import com.returntolife.jjcode.mydemolist.main.fragment.WidgetDemoListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


//    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.drawer)
    DrawerLayout drawer;
//    @BindView(R.id.tablayout)
    TabLayout tablayout;
//    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ActionBarDrawerToggle mDrawerToggle;

    List<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        drawer=findViewById(R.id.drawer);
        tablayout=findViewById(R.id.tablayout);
        viewpager=findViewById(R.id.viewpager);
        toolbar=findViewById(R.id.toolbar);

        initToolBar();
        initDrawerListener();

        initData();

        initListener();
    }

    private void initListener() {
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeDemoListFragment.getInstance());
        fragmentList.add(ImageDemoListFragment.getInstance());
        fragmentList.add(WidgetDemoListFragment.getInstance());
        fragmentList.add(FunctionDemoListFragment.getInstance());

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
