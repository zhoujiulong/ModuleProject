package com.zhoujiulong.baselib.http.listener;

import android.support.annotation.Nullable;
import com.zhoujiulong.baselib.http.other.RequestErrorType;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/08/15
 * 描述 :
 */

public abstract class RequestListener<T> {

    /**
     * 返回bean的回调：data字段是javabean的时候使用
     *
     * @param data 返回结果中的数据实体类
     */
    public abstract void requestSuccess(T data);

    /**
     * 请求出错回调
     *
     * @param data      返回结果中的数据实体类
     * @param errorType 错误类型
     * @param errorMsg  错误信息
     * @param errorCode 错误码
     */
    public abstract void requestError(@Nullable T data, RequestErrorType errorType, String errorMsg, int errorCode);

    /**
     * 登录失效了就会发生这种行为
     *
     * @param errorCode errorCode
     * @param erroMsg   错误信息
     * @return 是否在调用的地方处理了token失效，如果返回true，在application中的初始化配置不再调用
     */
    public boolean checkLogin(int errorCode, String erroMsg) {
        return false;
    }
}













