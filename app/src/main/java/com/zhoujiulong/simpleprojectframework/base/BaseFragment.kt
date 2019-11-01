package com.zhoujiulong.simpleprojectframework.base

import com.zhoujiulong.baselib.base.SimpleFragment
import com.zhoujiulong.widgetlib.dialog.LoadingDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<T : BasePresenter<*, *>> : SimpleFragment(), BaseView {

    /**
     * 網絡請求標記tag
     */
    protected var ReTag = System.currentTimeMillis().toString()
    /**
     * 用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏。
     */
    private var mCompositeDisposable: CompositeDisposable? = null

    protected var mPresenter: T? = null
    protected var mLoadingDialog: LoadingDialog? = null

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.apply { dispose() }
        mLoadingDialog?.apply { dismiss() }
        mLoadingDialog = null
    }

    public override fun attachView() {
        mPresenter?.attachView(this, ReTag)
    }

    public override fun detachView() {
        mPresenter?.detachView()
    }

    override fun bindSubscription(disposable: Disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = CompositeDisposable()
        }
        this.mCompositeDisposable?.add(disposable)
    }

    override fun showLoading() {
        if (mLoadingDialog == null && mContext!=null) {
            mLoadingDialog = LoadingDialog.build(mContext!!)
        }
        mLoadingDialog?.show()
    }

    override fun hideLoading() {
        mLoadingDialog?.dismiss()
    }

}
