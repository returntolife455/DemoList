package com.returntolife.jjcode.mydemolist.demo.function.coroutine

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.tools.jj.tools.utils.LogUtil
import kotlinx.coroutines.*
import org.json.JSONObject

/**
 * Created by HeJiaJun on 2020/7/31.
 * Email:hejj@mama.cn
 * des:
 */
class CoroutineTest1:Activity(), CoroutineScope by MainScope() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        launch(Dispatchers.Main) {
            LogUtil.d(Thread.currentThread().name)
            try {
                val result1 = async { fetchData() }
                val result2 = async { fetchData2("haha")}
                LogUtil.d(result1.await()+result2.await())

            } catch (e: Exception) {
                LogUtil.d(e.toString())

            }


        }

        LogUtil.d("GlobalScope.launch after")
    }


    suspend fun fetchData():String{
        withContext(Dispatchers.IO) {
            LogUtil.d(Thread.currentThread().name)
            delay(2000)
            LogUtil.d("fetchData end")
        }
        return "content"

    }

    suspend fun fetchData2(content:String):String{
        withContext(Dispatchers.IO) {
            LogUtil.d(Thread.currentThread().name)
            delay(3000)
            LogUtil.d("fetchData2 end")
        }
        return "fetchData2--$content"
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}