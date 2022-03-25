package com.returntolife.jjcode.mydemolist.bean;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;

/**
 * Created by jiajun He on 2019/4/16.
 * des:demo的item信息类
 * version: 图片icon暂不使用了
 */
public class DemoListBean {

    private String title;
    private String des;
    private String time;

    private int imageResource;
    private String imageUrl;

    private Intent intent;
    private Fragment fragment;
    private Class clazz;

    private int type;

    public static final int TYPE_IMAGE=1;
    public static final int TYPE_WIDGET=2;
    public static final int TYPE_FUNCTION=3;


    public DemoListBean(){

    }

    public DemoListBean(String title, String des, String time, int imageResource,int type,Intent intent) {
        this.title = title;
        this.des = des;
        this.time = time;
        this.imageResource = imageResource;
        this.intent=intent;
        this.type=type;
    }

    public DemoListBean(String title, String des, String time, String imageUrl,int type,Intent intent) {
        this.title = title;
        this.des = des;
        this.time = time;
        this.imageUrl = imageUrl;
        this.intent=intent;
        this.type=type;
    }

    public DemoListBean(String title, String des, String time, int imageResource, int type, Fragment fragment) {
        this.title = title;
        this.des = des;
        this.time = time;
        this.imageResource = imageResource;
        this.fragment=fragment;
        this.type=type;
    }

    public DemoListBean(String title, String des, String time,  String imageUrl, int type, Fragment fragment) {
        this.title = title;
        this.des = des;
        this.time = time;
        this.imageUrl = imageUrl;
        this.fragment=fragment;
        this.type=type;
    }

    public DemoListBean(String title, String des, String time, int imageResource,int type,Class clazz) {
        this.title = title;
        this.des = des;
        this.time = time;
        this.imageResource = imageResource;
        this.clazz=clazz;
        this.type=type;
    }

    public DemoListBean(String title, String des, String time, int type,Class clazz) {
        this.title = title;
        this.des = des;
        this.time = time;
        this.clazz=clazz;
        this.type=type;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void startActivity(Context context){
        if(clazz!=null){
            Intent intent=new Intent(context,clazz);
            context.startActivity(intent);
        }
    }
}
