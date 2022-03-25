package com.returntolife.jjcode.mydemolist.demo.function.whiteService

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import androidx.annotation.RequiresApi
import android.view.View
import android.widget.Toast
import com.returntolife.jjcode.mydemolist.R

/**
 * @Author : hejiajun
 * @Time   : 2021/4/25
 * @Email  : hejiajun@lizhi.fm
 * @Desc   :
 */
class WhiteServiceActivity:Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white_service)




    }

    //后台运行限制
    fun sleep(view: View) {
        KeepCompactUtils.noSleepSet(this)
        MarkActivity.startActivity(this@WhiteServiceActivity)
    }

    //自启
    fun start(view: View) {
        KeepCompactUtils.daemonSet(this)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun  isIgnoringBatteryOptimizations():Boolean {
        var isIgnoring = false
        val powerManager =  getSystemService(Context.POWER_SERVICE) as? PowerManager
        powerManager?.let {
            isIgnoring = it.isIgnoringBatteryOptimizations(packageName)
        }
        return isIgnoring
    }
}