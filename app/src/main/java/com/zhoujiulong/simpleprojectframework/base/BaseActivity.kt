package com.zhoujiulong.simpleprojectframework.base

import com.zhoujiulong.baselib.base.SimpleActivity
import com.zhoujiulong.widgetlib.dialog.LoadingDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity<T : BasePresenter<*, *>> : SimpleActivity(),
    BaseView {

    /**
     * 網絡請求標記tag
     */
    protected var ReTag = System.currentTimeMillis().toString()
    /**
     * 用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏。
     */
    private var mCompositeDisposable: CompositeDisposable? = null

    protected var mPresenter: T? = null
    private var mLoadingDialog: LoadingDialog? = null

    override fun onDestroy() {
        mCompositeDisposable?.apply { dispose() }
        mLoadingDialog?.apply { if (isShowing) dismiss() }
        super.onDestroy()
    }

    public override fun attachView() {
        mPresenter?.apply { attachView(this@BaseActivity, ReTag) }
    }

    public override fun detachView() {
        mPresenter?.apply { detachView() }
    }

    override fun bindSubscription(disposable: Disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = CompositeDisposable()
        }
        this.mCompositeDisposable?.add(disposable)
    }

    override fun showLoading() {
        if (mLoadingDialog == null) mLoadingDialog = LoadingDialog.build(this)
        mLoadingDialog?.show()
    }

    override fun hideLoading() {
        mLoadingDialog?.dismiss()
    }
}









