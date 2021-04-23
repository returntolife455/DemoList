package com.returntolife.jjcode.mydemolist.demo.function.webview

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import com.returntolife.jjcode.mydemolist.R
import com.tools.jj.tools.utils.LogUtil
import kotlinx.android.synthetic.main.activity_web_test.*
import kotlinx.coroutines.delay


/**
 * Created by HeJiaJun on 2020/7/31.
 * Email:hejj@mama.cn
 * des:
 */
class WebViewTestActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_test)

        btnJs.setOnClickListener {
            web.loadUrl("javascript:callJS()")
        }

        btnJs2.setOnClickListener {
            web.evaluateJavascript("javascript:callJS2()"
            ) {
                toast("webView CallBack=${it}")
            }
        }

        initWebView()
    }

    private fun initWebView() {
        web.addJavascriptInterface(JsKit(), "jskit")
        with(web.settings) {
            javaScriptEnabled = true
        }

        web.webViewClient = object : WebViewClient() {

            //该重载方法不建议使用了，7.0系统以上已经摒弃了
            //shouldOverrideUrlLoading(WebView view, String url)此方法，
            //如果要拦截URL，需要做兼容性处理，重写
            //shouldOverrideUrlLoading(WebView view, WebResourceRequest request)方法，
            //获取得到的可正常使用的URL
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return if(handleUri(Uri.parse(url))){
                    true
                }else{
                    super.shouldOverrideUrlLoading(view, url)
                }
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val uri =if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request?.url
                } else {
                    Uri.parse(request.toString())
                }
                return if(handleUri(uri)){
                    true
                }else{
                    super.shouldOverrideUrlLoading(view, request)
                }

            }
        }

        web.webChromeClient = object : WebChromeClient() {
            override fun onJsPrompt(
                view: WebView?,
                url: String?,
                message: String?,
                defaultValue: String?,
                result: JsPromptResult?
            ): Boolean {
                // 步骤2：根据协议的参数，判断是否是所需要的url(原理同方式2)
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //传入进来的 url="js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                val uri = Uri.parse(message);
                // 如果url的协议 = 预先约定的 js 协议,就解析往下解析参数
                if (uri.scheme == "js") {
                    // 如果 authority = 预先约定协议里的webview，即代表符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.authority == "demo") {

                        // 步骤3：执行JS所需要调用的逻辑
                        toast("js调用了Android的方法3")
                        // 可以在协议上带有参数并传递到Android上
//                        HashMap<String, String> params = new HashMap<>();
//                        Set<String> collection = uri.getQueryParameterNames();

                        //参数result:代表消息框的返回值(输入值)
                        result?.confirm("Android回调给JS的数据为onJsPrompt")
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        }



        // 加载JS代码
        web.loadUrl("file:///android_asset/javascript.html")
    }

    private fun toast(str:String) {
        Toast.makeText(
            this@WebViewTestActivity,
            str,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun handleUri(uri: Uri?):Boolean {
        uri?.let {
            if (it.scheme == "js") {
                // 如果 authority = 预先约定协议里的webview，即代表都符合约定的协议
                // 所以拦截url,下面JS开始调用Android需要的方法
                if (uri.authority == "webview") {

                    // 步骤3：执行JS所需要调用的逻辑
                    toast("js调用了Android的方法")
                    // 可以在协议上带有参数并传递到Android上
                    val params = HashMap<String, String>()
                    val collection = uri.queryParameterNames
                    LogUtil.d(collection)

                    val result = "Android回调给JS的数据为useid=123456";
                    web.loadUrl("javascript:returnResult(\"$result\")")
                }
                return true
            }
        }
        return false
    }
}