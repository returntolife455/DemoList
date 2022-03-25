package com.returntolife.jjcode.mydemolist.demo.widget;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.main.adapter.MainViewPagerAdapter;
import com.returntolife.jjcode.mydemolist.main.fragment.FunctionDemoListFragment;
import com.returntolife.jjcode.mydemolist.main.fragment.HomeDemoListFragment;
import com.returntolife.jjcode.mydemolist.main.fragment.ImageDemoListFragment;
import com.returntolife.jjcode.mydemolist.main.fragment.WidgetDemoListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NestedScrollingDemoActivity extends AppCompatActivity {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_demo);
        ButterKnife.bind(this);
        initData();
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

        viewpager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(),this, fragmentList));

    }
}
