package com.tools.jj.tools.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.tools.jj.tools.R;

/**
 * Created by jj on 2018/2/1.
 *  基于lottie的dialog
 */

public class LoadingByLottieDialog extends Dialog {

    private LottieAnimationView lottieAnimationView;

    public LoadingByLottieDialog(@NonNull Context context) {
        super(context, R.style.Dialog_LodingDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading_layout);
        lottieAnimationView=findViewById(R.id.animation_view);
    }
}
