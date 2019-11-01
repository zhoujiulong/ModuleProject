package com.zhoujiulong.baselib.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.zhoujiulong.baselib.BuildConfig
import com.zhoujiulong.baselib.utils.ContextUtil

/**
 * @author zhoujiulong
 * @createtime 2019/7/11 13:34
 */
open class SimpleApplication : Application() {

    companion object {

        var instance: Application? = null
            private set
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ContextUtil.init(this)
        initLogger()
    }

    /**
     * 初始化日志输入框架
     */
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true).methodCount(1).methodOffset(1).tag("xingfugolog").build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG_MODE
            }
        })
    }


}

















