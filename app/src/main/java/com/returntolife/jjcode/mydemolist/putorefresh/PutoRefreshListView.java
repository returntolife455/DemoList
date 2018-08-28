package com.returntolife.jjcode.mydemolist.putorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 455 on 2017/8/9.
 *
 * @author hj455
 *         version 1.0.0
 * @ClassName: MyListView
 * @Description:
 */

public class PutoRefreshListView extends ListView implements AbsListView.OnScrollListener{

   View header;
   int headerHight;
    TextView tip;
    TextView time;
    ImageView imageView;
    ProgressBar progressBar;
    Animation anim1;
    Animation anim2;

    int scrollState;  //当前view的滚动状态

    int firstVisibleItem; //可见的第一个item


    int startY;     //触摸屏幕时的高度

    boolean isRemak;   //是否可以刷新

    int state=0;            //当前header状态
    final int NONE=0;     //正常状态
    final int PULL=1;     //下拉状态
    final int RELEASE=2;  //提示刷新状态
    final int REFRESHING=3; //正在刷新状态

    IRefreshen iRefreshen; //回调刷新接口


    public PutoRefreshListView(Context context) {
        super(context);
        init(context);
    }


    public PutoRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PutoRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
    * @methodName: init
    * @Description: 初始化信息
    * @param
    * @return
    * @throws
    */
    private  void init(Context context) {
        //解析header布局文件
        header = LayoutInflater.from(context).inflate(R.layout.putorefresh_header, null);

        //测量header的高宽，具体可以查看MeasureSpec包
        int width = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int hight = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        header.measure(width, hight);

        //获取头部的高度，用于后面隐藏我下拉
        headerHight=header.getMeasuredHeight();



        tip=(TextView)header.findViewById(R.id.tip);
        time=(TextView)header.findViewById(R.id.time);
        imageView=(ImageView)header.findViewById(R.id.header_image);
        progressBar=(ProgressBar)header.findViewById(R.id.progressbar);

        anim1=new RotateAnimation(0,180, RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        anim2=new RotateAnimation(180,0, RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
        anim2.setDuration(500);
        anim2.setFillAfter(true);

        //隐藏头部文件
        toPadding(-headerHight);

        //将header添加到listview的顶部
        this.addHeaderView(header);
        //添加监听
        this.setOnScrollListener(this);
    }


    /**
      * 根据传入的高度设置header的paddingtop
      */
    private void toPadding(int i) {
        header.setPadding(
                header.getPaddingLeft(),
                i,
                header.getPaddingRight(),
                header.getPaddingBottom()
        );
        header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState=scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem=firstVisibleItem;
    }


    /*
    *触摸事件处理，分三种情况，按下，移动，抬起
    * 按下：判断当前是否为listview顶部，如果是记录按下位置
    * 移动：调用onMove方法处理移动情况
    * 抬起：如果当前状态为提示刷新状态就对header进行更新，并调用刷新内容接口
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case  MotionEvent.ACTION_DOWN:
                if(firstVisibleItem==0){
                    isRemak=true;
                    startY=(int)ev.getY();
                }
                break;
            case  MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case  MotionEvent.ACTION_UP:
                if(state==RELEASE){
                    state=REFRESHING;
                    refreshByState();//刷新header
                    iRefreshen.onRefresh(); //刷新listview
                }else if(state==PULL){
                    state=NONE;
                    isRemak=false;
                    refreshByState();
                }
                break;
        }

        return super.onTouchEvent(ev);

    }


    /*
 *触摸移动的时候判断移动的位置
 * 若下拉高过或低于设定的数值，则改变提示信息
  */
    private void onMove(MotionEvent ev) {

        if(!isRemak){
            return;
        }

        int tempY=(int) ev.getY();
        int distance=tempY-startY;
        int toPadding=distance-headerHight;

        switch (state){
            case NONE:
                if(distance>0){
                    state=PULL;
                    refreshByState();
                }
                break;
            case PULL:
                toPadding(toPadding);
                if(distance>(headerHight+150) && scrollState==SCROLL_STATE_TOUCH_SCROLL){
                    state=RELEASE;
                    refreshByState();
                }
                break;
            case RELEASE:
                toPadding(toPadding);
                if(distance<(headerHight+150)){
                    state=PULL;
                    refreshByState();
                }
                break;
        }


    }


    /*
   *通过当前state状态改变header的显示布局
    */
    private void refreshByState() {

        switch (state){
            case NONE:
                // imageView.clearAnimation();
                toPadding(-headerHight);
                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                break;
            case PULL:
                imageView.clearAnimation();
                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                imageView.setAnimation(anim2);
                tip.setText("下拉可刷新");
                break;
            case RELEASE:
                imageView.clearAnimation();
                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                imageView.setAnimation(anim1);
                tip.setText("松开可刷新");
                break;
            case REFRESHING:
                toPadding(50);
                imageView.clearAnimation();
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tip.setText("正在刷新");
                break;
        }
    }


    public void setInterface(IRefreshen iRefreshen){
        this.iRefreshen=iRefreshen;
    }

    public interface IRefreshen{
        public  void onRefresh();
    }

    /*
    *刷新完成后调用此方法重新设置参数，并设置上次刷新时间
     */
    public void refreshComplete(){
        state=NONE;
        isRemak=false;

        refreshByState();

        Date date=new Date(System.currentTimeMillis());

        SimpleDateFormat format=new SimpleDateFormat("yy年mm月dd日 HH:MM:SS");

        String time=format.format(date);

        this.time.setText(time);

    }
}
