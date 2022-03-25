package com.returntolife.jjcode.mydemolist.demo.widget.recyclerview.cardlayout;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/4/8.
 * des:
 * version:1.0.0
 */
class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> data;

    private Context mContext;

    public RecyclerViewAdapter(Context context,List<String> data){
        this.mContext=context;
        this.data=data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView=new TextView(mContext);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height=200;
        params.setMargins(0,10,0,0);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(Color.RED);

        return new RecyclerView.ViewHolder(textView) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView= (TextView) holder.itemView;
        textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }



    public List<String> getDataList(){
        return data;
    }


}
