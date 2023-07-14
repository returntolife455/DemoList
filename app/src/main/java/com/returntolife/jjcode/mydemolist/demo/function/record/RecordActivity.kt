package com.returntolife.jjcode.mydemolist.demo.function.record

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.returntolife.jjcode.mydemolist.R

class RecordActivity: AppCompatActivity() {

    companion object{
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 999
    }


    private var permissions: Array<String> = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.READ_CALL_LOG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        findViewById<View>(R.id.btnMediaProjection).setOnClickListener {
            startActivity(Intent(this@RecordActivity,AudioPlaybackCaptureActivity::class.java))
        }

        findViewById<View>(R.id.btnCall).setOnClickListener {
            startActivity(Intent(this@RecordActivity,RecordAccessibilityActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_RECORD_AUDIO_PERMISSION){
            grantResults.forEach {
                if(it != PackageManager.PERMISSION_GRANTED ){
                    finish()
                    return
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        AudioRecordManager.stopRecord()
        AudioRecordManager.stopPlaying()
    }
}