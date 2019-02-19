package com.returntolife.jjcode.mydemolist.mdwidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.returntolife.jjcode.mydemolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HeJiaJun on 2018/9/30.
 * des:
 * version:1.0.0
 */

public class MDWidgetActivity extends Activity {


    @BindView(R.id.btn_coordinatorlayout)
    Button btnCoordinatorlayout;

    @BindView(R.id.btn_bottomsheet)
    Button btnBottomsheet;


    @BindView(R.id.btn_fab)
    Button mFab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_widget);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.btn_coordinatorlayout, R.id.btn_bottomsheet,R.id.btn_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_coordinatorlayout:
                startActivity(new Intent(this, CoordinatorlayoutActivity.class));
                break;
            case R.id.btn_bottomsheet:
                startActivity(new Intent(this, BottomSheetActivity.class));
                break;
            case R.id.btn_fab:
                startActivity(new Intent(this, FloatingActionButtonActivity.class));
                break;
        }
    }
}
