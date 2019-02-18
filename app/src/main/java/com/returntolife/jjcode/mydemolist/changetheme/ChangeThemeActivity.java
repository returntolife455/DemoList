package com.returntolife.jjcode.mydemolist.changetheme;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

/**
 * Created by HeJiaJun on 2019/2/18.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class ChangeThemeActivity extends Activity implements View.OnClickListener{

    private Button btnDayTime,btnNight;

    private LinearLayout llParent;
    private TextView tvTip;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d("onCreate");
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyBaseTheme_Daytime);
        setContentView(R.layout.activity_changetheme);
        LogUtil.d("Themetest");

        btnDayTime=findViewById(R.id.btn_daytime);
        btnNight=findViewById(R.id.btn_night);
        btnDayTime.setOnClickListener(this);
        btnNight.setOnClickListener(this);

        tvTip=findViewById(R.id.tv_tip);
        llParent=findViewById(R.id.ll_parent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_daytime:
                setTheme(R.style.MyBaseTheme_Daytime);
                tvTip.setText("这是白天模式");
                break;
            case R.id.btn_night:
                setTheme(R.style.MyBaseTheme_Night);
                tvTip.setText("这是夜间模式");
                break;
            default:
                break;
        }

        updateUi();
    }

    private void updateUi() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        try {
            theme.resolveAttribute(R.attr.custom_attr_bg_color, typedValue, true);
            llParent.setBackgroundColor(getResources().getColor(typedValue.resourceId));

            theme.resolveAttribute(R.attr.custom_attr_btn_bg_color, typedValue, true);
            btnDayTime.setBackgroundColor(getResources().getColor(typedValue.resourceId));
            btnNight.setBackgroundColor(getResources().getColor(typedValue.resourceId));

            theme.resolveAttribute(R.attr.custom_attr_btn_text_color, typedValue, true);
            btnDayTime.setTextColor(getResources().getColor(typedValue.resourceId));
            btnNight.setTextColor(getResources().getColor(typedValue.resourceId));

            theme.resolveAttribute(R.attr.custom_attr_text_color, typedValue, true);
            tvTip.setTextColor(getResources().getColor(typedValue.resourceId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
