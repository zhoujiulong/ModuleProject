package com.zhoujiulong.baselib.http.other;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/08/17
 * 描述 : 超时
 */

public class TimeOut {

    public enum TimeOutType {
        READ_TIMEOUT,
        WRITE_TIMEOUT,
        CONNECT_TIMEOUT
    }

    /**
     * 超时类型
     */
    private TimeOutType mTimeOutType;
    /**
     * 超时时间 秒
     */
    private long mTimeOutSeconds;

    public TimeOut(TimeOutType timeOutType, long timeOutSeconds) {
        mTimeOutType = timeOutType;
        mTimeOutSeconds = timeOutSeconds;
    }

    public TimeOutType getTimeOutType() {
        return mTimeOutType;
    }

    public long getTimeOutSeconds() {
        return mTimeOutSeconds;
    }

}





