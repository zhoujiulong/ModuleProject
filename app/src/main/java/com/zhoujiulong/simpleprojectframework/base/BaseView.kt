package com.zhoujiulong.simpleprojectframework.base

import io.reactivex.disposables.Disposable

interface BaseView {

    /**
     * 用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏
     */
    fun bindSubscription(disposable: Disposable)

    /**
     * 显示loading
     */
    fun showLoading()

    /**
     * 隐藏loading
     */
    fun hideLoading()

}
