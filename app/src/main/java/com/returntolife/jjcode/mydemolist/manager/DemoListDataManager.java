package com.returntolife.jjcode.mydemolist.manager;

import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.bean.DemoListBean;
import com.returntolife.jjcode.mydemolist.demo.function.AnnotateMvp.AnnotateActivity;
import com.returntolife.jjcode.mydemolist.demo.function.accessibility.AccessibilityActivity;
import com.returntolife.jjcode.mydemolist.demo.function.aidl.AIDLClientActivity;
import com.returntolife.jjcode.mydemolist.demo.function.alibabaAlpha.AlphaActivity;
import com.returntolife.jjcode.mydemolist.demo.function.asmhook.AsmHookActivity;
import com.returntolife.jjcode.mydemolist.demo.function.audiofocus.AudioFocusActivity;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.ImageSearchActivity;
import com.returntolife.jjcode.mydemolist.demo.function.changetheme.ChangeThemeActivity;
import com.returntolife.jjcode.mydemolist.demo.function.coroutine.CoroutineTest1;
import com.returntolife.jjcode.mydemolist.demo.function.databinding.DataBindingActivity;
import com.returntolife.jjcode.mydemolist.demo.function.databinding.DataBindingAdapterActivity;
import com.returntolife.jjcode.mydemolist.demo.function.databinding.DataBindingFActivity;
import com.returntolife.jjcode.mydemolist.demo.function.imageslider.ImageSliderActivity;
import com.returntolife.jjcode.mydemolist.demo.function.location.LocationTestActivity;
import com.returntolife.jjcode.mydemolist.demo.function.mergeActivity.MergeActivity;
import com.returntolife.jjcode.mydemolist.demo.function.multiItem.MultiItemActivity;
import com.returntolife.jjcode.mydemolist.demo.function.mvvm.MvvmDemoActivity;
import com.returntolife.jjcode.mydemolist.demo.function.mycontentprovider.ContentProviderClientActivity;
import com.returntolife.jjcode.mydemolist.demo.function.record.RecordActivity;
import com.returntolife.jjcode.mydemolist.demo.function.resumedownload.ResumeDownloadActivity;
import com.returntolife.jjcode.mydemolist.demo.function.section.SectionActivity;
import com.returntolife.jjcode.mydemolist.demo.function.service.MyForegroundService;
import com.returntolife.jjcode.mydemolist.demo.function.share.ShareFileActivity;
import com.returntolife.jjcode.mydemolist.demo.function.test.TestActivity;
import com.returntolife.jjcode.mydemolist.demo.function.transition.TransitionActivity;
import com.returntolife.jjcode.mydemolist.demo.function.webview.WebViewTestActivity;
import com.returntolife.jjcode.mydemolist.demo.function.whiteService.WhiteServiceActivity;
import com.returntolife.jjcode.mydemolist.demo.image.InvertedImageActivity;
import com.returntolife.jjcode.mydemolist.demo.image.PickingPictureActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.BottomSheetActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.CoordinatorlayoutActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.DemoBottomSheetDialogFragment;
import com.returntolife.jjcode.mydemolist.demo.widget.FloatingActionButtonActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.NestedScrollingDemoActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.adrecyclerview.AdListActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.customview.CustomViewActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.drawlockscreen.DrawLockScreenActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.imageswitcher.ImageSwitcherActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.motionevent.MotionEventActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.putorefresh.PutoRefreshActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.recyclerview.CardLayoutActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.scaleview.ScaleViewActivity;
import com.returntolife.jjcode.mydemolist.demo.widget.superedittext.SuperEditTextActivity;


