package com.zhoujiulong.baselib.base;

import android.app.Application;
import android.support.annotation.Nullable;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zhoujiulong.baselib.BuildConfig;
import com.zhoujiulong.baselib.utils.ContextUtil;

/**
 * @author zhoujiulong
 * @createtime 2019/7/11 13:34
 */
public class SimpleApplication extends Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ContextUtil.init(this);
        initLogger();
    }

    /**
     * 初始化日志输入框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true).methodCount(1).methodOffset(1).tag("xingfugolog").build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG_MODE;
            }
        });
    }

    public static Application getInstance() {
        return mInstance;
    }

}

















