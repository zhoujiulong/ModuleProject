package com.zhoujiulong.simpleprojectframework.base;

import com.zhoujiulong.baselib.base.SimpleFragment;
import com.zhoujiulong.widgetlib.dialog.LoadingDialog;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView {

    /**
     * 網絡請求標記tag
     */
    protected String ReTag = String.valueOf(System.currentTimeMillis());
    /**
     * 用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏。
     */
    private CompositeDisposable mCompositeDisposable;

    protected T mPresenter;
    protected LoadingDialog mLoadingDialog;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            this.mCompositeDisposable.dispose();
        }
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
    }

    @Override
    public void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this, ReTag);
        }
    }

    @Override
    public void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void bindSubscription(Disposable disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            if (mContext == null) return;
            mLoadingDialog = LoadingDialog.showLoading(mContext);
        } else {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
