package com.returntolife.jjcode.mydemolist.manager;

import android.content.Intent;

import com.returntolife.jjcode.mydemolist.AppApplication;
import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.bean.DemoListBean;
import com.returntolife.jjcode.mydemolist.demo.function.AnnotateMvp.AnnotateActivity;
import com.returntolife.jjcode.mydemolist.demo.function.AspectButton.AspectButtonActivity;
import com.returntolife.jjcode.mydemolist.demo.function.aidl.AIDLClientAcitvity;
import com.returntolife.jjcode.mydemolist.demo.function.changetheme.ChangeThemeActivity;
import com.returntolife.jjcode.mydemolist.demo.function.multiItem.MultiItemActivity;
import com.returntolife.jjcode.mydemolist.demo.function.mvvm.MvvmDemoActivity;
import com.returntolife.jjcode.mydemolist.demo.function.mycontentprovider.ContentProviderClientActivity;
import com.returntolife.jjcode.mydemolist.demo.function.mycontentprovider.MyContentProvider;
import com.returntolife.jjcode.mydemolist.demo.function.resumedownload.ResumeDownloadActivity;
import com.returntolife.jjcode.mydemolist.demo.image.InvertedImageActivity;
import com.returntolife.jjcode.mydemolist.demo.image.PickingPictureActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.BottomSheetActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.CoordinatorlayoutActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.DemoBottomSheetDialogFragment;
import com.returntolife.jjcode.mydemolist.demo.widget.FloatingActionButtonActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.adrecyclerview.AdListActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.drawlockscreen.DrawLockScreenActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.motionevent.MotionEventActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.putorefresh.PutoRefreshActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.recyclerview.CardLayoutActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.scaleview.ScaleViewActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.superedittext.SuperEditTextActivity;


import java.util.ArrayList;
import java.util.List;

/*
 * Create by JiaJun He on 2019/4/16$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class DemoListDataManager {

    private volatile  static DemoListDataManager instance;


    private List<DemoListBean> demoListBeanList;

    private DemoListDataManager(){
        initDemoListData();
    }

    public static DemoListDataManager getInstance(){
        if(instance==null){
            synchronized (DemoListDataManager.class){
                if(instance==null){
                    instance=new DemoListDataManager();
                }
            }

        }
        return instance;
    }


    public List<DemoListBean> getDemoListBeanList(){
        return demoListBeanList;
    }


    private void initDemoListData() {
        demoListBeanList=new ArrayList<>();

        //图片处理
        demoListBeanList.add(new DemoListBean("图片倒影","图片倒影处理","2017-08-12", R.drawable.bg_md_test,DemoListBean.TYPE_IMAGE,InvertedImageActivity.class));
        demoListBeanList.add(new DemoListBean("图片取色","图片取色并融入背景色效果","2018-08-21", R.drawable.bg_md_test,DemoListBean.TYPE_IMAGE,PickingPictureActivity.class));

        //功能类型
        demoListBeanList.add(new DemoListBean("断点续传","采用多线程和数据库实现断点续传","2017-08-20", R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION,ResumeDownloadActivity.class));
        demoListBeanList.add(new DemoListBean("内置主题换肤","使用内置的自定义attr实现换肤","2019-02-20", R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION,ChangeThemeActivity.class));
        demoListBeanList.add(new DemoListBean("ContentProvider","ContentProvider的基本使用案例","2019-02-21", R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, ContentProviderClientActivity.class));
        demoListBeanList.add(new DemoListBean("AIDL","AIDL的使用","2019-05-31",R.drawable.bg_test1,DemoListBean.TYPE_FUNCTION, AIDLClientAcitvity.class));
        demoListBeanList.add(new DemoListBean("注解注入","mvp模式中用注解注入p层","2019-06-01",R.drawable.bg_annotate,DemoListBean.TYPE_FUNCTION, AnnotateActivity.class));
        demoListBeanList.add(new DemoListBean("多布局列表","自定义adapter","2019-06-01",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, MultiItemActivity.class));
        demoListBeanList.add(new DemoListBean("Aspect","按钮重复点击限制","2019-06-25",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, AspectButtonActivity.class));
        demoListBeanList.add(new DemoListBean("MVVM","简单的demo","2019-07-04",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, MvvmDemoActivity.class));

        //控件类型
        demoListBeanList.add(new DemoListBean("下拉刷新","自定义listview的下拉刷新","2017-08-9", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,PutoRefreshActivity.class));
        demoListBeanList.add(new DemoListBean("Bottomsheet","material desgin控件","2018-10-08", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,BottomSheetActivity.class));
        demoListBeanList.add(new DemoListBean("Coordinatorlayout","material desgin控件","2018-09-30", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,CoordinatorlayoutActivity.class));
        demoListBeanList.add(new DemoListBean("BottomSheetDialogFragment","material desgin控件","2018-10-08", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,DemoBottomSheetDialogFragment.class));
        demoListBeanList.add(new DemoListBean("FloatingActionButton","material desgin控件","2018-10-08", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,FloatingActionButtonActivity.class));
        demoListBeanList.add(new DemoListBean("仿探探卡片滑动","自定义recyclerview的layoutmanager","2019-04-08", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,CardLayoutActivity.class));
        demoListBeanList.add(new DemoListBean("波浪调频控件","自定义控件","2019-04-12", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,ScaleViewActivity.class));
        demoListBeanList.add(new DemoListBean("绘制锁屏","自定义控件","2017-08-14", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,DrawLockScreenActivity.class));
        demoListBeanList.add(new DemoListBean("知乎广告列表","自定义控件","2019-04-24", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,AdListActivity.class));
        demoListBeanList.add(new DemoListBean("事件分发机制","自定义控件","2019-04-26", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,MotionEventActivity.class));
        demoListBeanList.add(new DemoListBean("SuperEditText","自定义控件","2019-05-13", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET,SuperEditTextActivity.class));
    }




    //获取widget列表
    public List<DemoListBean> getWidgetDemoList(){
        List<DemoListBean> data=null;

        if(demoListBeanList!=null&&demoListBeanList.size()>0){
            for (DemoListBean demoListBean : demoListBeanList) {
                if(demoListBean.getType()==DemoListBean.TYPE_WIDGET){
                    if(data==null){
                        data=new ArrayList<>();
                    }
                    data.add(demoListBean);
                }
            }
        }

        return data;
    }


    //获取功能列表
    public List<DemoListBean> getFunctionDemoList(){
        List<DemoListBean> data=null;

        if(demoListBeanList!=null&&demoListBeanList.size()>0){
            for (DemoListBean demoListBean : demoListBeanList) {
                if(demoListBean.getType()==DemoListBean.TYPE_FUNCTION){
                    if(data==null){
                        data=new ArrayList<>();
                    }
                    data.add(demoListBean);
                }
            }
        }

        return data;
    }


    //获取图片处理列表
    public List<DemoListBean> getImageDemoList(){
        List<DemoListBean> data=null;

        if(demoListBeanList!=null&&demoListBeanList.size()>0){
            for (DemoListBean demoListBean : demoListBeanList) {
                if(demoListBean.getType()==DemoListBean.TYPE_IMAGE){
                    if(data==null){
                        data=new ArrayList<>();
                    }
                    data.add(demoListBean);
                }
            }
        }

        return data;
    }
}
