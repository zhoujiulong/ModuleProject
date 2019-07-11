package com.zhoujiulong.baselib.http;

import android.support.annotation.NonNull;
import com.zhoujiulong.baselib.BuildConfig;
import com.zhoujiulong.baselib.http.other.TimeOut;
import com.zhoujiulong.baselib.utils.JsonUtil;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : Service 管理
 */
class ServiceManager {

    private static ServiceManager mInstance;

    private String mBaseUrl;
    private Boolean mIsDebug;
    private Interceptor mHeaderInterceptor;
    private Map<String, Retrofit> mRetrofits;

    private ServiceManager() {
        mBaseUrl = BuildConfig.BASE_URL;
        mIsDebug = BuildConfig.DEBUG_MODE;
        mRetrofits = new HashMap<>();
    }

    static ServiceManager getInstance() {
        if (mInstance == null) {
            synchronized (ServiceManager.class) {
                if (mInstance == null) {
                    mInstance = new ServiceManager();
                }
            }
        }
        return mInstance;
    }

    public void setHeaderInterceptor(Interceptor headerInterceptor) {
        mHeaderInterceptor = headerInterceptor;
    }

    /**
     * 获取 Call ，使用传入的 BaseUrl
     *
     * @param callClass 需要获取的 Call 对应的 Class
     * @param timeOuts  TimeOut 超时设置，可变参数，不设置使用默认的
     * @param <T>       返回的 Call 类型
     */
    synchronized <T> T getService(@NonNull Class<T> callClass, TimeOut... timeOuts) {
        return getService(callClass, mBaseUrl, timeOuts);
    }

    /**
     * 获取 Call ，使用传入的 BaseUrl
     *
     * @param callClass 需要获取的 Call 对应的 Class
     * @param baseUrl   BaseUrl
     * @param timeOuts  TimeOut 超时设置，可变参数，不设置使用默认的
     * @param <T>       返回的 Call 类型
     */
    synchronized <T> T getService(@NonNull Class<T> callClass, @NonNull String baseUrl, TimeOut... timeOuts) {
        String key = new StringBuilder().append(mHeaderInterceptor == null ? "" : mHeaderInterceptor.hashCode())
                .append(mIsDebug).append(baseUrl).append(JsonUtil.object2String(timeOuts)).toString();
        Retrofit retrofit = null;
        if (mRetrofits.containsKey(key)) retrofit = mRetrofits.get(key);
        if (retrofit == null) {
            long readTimeOut = 15L;
            long writeTimeOut = 15L;
            long connectTimeOut = 10L;
            if (timeOuts != null && timeOuts.length > 0) {
                for (TimeOut timeOut : timeOuts) {
                    switch (timeOut.getTimeOutType()) {
                        case READ_TIMEOUT:
                            readTimeOut = timeOut.getTimeOutSeconds();
                            break;
                        case WRITE_TIMEOUT:
                            writeTimeOut = timeOut.getTimeOutSeconds();
                            break;
                        case CONNECT_TIMEOUT:
                            connectTimeOut = timeOut.getTimeOutSeconds();
                            break;
                    }
                }
            }
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (mHeaderInterceptor != null) {
                builder.addInterceptor(mHeaderInterceptor);
            }
            if (mIsDebug) {
                //配置消息头和打印日志等
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }
            OkHttpClient client = builder.retryOnConnectionFailure(true)
                    .readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                    .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                    .build();
            //创建请求
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRetrofits.put(key, retrofit);
        }
        return retrofit.create(callClass);
    }

    String getBaseUrl() {
        return mBaseUrl;
    }
}





















