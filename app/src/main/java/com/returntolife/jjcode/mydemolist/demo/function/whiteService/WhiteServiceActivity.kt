package com.returntolife.jjcode.mydemolist.demo.function.whiteService

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.support.annotation.RequiresApi
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && !isIgnoringBatteryOptimizations()){
            KeepCompactUtils.noSleepSet(this)
        }else{
            Toast.makeText(this@WhiteServiceActivity,"已关闭限制",Toast.LENGTH_SHORT).show()
        }
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