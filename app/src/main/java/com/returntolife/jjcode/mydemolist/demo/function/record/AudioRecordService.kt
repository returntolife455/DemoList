package com.returntolife.jjcode.mydemolist.demo.function.record

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Context.MEDIA_PROJECTION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioPlaybackCaptureConfiguration
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.tools.jj.tools.utils.LogUtil
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class AudioRecordService : Service() {

//    /**
//     * 录音数队列
//     */
//    private val audioQueue = ConcurrentLinkedQueue<ByteArray>()
//    private val mExecutor = ThreadPoolExecutor(
//        2, 2, 60, TimeUnit.SECONDS,
//        LinkedBlockingQueue()
//    )




    override fun onCreate() {
        super.onCreate()

        AudioRecordManager.context = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotification()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val currentResultCode = intent?.getIntExtra("resultCode", 0)?:0
        val resultData = intent?.getParcelableExtra<Intent>("resultData")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AudioRecordManager.initAudioRecord(currentResultCode, resultData)
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