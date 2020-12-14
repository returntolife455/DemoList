package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义View 系列
 */
public class CustomViewActivity extends AppCompatActivity {
    private PieView pieView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        pieView = findViewById(R.id.pieView);
        initData();
    }

    private void initData() {
        List<PieData> dataList = new ArrayList<>();
        dataList.add(new PieData("1", 20));
        dataList.add(new PieData("2", 10));
        dataList.add(new PieData("3", 50));
        dataList.add(new PieData("4", 5));
        dataList.add(new PieData("5", 15));
        pieView.updateData(dataList);
    }
}
