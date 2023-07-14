package com.returntolife.jjcode.mydemolist.demo.function.record

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi


class AudioPlaybackCaptureService : Service() {


    override fun onCreate() {
        super.onCreate()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotification()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val currentResultCode = intent?.getIntExtra("resultCode", 0)?:0
        val resultData = intent?.getParcelableExtra<Intent>("resultData")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AudioRecordManager.initAudioRecord(currentResultCode, resultData,0)
        }

        return super.onStartCommand(intent, flags, startId)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotification() {
        val builder: Notification.Builder
        val channelID = "MRecordService"
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelID, "录音服务", NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true) //设置提示灯
        channel.lightColor = Color.RED //设置提示灯颜色
        channel.setShowBadge(true) //显示logo
        channel.description = "MRecordService Notification" //设置描述
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC //设置锁屏可见 VISIBILITY_PUBLIC=可见
        manager.createNotificationChannel(channel)
        builder = Notification.Builder(this, channelID)
        val notification = builder.setAutoCancel(false)
            .setContentTitle("录音服务") //标题
            .setContentText("录音服务正在运行...") //内容
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(com.returntolife.jjcode.mydemolist.R.drawable.bg_annotate) //设置小图标
            .setLargeIcon(BitmapFactory.decodeResource(resources,com.returntolife.jjcode.mydemolist.R.drawable.bg_annotate))//设置大图标
            .build()
        startForeground(1, notification)
    }




    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}