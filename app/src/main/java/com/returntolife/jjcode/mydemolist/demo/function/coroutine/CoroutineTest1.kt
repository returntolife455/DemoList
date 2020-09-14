package com.returntolife.jjcode.mydemolist.demo.function.coroutine

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.tools.jj.tools.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by HeJiaJun on 2020/7/31.
 * Email:hejj@mama.cn
 * des:
 */
class CoroutineTest1:Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var job = GlobalScope.launch(Dispatchers.Main) {
            var content = fetchData()
            LogUtil.d(content)
        }
        LogUtil.d("coroutine after")
    }


    suspend fun fetchData():String{
        delay(2000)
        return "content"
    }
}