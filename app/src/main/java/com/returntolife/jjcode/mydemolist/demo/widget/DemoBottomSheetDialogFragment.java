package com.returntolife.jjcode.mydemolist.demo.widget;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntolife.jjcode.mydemolist.R;

/**
 * Created by HeJiaJun on 2018/10/8.
 * des:
 * version:1.0.0
 */

public class DemoBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static DemoBottomSheetDialogFragment newInstance() {
        return new DemoBottomSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_md_widget_bottomsheet, container, false);
    }

}
