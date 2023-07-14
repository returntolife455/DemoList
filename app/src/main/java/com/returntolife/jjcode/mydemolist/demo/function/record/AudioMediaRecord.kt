package com.returntolife.jjcode.mydemolist.demo.function.record

import android.media.AudioManager
import android.media.AudioRecordingConfiguration
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.returntolife.jjcode.mydemolist.AppApplication
import com.tools.jj.tools.utils.LogUtil
import java.io.IOException
import java.util.concurrent.Executors

object AudioMediaRecord {

    private val executor = Executors.newSingleThreadExecutor()
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    private const val FILE_NAME = "audiorecordtest.3gp"

    private var isStartRecord = false
    private var isPlaying = false

    @RequiresApi(Build.VERSION_CODES.Q)
    fun startRecord() {
        if (isStartRecord) {
            showToast("it is recoding")
            return
        }

        val fileName = "${AppApplication.pAppContext.externalCacheDir?.absolutePath}/${FILE_NAME}"
        recorder = MediaRecorder().apply {
            //通话录音模式
            setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            registerAudioRecordingCallback(executor, object :
                AudioManager.AudioRecordingCallback() {

                override fun onRecordingConfigChanged(configs: MutableList<AudioRecordingConfiguration>?) {
                    super.onRecordingConfigChanged(configs)
                    configs ?: return

                    configs.forEach {

                        LogUtil.d(
                            "isClientSilenced=${it.isClientSilenced} \n" +
                                    "devices=$${it.audioDevice.address} \n" +
                                    "effect=${it.effects.size} \n" +
                                    "format=${it.format} \n" +
                                    "audioSource=${it.audioSource}"
                        )
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

            isStartRecord = true
        }
    }

    fun stopRecord() {
        recorder?.release()
        recorder = null
        isStartRecord = false

        showToast("stopped record")

        LogUtil.d("stopRecord")
    }

    private fun showToast(toast: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(AppApplication.pAppContext, toast, Toast.LENGTH_SHORT).show()
        }
    }

    fun startPlaying() {
        if (isPlaying) {
            showToast("it is playing")
            return
        }
        val fileName = "${AppApplication.pAppContext.externalCacheDir?.absolutePath}/${FILE_NAME}"
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {

            }
        }
        isPlaying = true
    }

    fun stopPlaying() {
        player?.release()
        player = null

        showToast("stopped play")
    }
}