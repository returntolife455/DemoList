package com.returntolife.jjcode.mydemolist.demo.widget.scaleview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ScaleViewActivity extends Activity {

    @BindView(R.id.my_loopscaleview)
    MyLoopScaleView myLoopscaleview;
    @BindView(R.id.tv_fre)
    TextView tvFre;
    @BindView(R.id.btn_fre)
    Button btnFre;
    @BindView(R.id.btn_channel)
    Button btnChannel;
    @BindView(R.id.btn_vlue)
    Button btnVlue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        ButterKnife.bind(this);

        myLoopscaleview.setOnValueChangeListener(new MyLoopScaleView.OnValueChangeListener() {
            @Override
            public void OnValueChange(float newValue) {
                LogUtil.d("newValue="+newValue);
                tvFre.setText(newValue+"");
            }
        });
    }

    @OnClick({R.id.btn_fre, R.id.btn_channel, R.id.btn_vlue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fre:
            //    myLoopscaleview.scrollToTargetValue(89.5f);
                myLoopscaleview.scrollToTargetValue(100f);
                break;
            case R.id.btn_channel:
                if(myLoopscaleview.isFM()){
                    myLoopscaleview.scrollToTargetValue(999,false);
                }else {
                    myLoopscaleview.scrollToTargetValue(105f,true);
                }
                break;
            case R.id.btn_vlue:
                Toast.makeText(this, myLoopscaleview.getCurrsorValue()+"", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
