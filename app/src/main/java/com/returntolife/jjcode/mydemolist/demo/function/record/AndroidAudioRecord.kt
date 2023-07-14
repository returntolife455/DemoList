package com.returntolife.jjcode.mydemolist.demo.function.record

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioRecordingConfiguration
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.tools.jj.tools.utils.LogUtil
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.Executors

class AndroidAudioRecord(private val context: Context) {


    private val executor = Executors.newSingleThreadExecutor()

    private var mAudioRecord: AudioRecord? = null
    private var mRecorderBufferSize = 0
    private var mAudioData: ByteArray? =null

    /*默认数据*/
    private val mSampleRateInHZ = 8000 //采样率

    private val mAudioFormat = AudioFormat.ENCODING_PCM_16BIT //位数

    private val mChannelConfig = AudioFormat.CHANNEL_IN_MONO //声道

    var isRecording = false


    private var init = false
    /**
     * 初始化录音器
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun initAudioRecord(resultCode: Int, intent: Intent?) {
        if(isRecording){
            return
        }

        mRecorderBufferSize =
            AudioRecord.getMinBufferSize(
                mSampleRateInHZ,
                mChannelConfig,
                mAudioFormat
            )
        mAudioData = ByteArray(320)


        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
//                mAudioRecord = builder.build()
                mAudioRecord = AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, mSampleRateInHZ, 16, 2, mRecorderBufferSize)

                LogUtil.d("mAudioRecord id=${mAudioRecord!!.audioSessionId}")
                mAudioRecord?.registerAudioRecordingCallback(executor,object:
                    AudioManager.AudioRecordingCallback() {

                    override fun onRecordingConfigChanged(configs: MutableList<AudioRecordingConfiguration>?) {
                        super.onRecordingConfigChanged(configs)
                        configs?:return

                        val service = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                        service.mode.let {
                            LogUtil.d("service mode=${it}")
                        }


                        configs.forEach {

                            LogUtil.d("isClientSilenced=${it.isClientSilenced} \n" +
                                    "id=${it.clientAudioSessionId} \n" +
                                    "effect=${it.effects.size} \n" +
                                    "format=${it.format} \n" +
                                    "audioSource=${it.audioSource}")

                        }

                    }
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d( "录音器错误, 录音器初始化失败")
        }

        LogUtil.d( "录音器初始化成功")
        init = true
        startRecord()
    }


    /**
     * 开始录音
     */
    fun startRecord() {
        if(init.not()){
            return
        }

        if (isRecording) {
            LogUtil.d("已经在录音了")
            stopRecord()
            return
        }
        LogUtil.d("开始录音了")
        isRecording = true
        //承接音频数据的字节数组
        val mAudioData = ByteArray(320)
        //保存到本地录音文件名
        val tmpName = System.currentTimeMillis().toString()
        //新建文件，承接音频数据
        val tmpFile = AudioRecordManager.createFile(context,"$tmpName.pcm")
        //新建文件，后面会把音频数据转换为.wav格式，写入到该文件
        val tmpOutFile = AudioRecordManager.createFile(context,"$tmpName.wav")
        //开始录音
        mAudioRecord?.startRecording()
        Thread {
            try {
                val outputStream = FileOutputStream(tmpFile!!.absoluteFile)
                while (isRecording) {
                    //循环从音频硬件读取音频数据录制到字节数组中
                    mAudioRecord?.read(mAudioData, 0, mAudioData.size)
                    //将字节数组写入到tmpFile文件
                    outputStream.write(mAudioData)
                }
                outputStream.close()

                AudioRecordManager.pcmToWave(tmpFile.absolutePath, tmpOutFile!!.absolutePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }


    fun stopRecord(){
        if (!isRecording) {
            LogUtil.d("已结束，别点了")
            return;
        }
        LogUtil.d("已结束")
        isRecording = false
        mAudioRecord?.stop()
    }

}