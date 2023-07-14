package com.returntolife.jjcode.mydemolist.demo.function.record

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioRecordingConfiguration
import android.media.AudioTrack
import android.media.MediaRecorder.AudioSource.MIC
import android.media.MediaRecorder.AudioSource.VOICE_COMMUNICATION
import android.media.MediaRecorder.AudioSource.VOICE_RECOGNITION
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.tools.jj.tools.utils.LogUtil
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.Executors

@SuppressLint("StaticFieldLeak")
object AudioRecordManager {

    private val executor = Executors.newSingleThreadExecutor()
    private var mAudioTrack: AudioTrack? = null
    private var mAudioRecord: AudioRecord? = null
    private var mRecorderBufferSize = 0
    private var mAudioData: ByteArray? =null

    /*默认数据*/
    private val mSampleRateInHZ = 8000 //采样率

    private val mAudioFormat = AudioFormat.ENCODING_PCM_16BIT //位数

    private val mChannelConfig = AudioFormat.CHANNEL_IN_MONO //声道

     var isRecording = false


     lateinit var context: Context

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
            AudioRecord.getMinBufferSize(mSampleRateInHZ, mChannelConfig, mAudioFormat)
        mAudioData = ByteArray(320)




        //设置应用程序录制系统音频的能力

//        val builder = AudioRecord.Builder()
//        builder.setAudioFormat(AudioFormat.Builder()
//            .setSampleRate(mSampleRateInHZ) //设置采样率（一般为可选的三个-> 8000Hz 、16000Hz、44100Hz）
//            .setChannelMask(AudioFormat.CHANNEL_IN_FRONT) //音频通道的配置，可选的有-> AudioFormat.CHANNEL_IN_MONO 单声道，CHANNEL_IN_STEREO为双声道，立体声道，选择单声道就行
//            .setEncoding(AudioFormat.ENCODING_PCM_16BIT).build()) //音频数据的格式，可选的有-> AudioFormat.ENCODING_PCM_8BIT，AudioFormat.ENCODING_PCM_16BIT
//            .setBufferSizeInBytes(mRecorderBufferSize) //设置最小缓存区域
//            .setAudioSource(MIC)

//        val mediaProjectionManager = context.getSystemService(Service.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
//        val mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, intent)
//        val config = AudioPlaybackCaptureConfiguration.Builder(mediaProjection)
//            .addMatchingUsage(AudioAttributes.USAGE_MEDIA) //设置捕获多媒体音频
//            .addMatchingUsage(AudioAttributes.USAGE_GAME) //设置捕获游戏音频
//
//            .build()
//        //将 AudioRecord 设置为录制其他应用播放的音频
//        builder.setAudioPlaybackCaptureConfig(config)
        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
//                mAudioRecord = builder.build()
                mAudioRecord = AudioRecord(VOICE_COMMUNICATION, mSampleRateInHZ, 16, 2, mRecorderBufferSize)

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
        val tmpFile = createFile(context,"$tmpName.pcm")
        //新建文件，后面会把音频数据转换为.wav格式，写入到该文件
        val tmpOutFile = createFile(context,"$tmpName.wav")
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
//                //将.pcm文件转换为.wav文件
                pcmToWave(tmpFile.absolutePath, tmpOutFile!!.absolutePath)
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


