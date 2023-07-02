package com.returntolife.jjcode.mydemolist.demo.function.asmhook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

/**
 * @Author : hejiajun
 * @Time : 2021/5/7
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class AsmHookActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asm_hook);

        printDefaultText();
    }


    @SuppressLint("ThreadUsage")
    public void testHookThread(View view) {
        new Thread(() -> LogUtil.d("单独new的线程，使用asm hook替换为自定义线程")).start();
    }

    private void insertCodeTest(){
        String test1="string";

        int a=100;
        int b=200;

        LogUtil.d("insertCodeTest end");
    }

    public void printDefaultText(){
        LogUtil.d("printDefaultText");
    }
}
