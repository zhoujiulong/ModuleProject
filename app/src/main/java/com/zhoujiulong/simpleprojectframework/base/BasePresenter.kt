package com.zhoujiulong.simpleprojectframework.base


import com.zhoujiulong.baselib.http.HttpUtil

abstract class BasePresenter<M : BaseModel<*>, T : BaseView> {

    protected var mModel: M? = null
    protected var mView: T? = null
    /**
     * 網絡請求標記tag
     */
    protected var ReTag = javaClass.name

    init {
        mModel = this.initModel()
    }

    abstract fun initModel(): M

    fun attachView(view: Any, ReTag: String) {
        this.ReTag = ReTag
        @Suppress("UNCHECKED_CAST")
        this.mView = view as? T
        mModel?.attachView(ReTag)
    }

    fun detachView() {
        HttpUtil.cancelWithTag(ReTag)
        mView = null
        mModel?.detachView()
        mModel = null
    }

}
