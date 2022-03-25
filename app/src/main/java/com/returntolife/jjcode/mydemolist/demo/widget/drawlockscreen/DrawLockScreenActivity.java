package com.returntolife.jjcode.mydemolist.demo.widget.drawlockscreen;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.returntolife.jjcode.mydemolist.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HeJiaJun on 2018/8/28.
 * des:
 * version:1.0.0
 */

public class DrawLockScreenActivity extends Activity {


    @BindView(R.id.my_drawlockscreen)
    DrawLockScreenView myDrawlockscreen;
    @BindView(R.id.btn_again)
    Button btnAgain;
    @BindView(R.id.btn_current)
    Button btnCurrent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawlockscreen);
        ButterKnife.bind(this);

        initDrawView();
    }

    private void initDrawView() {
        myDrawlockscreen.setInterFace(new DrawLockScreenView.IOnDrawFinish() {
            @Override
            public void oneDraw() {
                btnAgain.setEnabled(true);
                btnCurrent.setEnabled(true);
            }

            @Override
            public void towDraw(boolean isOK) {
                if(isOK){
                    Toast.makeText(DrawLockScreenActivity.this, "设置成功,重头开始", Toast.LENGTH_SHORT).show();
                    myDrawlockscreen.cancel();
                    myDrawlockscreen.setTowDraw(false);
                    btnCurrent.setText("再次绘制");

                }else{
                    Toast.makeText(DrawLockScreenActivity.this, "设置失败，重头开始", Toast.LENGTH_SHORT).show();
                    myDrawlockscreen.cancel();
                    myDrawlockscreen.setTowDraw(false);
                    btnCurrent.setText("再次绘制");
                }

            }
        });
    }


    @OnClick({R.id.btn_again, R.id.btn_current})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_again:
                myDrawlockscreen.cancel();
                btnAgain.setEnabled(false);
                btnCurrent.setEnabled(false);
                break;
            case R.id.btn_current:
                myDrawlockscreen.cancel();
                myDrawlockscreen.setTowDraw(true);
                btnAgain.setEnabled(false);
                btnCurrent.setEnabled(false);
                btnCurrent.setText("确定");
                break;
        }
    }
}
