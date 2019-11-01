package com.zhoujiulong.simpleprojectframework.app

import com.zhoujiulong.baselib.utils.PhoneUtils
import com.zhoujiulong.baselib.utils.ScreenUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Day : 2017/03/24
 * Desc : 添加请求头拦截器
 */
class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        builder.header("version", PhoneUtils.appVersion)//App 版本号
            //手机屏幕分辨率
            .header("screen", ScreenUtil.screenWidth.toString() + "*" + ScreenUtil.screenHeight)
        return chain.proceed(builder.build())
    }

}
