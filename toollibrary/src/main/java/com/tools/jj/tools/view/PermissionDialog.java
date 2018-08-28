package com.tools.jj.tools.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.tools.jj.tools.R;

/**
 * Created by jj on 2018/2/5.
 */

public class PermissionDialog extends Dialog {


    private IPermissionDialogListener listener;

    public PermissionDialog(@NonNull Context context, IPermissionDialogListener listener) {
        super(context, R.style.Dialog_PermissionDialog);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_permission);

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancel();
            }
        });
        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.go();
            }
        });
    }




    public interface IPermissionDialogListener {
        void cancel();

        void go();
    }
}
