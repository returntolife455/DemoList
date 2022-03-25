package com.returntolife.jjcode.mydemolist.demo.function.databinding

import android.app.Activity
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.returntolife.jjcode.mydemolist.R
import com.returntolife.jjcode.mydemolist.databinding.ActivityDataBindingAdapterBinding

/**
 * Created by HeJiaJun on 2020/9/14.
 * Email:hejj@mama.cn
 * des:
 */
class DataBindingAdapterActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_binding_adapter)

        val binding:ActivityDataBindingAdapterBinding=DataBindingUtil.setContentView(this,R.layout.activity_data_binding_adapter)

        binding.rvContent.layoutManager=
            androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.rvContent.adapter=DataBindingfAdapter()
    }
}