package com.tools.jj.tools.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Author : zhouyx
 * Date   : 2015/12/11
 * Activity管理类
 */
public class AppManager {

    private static Stack<Activity> activityStack = new Stack<>();
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 从堆栈中移除该Activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 是否包含某Activity
     */
    public boolean containActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity 除了cls这个activity之外
     */
    public void finishAllActivityExceptClsActivity(Class<?> cls) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity != null && !activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }


    /**
     * 结束所有Activity 除了cls和cls2这个主activity之外
     */
    public void finishAllActivityExceptParamActivity(Class<?> cls, Class<?> cls2) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity != null && !activity.getClass().equals(cls) && !activity.getClass().equals(cls2)) {
                activity.finish();
            }
        }
    }

    /**
     * 获取栈内所有activity集合
     */
    public Stack<Activity> getActivityList() {
        return activityStack;
    }
}