import com.returntolife.jjcode.mydemolist.demo.widget.transparent.TransparentActivity;
import com.tools.jj.tools.utils.DateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Create by JiaJun He on 2019/4/16$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class DemoListDataManager {

    private static final DemoListDataManager instance=new DemoListDataManager();


    private List<DemoListBean> demoListBeanList;

    private DemoListDataManager(){
        initDemoListData();
    }

    public static DemoListDataManager getInstance(){
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
        demoListBeanList.add(new DemoListBean("AIDL","AIDL的使用","2019-05-31",R.drawable.bg_test1,DemoListBean.TYPE_FUNCTION, AIDLClientActivity.class));
        demoListBeanList.add(new DemoListBean("注解注入","mvp模式中用注解注入p层","2019-06-01",R.drawable.bg_annotate,DemoListBean.TYPE_FUNCTION, AnnotateActivity.class));
        demoListBeanList.add(new DemoListBean("多布局列表","自定义adapter","2019-06-01",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, MultiItemActivity.class));
//        demoListBeanList.add(new DemoListBean("Aspect","按钮重复点击限制","2019-06-25",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, AspectButtonActivity.class));
        demoListBeanList.add(new DemoListBean("MVVM","简单的demo","2019-07-04",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, MvvmDemoActivity.class));
        demoListBeanList.add(new DemoListBean("图像识别","基于百度AI","2019-08-01",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, ImageSearchActivity.class));
        demoListBeanList.add(new DemoListBean("仿饿了么点餐滑动效果","recyclerview联动","2019-08-26",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, SectionActivity.class));
        demoListBeanList.add(new DemoListBean("MVP单元测试","初级单元测试demo","2019-08-26",R.drawable.bg_md_test,DemoListBean.TYPE_FUNCTION, TestActivity.class));
        demoListBeanList.add(new DemoListBean("转场动画","转场动画，共享元素","2020-03-31",DemoListBean.TYPE_FUNCTION, TransitionActivity.class));
        demoListBeanList.add(new DemoListBean("图片切换特效","","2020-04-12",DemoListBean.TYPE_FUNCTION, ImageSliderActivity.class));
        demoListBeanList.add(new DemoListBean("无障碍服务","","2020-04-12",DemoListBean.TYPE_FUNCTION, AccessibilityActivity.class));
        demoListBeanList.add(new DemoListBean("kotlin协程","","2020-07-31",DemoListBean.TYPE_FUNCTION, CoroutineTest1.class));
        demoListBeanList.add(new DemoListBean("dataBinding","","2020-07-31",DemoListBean.TYPE_FUNCTION, DataBindingActivity.class));
        demoListBeanList.add(new DemoListBean("dataBinding2","framgent中引用","2020-09-14",DemoListBean.TYPE_FUNCTION, DataBindingFActivity.class));
        demoListBeanList.add(new DemoListBean("dataBinding3","adapter中引用","2020-09-14",DemoListBean.TYPE_FUNCTION, DataBindingAdapterActivity.class));
        demoListBeanList.add(new DemoListBean("webview","hybird","2021-04-23",DemoListBean.TYPE_FUNCTION, WebViewTestActivity.class));
        demoListBeanList.add(new DemoListBean("foregroundService","前台服务测试","2021-04-23",DemoListBean.TYPE_FUNCTION, MyForegroundService.class));
        demoListBeanList.add(new DemoListBean("WhiteService","后台运行白名单","2021-04-25",DemoListBean.TYPE_FUNCTION, WhiteServiceActivity.class));
        demoListBeanList.add(new DemoListBean("AudioFocus","音频焦点","2021-05-07",DemoListBean.TYPE_FUNCTION, AudioFocusActivity.class));
        demoListBeanList.add(new DemoListBean("Location","地理位置","2021-05-20",DemoListBean.TYPE_FUNCTION, LocationTestActivity.class));
        demoListBeanList.add(new DemoListBean("alibabaAlpha","任务执行框架","2021-05-20",DemoListBean.TYPE_FUNCTION, AlphaActivity.class));
        demoListBeanList.add(new DemoListBean("merge","merge的使用","2021-05-24",DemoListBean.TYPE_FUNCTION, MergeActivity.class));
        demoListBeanList.add(new DemoListBean("ASM","asm相关demo","2022-10-12",DemoListBean.TYPE_FUNCTION, AsmHookActivity.class));
        demoListBeanList.add(new DemoListBean("Share","原生文件分享","2023-7-12",DemoListBean.TYPE_FUNCTION, ShareFileActivity.class));
        demoListBeanList.add(new DemoListBean("Record","Record Voices","2023-7-14",DemoListBean.TYPE_FUNCTION, RecordActivity.class));

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
        demoListBeanList.add(new DemoListBean("NestedScrolling机制","自定义控件","2020-06-04", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET, NestedScrollingDemoActivity.class));
        demoListBeanList.add(new DemoListBean("自定义View系列","自定义控件","2020-12-14", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET, CustomViewActivity.class));
        demoListBeanList.add(new DemoListBean("ImageSwitcher","系统控件","2021-04-26", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET, ImageSwitcherActivity.class));
        demoListBeanList.add(new DemoListBean("内容渐变效果","自定义控件","2023-03-10", R.drawable.bg_md_test,DemoListBean.TYPE_WIDGET, TransparentActivity.class));

        Collections.sort(demoListBeanList, new Comparator<DemoListBean>() {
            @Override
            public int compare(DemoListBean o1, DemoListBean o2) {
                long time1= DateUtil.parseToLong(o1.getTime(),"yyyy-MM-dd");
                long time2= DateUtil.parseToLong(o2.getTime(),"yyyy-MM-dd");

                if(time1<time2){
                    return 1;
                }else if(time1>time2){
                    return -1;
                }else {
                    return 0;
                }
            }});
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
