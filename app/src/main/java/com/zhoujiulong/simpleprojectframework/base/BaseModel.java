package com.zhoujiulong.simpleprojectframework.base;

import com.zhoujiulong.baselib.http.HttpUtil;

public abstract class BaseModel<T> {

    /**
     * 網絡請求標記tag
     */
    protected String ReTag = getClass().getName();

    protected T mService;

    public BaseModel() {
        initService();
    }

    public abstract void initService();

    public void attachView(String reTag) {
        this.ReTag = reTag;
    }

    public void detachView() {
        HttpUtil.cancelWithTag(ReTag);
        if (mService != null) {
            mService = null;
        }
    }

}
