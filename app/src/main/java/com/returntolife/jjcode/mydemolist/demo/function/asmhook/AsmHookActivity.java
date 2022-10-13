package com.returntolife.jjcode.mydemolist.demo.function.asmhook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

/**
 * @Author : hejiajun
 * @Time : 2021/5/7
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class AsmHookActivity extends Activity {

    private final String TAG = AsmHookActivity.this.getClass().getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asm_hook);


    }


    @SuppressLint("ThreadUsage")
    public void testHookThread(View view) {
        new Thread(() -> LogUtil.d("单独new的线程，使用asm hook替换为自定义线程")).start();
    }
}
