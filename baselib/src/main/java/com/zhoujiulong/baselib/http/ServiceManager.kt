package com.zhoujiulong.baselib.http

import com.zhoujiulong.baselib.BuildConfig
import com.zhoujiulong.baselib.http.other.TimeOut
import com.zhoujiulong.baselib.utils.JsonUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : Service 管理
 */
internal class ServiceManager private constructor() {

    val baseUrl: String = BuildConfig.BASE_URL
    private val mIsDebug: Boolean = BuildConfig.DEBUG_MODE
    private var mHeaderInterceptor: Interceptor? = null
    private val mRetrofits: MutableMap<String, Retrofit> = HashMap()

    companion object {

        private var mInstance: ServiceManager? = null

        val instance: ServiceManager
            get() {
                if (mInstance == null) {
                    synchronized(ServiceManager::class.java) {
                        if (mInstance == null) {
                            mInstance = ServiceManager()
                        }
                    }
                }
                return mInstance!!
            }
    }

    fun setHeaderInterceptor(headerInterceptor: Interceptor) {
        mHeaderInterceptor = headerInterceptor
    }

    /**
     * 获取 Call ，使用传入的 BaseUrl
     *
     * @param callClass 需要获取的 Call 对应的 Class
     * @param timeOuts  TimeOut 超时设置，可变参数，不设置使用默认的
     * @param <T>       返回的 Call 类型
    </T> */
    @Synchronized
    fun <T> getService(callClass: Class<T>, vararg timeOuts: TimeOut): T {
        return getService(callClass, baseUrl, *timeOuts)
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
        val key =
            StringBuilder().append(if (mHeaderInterceptor == null) "" else mHeaderInterceptor!!.hashCode())
                .append(mIsDebug).append(baseUrl).append(JsonUtil.object2String(timeOuts))
                .toString()
        var retrofit: Retrofit? = null
        if (mRetrofits.containsKey(key)) retrofit = mRetrofits[key]
        if (retrofit == null) {
            var readTimeOut = 15L
            var writeTimeOut = 15L
            var connectTimeOut = 10L
            if (timeOuts.isNotEmpty()) {
                for (timeOut in timeOuts) {
                    when (timeOut.timeOutType) {
                        TimeOut.TimeOutType.READ_TIMEOUT -> readTimeOut = timeOut.timeOutSeconds
                        TimeOut.TimeOutType.WRITE_TIMEOUT -> writeTimeOut = timeOut.timeOutSeconds
                        TimeOut.TimeOutType.CONNECT_TIMEOUT -> connectTimeOut =
                            timeOut.timeOutSeconds
                    }
                }
            }
            val builder = OkHttpClient.Builder()
            if (mHeaderInterceptor != null) {
                builder.addInterceptor(mHeaderInterceptor!!)
            }
            if (mIsDebug) {
                //配置消息头和打印日志等
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }
            val client = builder.retryOnConnectionFailure(true)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .build()
            //创建请求
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mRetrofits[key] = retrofit!!
        }
        return retrofit.create(callClass)
    }


}





















