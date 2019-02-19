package com.returntolife.jjcode.mydemolist.mdwidget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
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

public class BottomSheetActivity extends AppCompatActivity {


    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    @BindView(R.id.btn_expand)
    Button btnExpand;
    @BindView(R.id.btn_hide)
    Button btnHide;
    @BindView(R.id.btn_collapse)
    Button btnCollapse;
    private BottomSheetBehavior mBottomSheetBehavior;

    private BottomSheetDialog bottomSheetDialog;

    private DemoBottomSheetDialogFragment mDemoBottomSheetDialogFragment;

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

    public void showDialog() {
        if(null==bottomSheetDialog){
            bottomSheetDialog = new BottomSheetDialog(this);
        }
        bottomSheetDialog.setContentView(R.layout.dialog_md_widget_bottomsheet);
        bottomSheetDialog.show();
    }

    public void hideDialog() {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.hide();
        }
    }

    public void showDialogFragment() {
        mDemoBottomSheetDialogFragment = DemoBottomSheetDialogFragment.newInstance();
        mDemoBottomSheetDialogFragment.show(getSupportFragmentManager(), "demoBottom");
    }

    public void hideDialogFragment() {
        if (mDemoBottomSheetDialogFragment != null) {
            mDemoBottomSheetDialogFragment.dismiss();
        }
    }



    @OnClick({R.id.btn_expand, R.id.btn_hide, R.id.btn_collapse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_expand:
                //showDialog();
                expandBottomSheet();
               // showDialogFragment();
                break;
            case R.id.btn_hide:
               // hideDialog();
                hideBottomSheet();
                //hideDialogFragment();
                break;
            case R.id.btn_collapse:
                collapseBottomSheet();
                break;
        }
    }
}
