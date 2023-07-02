package com.returntolife.jjcode.mydemolist.demo.function.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.returntolife.jjcode.mydemolist.R
import java.io.File


class ShareFileActivity:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_file)


        val path = getExternalFilesDir(null)?.absolutePath?:""

        val file = File(path+"/share")
        if(!file.exists()){
            file.createNewFile()
        }

        findViewById<View>(R.id.btnShareAcc).setOnClickListener {
            val uri = getFileUri("share_test_acc.aac")

            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(Intent.EXTRA_STREAM,uri)


            intent.setDataAndType(uri, "audio/*")
            startActivity(Intent.createChooser(intent,"选择你最爱的acc"))
        }

        findViewById<View>(R.id.btnShareImg).setOnClickListener {
            val uri = getFileUri("share_file_img.jpg")

            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(Intent.EXTRA_STREAM,uri)


            intent.setDataAndType(uri, "image/*")
            startActivity(intent)
//            startActivity(Intent.createChooser(intent,"选择你最爱的image"))
        }

        findViewById<View>(R.id.btnShareMp3).setOnClickListener {
            val uri = getFileUri("surgar.mp3")

            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(Intent.EXTRA_STREAM,uri)


            intent.setDataAndType(uri, "audio/*")
            startActivity(Intent.createChooser(intent,"选择你最爱的mp3"))
        }

        findViewById<View>(R.id.btnShareVideo).setOnClickListener {
            val uri = getFileUri("婚礼视频1.mp4")

            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(Intent.EXTRA_STREAM,uri)

            val mime = MimeTypeMap.getFileExtensionFromUrl("婚礼视频1.mp4")
            intent.setDataAndType(uri, "video/*")
            startActivity(Intent.createChooser(intent,"选择视频小mp4"))

        }

        findViewById<View>(R.id.btnShareVideoBig).setOnClickListener {
            val uri = getFileUri("A2214271131684678472130.mp4")

            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(Intent.EXTRA_STREAM,uri)

            val mime = MimeTypeMap.getFileExtensionFromUrl("A2214271131684678472130.mp4")
            intent.setDataAndType(uri, "video/*")
            startActivity(Intent.createChooser(intent,"选择视频大mp4"))

        }
    }

    fun getFileUri(fileName:String):Uri{
        val imagePath: File = File(getExternalFilesDir(null), "/share_test")
        val newFile = File(imagePath, fileName)


        val contentUri: Uri = FileProvider.getUriForFile(
            this,
            "com.returntolife.jjcode.mydemolist.fileprovider", newFile
        )
//        this.grantUriPermission("com.tencent.mm",contentUri,Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        return  contentUri
    }
}