package com.returntolife.jjcode.mydemolist.demo.function.databinding


import android.content.ClipData.Item
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.returntolife.jjcode.mydemolist.R
import com.returntolife.jjcode.mydemolist.databinding.ItemDatabindingBinding


/**
 * Created by HeJiaJun on 2020/9/14.
 * Email:hejj@mama.cn
 * des:
 */
class DataBindingfAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<DataBindingfAdapter.ViewHolder>(){


    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vh: ViewHolder = holder

        vh.databinding?.tvExample?.text="position${position}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_databinding, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View):
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
         val databinding=DataBindingUtil.bind<ItemDatabindingBinding>(itemView)
    }




}