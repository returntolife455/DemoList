package com.returntolife.jjcode.mydemolist.demo.function.webview

import android.webkit.JavascriptInterface
import android.widget.Toast
import com.returntolife.jjcode.mydemolist.AppApplication

/**
 * @Author : hejiajun
 * @Time   : 2021/4/23
 * @Email  : hejiajun@lizhi.fm
 * @Desc   :
 */
class JsKit {

    // 定义JS需要调用的方法，被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    fun hello(jsData:String) {
        Toast.makeText(AppApplication.pAppContext, "hello world", Toast.LENGTH_SHORT).show()
    }
}