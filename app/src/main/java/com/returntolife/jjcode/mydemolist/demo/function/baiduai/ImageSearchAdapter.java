package com.returntolife.jjcode.mydemolist.demo.function.baiduai;

import android.content.Context;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.ImageSearchBean;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;
import com.tools.jj.tools.utils.LogUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HeJiaJun on 2019/8/1.
 * Email:hejj@mama.cn
 * des:
 */
public class ImageSearchAdapter extends CommonRecyclerAdapter<ImageSearchBean> {

    private ImageSearchHelper imageSearchHelper;

    public ImageSearchAdapter(Context context, int layoutId, List<ImageSearchBean> datas) {
        super(context, layoutId, datas);
        imageSearchHelper=new ImageSearchHelper();
    }


    @Override
    protected void convert(final BaseRecyclerViewHolder holder, final ImageSearchBean imageSearchBean, int position) {
        holder.setImageResource(R.id.iv_bg,imageSearchBean.getImageRes());

        if(imageSearchBean.isHandler()){
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    String result=imageSearchHelper.imageSearch(imageSearchBean.getImageRes(),imageSearchBean.getType());
                    emitter.onNext(result);
                }
            }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    holder.setText(R.id.tv_des,s);
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.d("ImageSearchAdapter handle imge e="+e.toString());
                }

                @Override
                public void onComplete() {

                }
            });
        }

    }

    public void handleImage(){
        if(mDatas!=null&&mDatas.size()>0){
            for (ImageSearchBean mData : mDatas) {
                mData.setHandler(true);
            }
        }
        notifyDataSetChanged();
    }
}
