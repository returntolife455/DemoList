package com.returntolife.jjcode.mydemolist.demo.function.section;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.demo.function.multiItem.ViewHolder;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/8/8.
 * Email:hejj@mama.cn
 * des:
 */
public class TypeAdapter extends RecyclerView.Adapter<ViewHolder> {

    /**
     * 当前类型选中位置
     */
    private int currentPos = -1;
    /**
     * 当前选中类型
     */
    private int curretnTypeId;
    private OnItemClickListener listener;

    private Context mContext;
    private List<TypeBean> mDatas;

    public TypeAdapter(Context context, List<TypeBean> datas) {
        mContext=context;
        mDatas=datas;
    }

    public void updateCurrentTypeId(int typeId){
        if (curretnTypeId == typeId) {
            return;
        }
        curretnTypeId = typeId;

        int newPos=-2;
        for (int i = 0; i < mDatas.size(); i++) {
            if(typeId==mDatas.get(i).typeId){
                newPos=i;
                break;
            }
        }
        updatePos(newPos);
    }


    public String getTypeName(int typeId){
        String name="";
        for (int i = 0; i < mDatas.size(); i++) {
            if(typeId==mDatas.get(i).typeId){
                name=mDatas.get(i).typeName;
                break;
            }
        }

        return name;
    }

    private void updatePos(int newPos){
        if (currentPos == newPos || newPos < 0 || newPos >= mDatas.size()) {
            return;
        }

        int temp = currentPos;
        currentPos = newPos;
        notifyItemChanged(temp);
        notifyItemChanged(currentPos);
    }



    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getcurrentPos() {
        return currentPos;
    }

    public void updataData(@Nullable List<TypeBean> data) {
        if (data != null && data.size() > 0 && mDatas != null) {
            mDatas.clear();
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, R.layout.item_section_type);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        final TypeBean typeBean = mDatas.get(position);
        holder.setText(R.id.tv_title, typeBean.typeName);
        if (position == currentPos) {
            holder.getConvertView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.permission_dialog_line_darkgrey));
            holder.getView(R.id.v_selected).setVisibility(View.VISIBLE);
            holder.setTextSize(R.id.tv_title, 14);
            holder.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.black));
            holder.setTextTypeface(R.id.tv_title, Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            holder.getConvertView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.getView(R.id.v_selected).setVisibility(View.GONE);
            holder.setTextSize(R.id.tv_title, 12);
            holder.setTextTypeface(R.id.tv_title, Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.black));
        }
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(holder, typeBean.typeId, position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface OnItemClickListener{
        void onClick(ViewHolder holder, int typeId, int position);
    }
}
