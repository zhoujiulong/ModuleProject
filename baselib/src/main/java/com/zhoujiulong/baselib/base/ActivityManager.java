package com.zhoujiulong.baselib.base;

import android.app.Activity;

import java.util.Stack;

/**
 * 应用Activity管理类
 */
public class ActivityManager {

    private static volatile Stack<SimpleActivity> activityStack;
    private static volatile ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 获得单例对象
     *
     * @return ActivityManager
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) instance = new ActivityManager();
            }
        }
        return instance;
    }

    /**
     * 添加 Activity
     *
     * @param activity Activity
     */
    public void addActivity(SimpleActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<SimpleActivity>();
        }
        if (activity != null) activityStack.add(activity);
    }

    /**
     * 移除 Activity
     *
     * @param activity Activity
     */
    public void removeActivity(SimpleActivity activity) {
        if (activity != null && activityStack != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定Activity
     *
     * @param activity Activity
     */
    public void finishActivity(SimpleActivity activity) {
        if (activity != null) {
            if (activityStack != null) activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定Activity
     *
     * @param cls Activity.class
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            for (SimpleActivity activity : activityStack) {
                if (activity != null && activity.getClass().equals(cls)) {
                    activityStack.remove(activity);
                    finishActivity(activity);
                    break;
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            for (Activity activity:activityStack) {
                activity.finish();
            }
            activityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前的Activity
     */
    public SimpleActivity getCurrentActivity() {
        return activityStack.lastElement();
    }

    public Stack<SimpleActivity> getActivityStack(){
        return activityStack;
    }
}
