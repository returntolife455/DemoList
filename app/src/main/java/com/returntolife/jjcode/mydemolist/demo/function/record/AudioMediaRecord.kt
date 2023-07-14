package com.returntolife.jjcode.mydemolist.demo.function.record

import android.media.AudioManager
import android.media.AudioRecordingConfiguration
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.returntolife.jjcode.mydemolist.AppApplication
import com.tools.jj.tools.utils.LogUtil
import java.io.IOException
import java.util.concurrent.Executors

object AudioMediaRecord {

    private val executor = Executors.newSingleThreadExecutor()
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

   @RequiresApi(Build.VERSION_CODES.Q)
   fun startRecord(){
       val fileName = "${AppApplication.pAppContext.externalCacheDir?.absolutePath}/audiorecordtest2.3gp"
       recorder = MediaRecorder().apply {
           setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
           setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
           setOutputFile(fileName)
           setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
           registerAudioRecordingCallback(executor,object:
               AudioManager.AudioRecordingCallback() {

               override fun onRecordingConfigChanged(configs: MutableList<AudioRecordingConfiguration>?) {
                   super.onRecordingConfigChanged(configs)
                   configs?:return

                   configs.forEach {

                       LogUtil.d("isClientSilenced=${it.isClientSilenced} \n" +
                               "devices=$${it.audioDevice.address} \n" +
                               "effect=${it.effects.size} \n" +
                               "format=${it.format} \n" +
                               "audioSource=${it.audioSource}")
                   }

               }
           })
           try {
               prepare()
           } catch (e: IOException) {
               LogUtil.d("prepare() failed")
           }
           LogUtil.d("start record")
           start()
       }
   }

    fun stopRecord(){
        recorder?.release()
        recorder = null
        LogUtil.d("stopRecord")
    }

     fun startPlaying() {
         val fileName = "${AppApplication.pAppContext.externalCacheDir?.absolutePath}/audiorecordtest2.3gp"
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {

            }
        }
    }

     fun stopPlaying() {
        player?.release()
        player = null
    }
}