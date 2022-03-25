package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义View 系列
 */
public class CustomViewActivity extends AppCompatActivity implements View.OnClickListener {
    private PieView pieView;
    private RadarView radarView;
    private ConfirmView confirmView;
    private PasswordView passwordView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        pieView = findViewById(R.id.pieView);
        radarView = findViewById(R.id.radarView);
        confirmView = findViewById(R.id.confirmView);
        confirmView.setOnClickListener(this);
        passwordView = findViewById(R.id.passwordView);
        initData();
        passwordView.setListener(new PasswordView.onInputListener() {
            @Override
            public void onFinish(String text) {
                Toast.makeText(CustomViewActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        List<PieData> dataList = new ArrayList<>();
        dataList.add(new PieData("1", 20));
        dataList.add(new PieData("2", 10));
        dataList.add(new PieData("3", 50));
        dataList.add(new PieData("4", 5));
        dataList.add(new PieData("5", 15));
        pieView.updateData(dataList);

        List<RadarData> radarDataList = new ArrayList<>();
        radarDataList.add(new RadarData("1", 2));
        radarDataList.add(new RadarData("2", 3));
        radarDataList.add(new RadarData("3", 1));
        radarDataList.add(new RadarData("4", 4));
        radarDataList.add(new RadarData("5", 5));
        radarView.updateData(radarDataList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmView:
                confirmView.confirm();
                break;
        }
    }
}
