package com.zhoujiulong.simpleprojectframework.base;


import com.zhoujiulong.baselib.http.HttpUtil;

public abstract class BasePresenter<M extends BaseModel, T extends BaseView> {

    protected M mModel;
    protected T mView;
    /**
     * 網絡請求標記tag
     */
    protected String ReTag = getClass().getName();

    public BasePresenter() {
        initModel();
    }

    public abstract void initModel();

    public void attachView(T View, String ReTag) {
        this.mView = View;
        this.ReTag = ReTag;
        if (mModel != null) {
            mModel.attachView(ReTag);
        }
    }

    public void detachView() {
        HttpUtil.cancelWithTag(ReTag);
        if (mView != null) {
            mView = null;
        }
        if (mModel != null) {
            mModel.detachView();
            mModel = null;
        }
    }

}
