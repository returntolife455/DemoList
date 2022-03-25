package com.returntolife.jjcode.mydemolist.demo.function.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.returntolife.jjcode.mydemolist.R

/**
 * Created by HeJiaJun on 2020/9/14.
 * Email:hejj@mama.cn
 * des:
 */
class DataBindingFActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_binding_f)

        val fragment=DataBindingFragment()

        fragmentManager.beginTransaction().add(R.id.fl_content,fragment).commit()
    }
}