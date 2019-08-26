package com.returntolife.jjcode.mydemolist.demo.function.section;

import java.lang.reflect.Type;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:455hejiajun@gmail
 * des:
 */
public class ContentBean {

    int imageRes;
    String contentName;
    TypeBean typeBean;

    public ContentBean(int imageRes, String contentName, TypeBean typeBean) {
        this.imageRes = imageRes;
        this.contentName = contentName;
        this.typeBean=typeBean;
    }
}
