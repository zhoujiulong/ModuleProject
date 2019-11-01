package com.zhoujiulong.simpleprojectframework.base

import com.zhoujiulong.baselib.http.HttpUtil

abstract class BaseModel<T> {

    /**
     * 網絡請求標記tag
     */
    protected var ReTag = javaClass.name

    protected var mService: T

    init {
        mService = this.initService()
    }

    abstract fun initService() : T

    fun attachView(reTag: String) {
        this.ReTag = reTag
    }

    fun detachView() {
        HttpUtil.cancelWithTag(ReTag)
    }

}
