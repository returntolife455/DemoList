package com.returntolife.jjcode.mydemolist.demo.function.whiteService

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.returntolife.jjcode.mydemolist.R

/**
 * @Author : hejiajun
 * @Time   : 2021/4/25
 * @Email  : hejiajun@lizhi.fm
 * @Desc   :
 */
class MarkActivity:Activity() {

    companion object{
        fun startActivity(context: Context){
            val intent=with(Intent(context,MarkActivity::class.java)){
                this
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark)
    }
}