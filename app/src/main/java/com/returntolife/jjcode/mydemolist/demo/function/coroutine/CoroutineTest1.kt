package com.returntolife.jjcode.mydemolist.demo.function.coroutine

import android.app.Activity
import android.os.Bundle
import com.returntolife.jjcode.mydemolist.R
import com.tools.jj.tools.utils.LogUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @Author : hejiajun
 * @Time   : 2021/7/7
 * @Email  : hejiajun@lizhi.fm
 * @Desc   :
 */
class CoroutineTest1 : Activity(), CoroutineScope {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        LogUtil.d("coroutineExceptionHandler  e:${e.printStackTrace()}")
    }
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext get() =  Dispatchers.Default + job + coroutineExceptionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        launch {
            LogUtil.d("customScope start")

            launch {
                LogUtil.d("customScope child start")
            }

            LogUtil.d("customScope end")
        }


    }
}