package com.returntolife.jjcode.mydemolist.demo.widget.adrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;

import java.util.List;

public class AdListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> datas;

    public AdListAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_ad, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (position > 0 && position % 6 == 0) {
            viewHolder.desc.setVisibility(View.GONE);
            viewHolder.title.setVisibility(View.GONE);
            viewHolder.img.setVisibility(View.VISIBLE);
        } else {
            viewHolder.desc.setVisibility(View.VISIBLE);
            viewHolder.title.setVisibility(View.VISIBLE);
            viewHolder.img.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdImageViewVersion1 img;
        TextView title;
        TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.id_iv_ad);
            title = itemView.findViewById(R.id.id_tv_title);
            desc = itemView.findViewById(R.id.id_tv_desc);
        }
    }
}
