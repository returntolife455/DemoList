package com.returntolife.jjcode.mydemolist.manager;

import com.returntolife.jjcode.mydemolist.bean.DemoListBean;

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

        for (int i = 0; i < 10; i++) {
            demoListBeanList.add(new DemoListBean());
        }

    }
}
