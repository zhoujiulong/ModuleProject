package com.zhoujiulong.simpleprojectframework.base;

import io.reactivex.disposables.Disposable;

public interface BaseView {

    /**
     * 用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏
     */
    void bindSubscription(Disposable disposable);

    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 隐藏loading
     */
    void hideLoading();

}