     fun createFile(context: Context,name: String): File? {
        val dirPath = context.externalCacheDir?.absolutePath + "/AudioRecord/"
        val file = File(dirPath)
        if (!file.exists()) {
            file.mkdirs()
        }
        val filePath = dirPath + name
        val objFile = File(filePath)
        if (!objFile.exists()) {
            try {
                objFile.createNewFile()
                return objFile
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

     fun pcmToWave(inFileName: String, outFileName: String) {
        var `in`: FileInputStream? = null
        var out: FileOutputStream? = null
        var totalAudioLen: Long = 0
        val longSampleRate = mSampleRateInHZ.toLong()
        var totalDataLen = totalAudioLen + 36
        val channels = 1 //你录制是单声道就是1 双声道就是2（如果错了声音可能会急促等）
        val byteRate = 16 * longSampleRate * channels / 8
        val data = ByteArray(mRecorderBufferSize)
        try {
            `in` = FileInputStream(inFileName)
            out = FileOutputStream(outFileName)
            totalAudioLen = `in`.channel.size()
            totalDataLen = totalAudioLen + 36
            writeWaveFileHeader(
                out,
                totalAudioLen,
                totalDataLen,
                longSampleRate,
                channels,
                byteRate
            )
            while (`in`.read(data) != -1) {
                out.write(data)
            }
            `in`.close()
            out.close()
        } catch (e: FileNotFoundException) {

            e.printStackTrace()
        } catch (e: IOException) {

            e.printStackTrace()
        }
    }

    /*
    任何一种文件在头部添加相应的头文件才能够确定的表示这种文件的格式，wave是RIFF文件结构，每一部分为一个chunk，其中有RIFF WAVE chunk，
    FMT Chunk，Fact chunk,Data chunk,其中Fact chunk是可以选择的，
     */
    @Throws(IOException::class)
    private fun writeWaveFileHeader(
        out: FileOutputStream, totalAudioLen: Long, totalDataLen: Long, longSampleRate: Long,
        channels: Int, byteRate: Long
    ) {
        val header = ByteArray(44)
        header[0] = 'R'.code.toByte() // RIFF
        header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte()
        header[3] = 'F'.code.toByte()
        header[4] = (totalDataLen and 0xffL).toByte() //数据大小
        header[5] = (totalDataLen shr 8 and 0xffL).toByte()
        header[6] = (totalDataLen shr 16 and 0xffL).toByte()
        header[7] = (totalDataLen shr 24 and 0xffL).toByte()
        header[8] = 'W'.code.toByte() //WAVE
        header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte()
        header[11] = 'E'.code.toByte()
        //FMT Chunk
        header[12] = 'f'.code.toByte() // 'fmt '
        header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte()
        header[15] = ' '.code.toByte() //过渡字节
        //数据大小
        header[16] = 16 // 4 bytes: size of 'fmt ' chunk
        header[17] = 0
        header[18] = 0
        header[19] = 0
        //编码方式 10H为PCM编码格式
        header[20] = 1 // format = 1
        header[21] = 0
        //通道数
        header[22] = channels.toByte()
        header[23] = 0
        //采样率，每个通道的播放速度
        header[24] = (longSampleRate and 0xffL).toByte()
        header[25] = (longSampleRate shr 8 and 0xffL).toByte()
        header[26] = (longSampleRate shr 16 and 0xffL).toByte()
        header[27] = (longSampleRate shr 24 and 0xffL).toByte()
        //音频数据传送速率,采样率*通道数*采样深度/8
        header[28] = (byteRate and 0xffL).toByte()
        header[29] = (byteRate shr 8 and 0xffL).toByte()
        header[30] = (byteRate shr 16 and 0xffL).toByte()
        header[31] = (byteRate shr 24 and 0xffL).toByte()
        // 确定系统一次要处理多少个这样字节的数据，确定缓冲区，通道数*采样位数
        header[32] = (1 * 16 / 8).toByte()
        header[33] = 0
        //每个样本的数据位数
        header[34] = 16
        header[35] = 0
        //Data chunk
        header[36] = 'd'.code.toByte() //data
        header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte()
        header[39] = 'a'.code.toByte()
        header[40] = (totalAudioLen and 0xffL).toByte()
        header[41] = (totalAudioLen shr 8 and 0xffL).toByte()
        header[42] = (totalAudioLen shr 16 and 0xffL).toByte()
        header[43] = (totalAudioLen shr 24 and 0xffL).toByte()
        out.write(header, 0, 44)
    }
}