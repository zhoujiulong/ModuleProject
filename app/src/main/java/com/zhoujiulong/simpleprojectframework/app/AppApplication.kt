package com.zhoujiulong.simpleprojectframework.app

import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhoujiulong.baselib.R
import com.zhoujiulong.baselib.base.SimpleApplication
import com.zhoujiulong.baselib.http.HttpUtil
import com.zhoujiulong.baselib.http.listener.OnTokenInvalidListener
import com.zhoujiulong.widgetlib.loadmore_refresh_view.NormalRefreshHeader

/**
 * @author zhoujiulong
 * @createtime 2019/7/11 13:56
 */
class AppApplication : SimpleApplication() {

    override fun onCreate() {
        super.onCreate()
        initRefresh()
        initHttpUtil()
    }

    /**
     * 初始化刷新控件
     */
    private fun initRefresh() {
        //初始化刷新控件 SmartRefreshLayout 构建器
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(android.R.color.white, android.R.color.black)//全局设置主题颜色
            layout.setHeaderHeightPx(resources.getDimensionPixelSize(R.dimen.dip140))
            layout.setDragRate(0.8f)
            layout.isEnableLoadMore = false
            layout.isEnableAutoLoadMore = false
            NormalRefreshHeader(context)
        }
    }

    /**
     * 初始化网络请求工具
     */
    private fun initHttpUtil() {
        HttpUtil.addHeaderInterceptor(HeaderInterceptor())
        HttpUtil.initTokenInvalidListener(object : OnTokenInvalidListener {
            override fun onTokenInvalid(code: Int, msg: String) {

            }
        })
    }
}


















