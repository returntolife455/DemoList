package com.returntolife.jjcode.mydemolist.demo.function.section;

import android.content.Context;
import android.support.annotation.Nullable;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HeJiaJun on 2019/8/8.
 * Email:455hejiajun@gmail
 * des:
 */
public class ContentAdapter extends CommonRecyclerAdapter<ContentBean> {


    private onAddClickListener onAddClickListener;

    public ContentAdapter(Context context, List<ContentBean> datas) {
        super(context,R.layout.item_section_content,datas);
    }

    public void setOnAddClickListener(ContentAdapter.onAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
    }

    /**
     * 获取类型第一个出现的位置
     *
     * @param type
     * @return
     */
    public int getPosByItemType(int type) {
        int pos = -1;
        if (mDatas != null && mDatas.size() != 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).typeBean.typeId == type) {
                    pos = i;
                    break;
                }
            }
        }
        return pos;
    }




    public void updataData(@Nullable List<ContentBean> data) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }

        if (data != null && data.size() > 0) {
            mDatas.clear();
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    protected void convert(BaseRecyclerViewHolder holder, ContentBean contentBean, int position) {
        holder.setImageResource(R.id.cv_avatar,R.drawable.bg_md_test);
        holder.setText(R.id.tv_circle_name,contentBean.contentName);super.convert(holder, contentBean, position);
    }

    public interface onAddClickListener {
        void click(ContentBean circleBean, int position);
    }
}
