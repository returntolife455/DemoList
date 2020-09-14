package com.returntolife.jjcode.mydemolist.demo.function.databinding

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.returntolife.jjcode.mydemolist.databinding.FragmentDatabindingBinding

/**
 * Created by HeJiaJun on 2020/9/14.
 * Email:hejj@mama.cn
 * des:
 */
class DataBindingFragment: Fragment() {

    private var mBinding: FragmentDatabindingBinding? = null


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding=FragmentDatabindingBinding.inflate(inflater!!)
        mBinding?.tvContent?.text="hello fragment dataBinding"
        return mBinding!!.root
    }
}