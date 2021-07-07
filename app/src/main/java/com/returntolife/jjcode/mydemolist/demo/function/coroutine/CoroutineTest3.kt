package com.returntolife.jjcode.mydemolist.demo.function.coroutine

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.returntolife.jjcode.mydemolist.R
import com.returntolife.jjcode.mydemolist.demo.function.lambdaTest.test2
import com.tools.jj.tools.utils.LogUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import org.json.JSONObject
import java.lang.IllegalArgumentException
import kotlin.coroutines.CoroutineContext

/**
 * Created by HeJiaJun on 2020/7/31.
 * Email:hejj@mama.cn
 * des:
 */
class CoroutineTest3:Activity(),CoroutineScope   {

    private val handler = CoroutineExceptionHandler { _, e ->
        LogUtil.d("协程任务  顶层异常处理 e:${e.printStackTrace()}")
    }

    private val topParentJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = topParentJob + Dispatchers.Main + handler


    internal class TestClass{
        var  isCancel:Boolean = false
    }


    private var testClass:TestClass?=null


    private var testJob:Job?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_coroutine)

        GlobalScope.launch {

        }
    }

    private fun start(): Int {
        var index = 0
        for (i in 0..100) {
            index += i
        }
        return index
    }


    private suspend fun test() {
        LogUtil.d(1)
        try {
            supervisorScope { //①
                LogUtil.d(2)
                launch(Dispatchers.IO) { // ②
                    LogUtil.d(3)
                    launch(Dispatchers.IO) { // ③
                        LogUtil.d(4)
                        delay(100)
                        throw ArithmeticException("Hey!!")
                    }
                    LogUtil.d(5)
                }
                LogUtil.d(6)
                val job = launch(Dispatchers.IO) { // ④
                    LogUtil.d(7)
                    try {
                        delay(1000)
                    }catch (e:java.lang.Exception){
                        LogUtil.d("四. $e")
                    }

                }
                try {
                    LogUtil.d(8)
                    job.join()
                    LogUtil.d("9")
                } catch (e: Exception) {
                    LogUtil.d("10. $e")
                }
            }
            LogUtil.d(11)
        } catch (e: Exception) {
            LogUtil.d("12. $e")
        }
        LogUtil.d(13)
    }


    private suspend fun compute(i: Int): Int {
        LogUtil.d("协程任务${i} 开始")
        delay(5000)
        LogUtil.d("协程任务 内容值${i}")
        return i
    }



    private suspend fun requestDataSuccess():String{
        delay(4000)
        LogUtil.d("requestDataSuccess end")
//        throw IllegalArgumentException("requestDataSuccess")
        return "requestDataSuccess"
    }

    private suspend fun requestDataFail():String{
        delay(2000)
        throw IllegalArgumentException("requestDataFail")
        return "requestDataFail"
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    fun request(view: View) {

//        testJob?.let {
//            if(it.isCancelled){
//                LogUtil.d("the testJob was cancel")
//            }else{
//                LogUtil.d("the testJob is not cancel")
//                testJob?.cancel()
//            }
//        }


        testJob=GlobalScope.launch(Dispatchers.IO+handler) {
            val requestTest1 = async { delay(5000)
                LogUtil.d("requestTest1  ")
            }

            val requestTest2 = async { delay(1000)
                LogUtil.d("requestTest2  ")
            }

            requestTest1.await()
            requestTest2.await()

            LogUtil.d("GlobalScope end")
            withContext(Dispatchers.IO){
                LogUtil.d("GlobalScope withContext")
            }
        }
    }

    fun cancel(view: View) {
        testJob?.cancel()
    }
}