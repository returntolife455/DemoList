package com.returntolife.jjcode.mydemolist.mdwidget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.returntolife.jjcode.mydemolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HeJiaJun on 2018/10/8.
 * des:
 * version:1.0.0
 */

public class BottomSheetActivity extends Activity {


    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    @BindView(R.id.btn_expand)
    Button btnExpand;
    @BindView(R.id.btn_hide)
    Button btnHide;
    @BindView(R.id.btn_collapse)
    Button btnCollapse;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_widget_bottomsheet);
        ButterKnife.bind(this);

        //把这个底部菜单和一个BottomSheetBehavior关联起来
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("BottomSheet", "newState=" + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d("BottomSheet", "onSlide=" + slideOffset);
            }
        });

    }

    public void expandBottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void hideBottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void collapseBottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    @OnClick({R.id.btn_expand, R.id.btn_hide, R.id.btn_collapse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_expand:
                expandBottomSheet();
                break;
            case R.id.btn_hide:
                hideBottomSheet();
                break;
            case R.id.btn_collapse:
                collapseBottomSheet();
                break;
        }
    }
}
