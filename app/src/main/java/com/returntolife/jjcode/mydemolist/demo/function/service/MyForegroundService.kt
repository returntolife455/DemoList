package com.returntolife.jjcode.mydemolist.demo.function.service

import android.app.Activity
import android.content.Intent
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
class MyForegroundService:Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startForegroundService(Intent(this,MyService::class.java))
    }


}