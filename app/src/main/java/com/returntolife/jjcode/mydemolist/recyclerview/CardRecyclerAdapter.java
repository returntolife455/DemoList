package com.returntolife.jjcode.mydemolist.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tools.jj.tools.utils.Dp2PxUtil;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/4/8.
 * des:
 * version:1.0.0
 */
public class CardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> data;

    private Context mContext;

    public CardRecyclerAdapter(Context mContext,List<Integer> data){
        this.mContext=mContext;
        this.data=data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView=new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width= Dp2PxUtil.dip2px(mContext,300);
        layoutParams.height=Dp2PxUtil.dip2px(mContext,400);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        return new RecyclerView.ViewHolder(imageView) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }
    

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView= (ImageView) holder.itemView;
        imageView.setImageResource(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }
}
