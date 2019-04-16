package com.returntolife.jjcode.mydemolist.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.returntolife.jjcode.mydemolist.R;

import butterknife.ButterKnife;

/**
 * Created by HeJiaJun on 2019/2/25.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class TestActivity extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


    }
}
