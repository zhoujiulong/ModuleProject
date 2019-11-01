package com.zhoujiulong.baselib.http


import com.zhoujiulong.baselib.http.listener.DownLoadListener
import com.zhoujiulong.baselib.http.listener.OnTokenInvalidListener
import com.zhoujiulong.baselib.http.listener.RequestListener
import com.zhoujiulong.baselib.http.other.TimeOut
import okhttp3.Interceptor
import okhttp3.ResponseBody

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : 网络请求工具类
 */
object HttpUtil {

    /**
     * 获取BaseUrl
     */
    val baseUrl: String
        get() = ServiceManager.instance.baseUrl

    /**
     * 设置 Token 失效回调，全局只调用一次，在 Application 中设置
     */
    fun initTokenInvalidListener(onTokenInvalidListener: OnTokenInvalidListener) {
        RequestHelper.instance.setOnTokenInvalidListener(onTokenInvalidListener)
    }

    /**
     * 添加请求头部拦截器
     */
    fun addHeaderInterceptor(headerInterceptor: Interceptor) {
        ServiceManager.instance.setHeaderInterceptor(headerInterceptor)
    }

    /**
     * 获取 Call ，使用默认的 BaseUrl
     *
     * @param callClass 需要获取的 Call 对应的 Class
     * @param timeOuts  TimeOut 超时设置，可变参数，不设置使用默认的
     * @param <T>       返回的 Call 类型
    </T> */
    @Synchronized
    fun <T> getService(callClass: Class<T>, vararg timeOuts: TimeOut): T {
        return ServiceManager.instance.getService(callClass, *timeOuts)
    }

    /**
     * 获取 Call ，使用传入的 BaseUrl
     *
     * @param callClass 需要获取的 Call 对应的 Class
     * @param baseUrl   BaseUrl
     * @param timeOuts  TimeOut 超时设置，可变参数，不设置使用默认的
     * @param <T>       返回的 Call 类型
    </T> */
    @Synchronized
    fun <T> getService(callClass: Class<T>, baseUrl: String, vararg timeOuts: TimeOut): T {
        return ServiceManager.instance.getService(callClass, baseUrl, *timeOuts)
    }

    /**
     * 发送请求
     *
     * @param tag      请求标记，用于取消请求用
     * @param listener 请求完成后的回调
     * @param <T>      请求返回的数据对应的类型，第一层必须继承 BaseResponse
    </T> */
    fun <T> sendRequest(tag: String, call: retrofit2.Call<T>, listener: RequestListener<T>) {
        RequestHelper.instance.sendRequest(tag, call, listener)
    }

    /**
     * 发送下载网络请求
     *
     * @param tag              请求标记，用于取消请求用
     * @param downLoadFilePath 下载文件保存路径
     * @param downloadListener 下载回调
     */
    fun sendDownloadRequest(
        tag: String, call: retrofit2.Call<ResponseBody>, downLoadFilePath: String,
        fileName: String, downloadListener: DownLoadListener
    ) {
        RequestHelper.instance
            .sendDownloadRequest(tag, call, downLoadFilePath, fileName, downloadListener)
    }

    /**
     * 根据请求的标记 tag 取消请求和 Observer
     */
    fun cancelWithTag(tag: String) {
        RequestManager.instance.cancelRequestWithTag(tag)
    }
}




















