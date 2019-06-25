package com.returntolife.jjcode.mydemolist.demo.function.AspectButton;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

/**
 * Created by HeJiaJun on 2019/6/25.
 * Email:hejj@mama.cn
 * des:
 */
public class AspectButtonActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspectbutton);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @SingleClick(10000)
            @Override
            public void onClick(View v) {
                Toast.makeText(AspectButtonActivity.this, "clcik", Toast.LENGTH_SHORT).show();
            }
        });

        test();
    }

    public void test(){
        LogUtil.d("test");
    }

}
