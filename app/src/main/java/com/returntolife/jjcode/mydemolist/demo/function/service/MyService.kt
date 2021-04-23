package com.returntolife.jjcode.mydemolist.demo.function.service

import android.R
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.returntolife.jjcode.mydemolist.main.activity.MainActivity
import com.tools.jj.tools.utils.LogUtil


/**
 * @Author : hejiajun
 * @Time   : 2021/4/23
 * @Email  : hejiajun@lizhi.fm
 * @Desc   :
 */
class MyService:Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()

        val id = "channel_001";
        val name = "name";
        val notificationManager:NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel =  NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification =  Notification.Builder(this)
                .setChannelId(id)
                .setContentTitle("睡眠")
                .setContentText("正在记录你的睡眠")
                .setSmallIcon(R.drawable.sym_def_app_icon).build()
        } else {
            val  notificationBuilder =  NotificationCompat.Builder(this)
                .setContentTitle("睡眠")
                .setContentText("正在记录你的睡眠")
                .setSmallIcon(R.drawable.sym_def_app_icon)
                .setOngoing(true)
                .setChannelId(id);//无效
            notification = notificationBuilder.build()
        }
        startForeground(1, notification)

        var index=1L
        Thread{
            while (true){
                LogUtil.d("index=${index++}")
                Thread.sleep(1000)
            }
        }.start()
    }

}