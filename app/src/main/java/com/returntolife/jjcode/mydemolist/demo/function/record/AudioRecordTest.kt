package com.returntolife.jjcode.mydemolist.demo.function.record

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.tools.jj.tools.utils.LogUtil
import java.io.IOException

class AudioRecordTest : AppCompatActivity() {

    private val REQUEST_RECORD_AUDIO_PERMISSION = 999
    private val REQUEST_RECORD_AUDIO_PERMISSION2 = 998
    private val LOG_TAG: String = "AudioRecordTest"
    private var fileName: String = ""

    private var recordButton: RecordButton? = null


    private var playButton: PlayButton? = null
    private var player: MediaPlayer? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_CALENDAR)



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_RECORD_AUDIO_PERMISSION){
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

            if (!permissionToRecordAccepted)
                finish()
            else{
                if(AudioRecordManager.isRecording.not()){
                    val mProjectionManager = this.getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
                    val screenCaptureIntent = mProjectionManager.createScreenCaptureIntent()
                    startActivityForResult(screenCaptureIntent, REQUEST_RECORD_AUDIO_PERMISSION2)
                }

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode ==  REQUEST_RECORD_AUDIO_PERMISSION2){

            data?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && resultCode == Activity.RESULT_OK) {
                    LogUtil.d("onActivityResult initAudioRecord")
                    val intent = Intent(this, AudioRecordService::class.java)
                    intent.putExtra("resultData", data)
                    intent.putExtra("resultCode", resultCode)
                    startForegroundService(intent)

                }
            }

        }
    }


    private fun onRecord(start: Boolean) = if (start) {
//        audioRecordService?.startRecord()
        AudioRecordManager.startRecord()
    } else {
        AudioRecordManager.startRecord()
//        audioRecordService?.startRecord()
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }



    internal inner class RecordButton(ctx: Context) : androidx.appcompat.widget.AppCompatButton(ctx) {

        var mStartRecording = true

        var clicker: OnClickListener = OnClickListener {
            onRecord(mStartRecording)
            text = when (mStartRecording) {
                true -> "Stop recording"
                false -> "Start recording"
            }
            mStartRecording = !mStartRecording
        }

        init {
            text = "Start recording"
            setOnClickListener(clicker)
        }
    }

    internal inner class PlayButton(ctx: Context) : androidx.appcompat.widget.AppCompatButton(ctx) {
        var mStartPlaying = true
        var clicker: OnClickListener = OnClickListener {
            onPlay(mStartPlaying)
            text = when (mStartPlaying) {
                true -> "Stop playing"
                false -> "Start playing"
            }
            mStartPlaying = !mStartPlaying
        }

        init {
            text = "Start playing"
            setOnClickListener(clicker)
        }
    }

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Record to the external cache directory for visibility
        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)


        recordButton = RecordButton(this)
        playButton = PlayButton(this)
        val ll = LinearLayout(this).apply {
            addView(recordButton,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0f))
            addView(playButton,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0f))
        }
        setContentView(ll)
    }

    override fun onStop() {
        super.onStop()

        player?.release()
        player = null
    }
}