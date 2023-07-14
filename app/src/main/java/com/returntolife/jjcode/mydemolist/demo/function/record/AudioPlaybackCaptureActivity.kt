package com.returntolife.jjcode.mydemolist.demo.function.record

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class AudioPlaybackCaptureActivity : AppCompatActivity() {

    companion object{
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 998
    }
    private val LOG_TAG: String = "AudioRecordTest"


    private var recordButton: RecordButton? = null

    private var playButton: PlayButton? = null



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode ==  REQUEST_RECORD_AUDIO_PERMISSION){

            data?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && resultCode == Activity.RESULT_OK) {

                    val intent = Intent(this, AudioPlaybackCaptureService::class.java)
                    intent.putExtra("resultData", data)
                    intent.putExtra("resultCode", resultCode)
                    startForegroundService(intent)

                }
            }

        }
    }


    private fun onRecord(start: Boolean) = if (start) {
        AudioRecordManager.startRecord()
    } else {
        AudioRecordManager.stopRecord()
    }

    private fun onPlay(start: Boolean) = if (start) {
        AudioRecordManager.startPlaying()
    } else {
        AudioRecordManager.stopPlaying()
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


        AudioRecordManager.stopRecord()
        val mProjectionManager =
            this.getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val screenCaptureIntent = mProjectionManager.createScreenCaptureIntent()
        startActivityForResult(screenCaptureIntent, REQUEST_RECORD_AUDIO_PERMISSION)

    }




}