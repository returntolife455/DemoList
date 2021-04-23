package com.returntolife.jjcode.mydemolist.demo.function.webview;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.returntolife.jjcode.mydemolist.AppApplication;

/**
 * @Author : hejiajun
 * @Time : 2021/4/23
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class JsKit2 {

    @JavascriptInterface
    public void hello(String jsData){
        Toast.makeText(AppApplication.pAppContext, "hello world", Toast.LENGTH_SHORT).show();
    }
}
