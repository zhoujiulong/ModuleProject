package com.zhoujiulong.baselib.base

import java.util.*

/**
 * 应用Activity管理类
 */
class ActivityManager private constructor() {

    companion object {

        @Volatile
        private var activityStack: Stack<SimpleActivity> = Stack()
        @Volatile
        private var instance: ActivityManager? = null

        /**
         * 获得单例对象
         *
         * @return ActivityManager
         */
        fun getInstance(): ActivityManager? {
            if (instance == null) {
                synchronized(ActivityManager::class.java) {
                    if (instance == null) instance = ActivityManager()
                }
            }
            return instance
        }
    }

    /**
     * 获取当前的Activity
     */
    val currentActivity: SimpleActivity
        get() = activityStack.lastElement()

    /**
     * 添加 Activity
     *
     * @param activity Activity
     */
    fun addActivity(activity: SimpleActivity?) {
        if (activity != null) activityStack.add(activity)
    }

    /**
     * 移除 Activity
     *
     * @param activity Activity
     */
    fun removeActivity(activity: SimpleActivity?) {
        activityStack.remove(activity)
    }

    /**
     * 结束指定Activity
     *
     * @param activity Activity
     */
    fun finishActivity(activity: SimpleActivity?) {
        if (activity != null) {
            activityStack.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定Activity
     *
     * @param cls Activity.class
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity != null && activity.javaClass == cls) {
                activityStack.remove(activity)
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用程序
     */
    fun appExit() {
        try {
            finishAllActivity()
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getActivityStack(): Stack<SimpleActivity> {
        return activityStack
    }

}
