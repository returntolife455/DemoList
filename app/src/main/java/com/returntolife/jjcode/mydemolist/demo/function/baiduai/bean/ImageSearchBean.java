package com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean;

/**
 * Created by HeJiaJun on 2019/8/1.
 * Email:hejj@mama.cn
 * des:
 */
public class ImageSearchBean {

    public static final int SEARCH_TYPE_ANIMAL=1; //动物
    public static final int SEARCH_TYPE_CAR=2;//车型
    public static final int SEARCH_TYPE_INGREDIENT=3;//果蔬

    private int imageRes;
    private boolean isHandler;
    private int type;

    public ImageSearchBean(int imageRes, boolean isHandler,int type) {
        this.imageRes = imageRes;
        this.isHandler = isHandler;
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public boolean isHandler() {
        return isHandler;
    }

    public void setHandler(boolean handler) {
        isHandler = handler;
    }


}
