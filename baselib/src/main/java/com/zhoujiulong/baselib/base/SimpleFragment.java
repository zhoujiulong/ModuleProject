package com.zhoujiulong.baselib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : SimpleFragment
 */
public abstract class SimpleFragment extends Fragment implements View.OnClickListener {

    protected final String TAG = this.getClass().getName();

    protected Context mContext;
    protected Activity mActivity;
    protected View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);

        mContext = getActivity();
        mActivity = getActivity();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(mRootView);
        initPresenter();
        attachView();
        initListener();
        initData();
        getData();

        mIsPrepared = true;
        if (getUserVisibleHint() && mIsFirstTimeLoadData) {
            mIsFirstTimeLoadData = false;
            getDataLazy();
        }
    }

    @Override
    public void onDestroyView() {
        detachView();
        mRootView = null;
        mContext = null;
        mActivity = null;
        super.onDestroyView();
    }

    /* ********************************************** 初始化相关方法 **************************************************** */
    /* ********************************************** 初始化相关方法 **************************************************** */

    /**
     * 获取布局资源 id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView(View rootView);

    /**
     * 初始化逻辑处理层
     */
    protected abstract void initPresenter();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 初始化数据,设置数据
     */
    protected abstract void initData();

    /**
     * 获取网络数据
     */
    protected abstract void getData();

    /**
     * 懒加载数据在 ViewPager 管理的 Fragment 中才能使用
     */
    protected void getDataLazy() {
    }

    protected abstract void attachView();

    protected abstract void detachView();

    /**
     * 設置點擊
     */
    public void setOnClick(@IdRes int... viewIds) {
        if (viewIds != null) {
            for (int id : viewIds) {
                findViewById(id).setOnClickListener(this);
            }
        }
    }

    /**
     * 設置點擊
     */
    public void setOnClick(View... views){
        if (views!=null) {
            for (View view:views){
                view.setOnClickListener(this);
            }
        }
    }

    public <T extends View> T findViewById(@IdRes int viewId) {
        return mRootView.findViewById(viewId);
    }

    /* ********************************************** 懒加载数据在 ViewPager 管理的 Fragment 中才能使用 **************************************************** */
    /* ********************************************** 懒加载数据在 ViewPager 管理的 Fragment 中才能使用 **************************************************** */

    /**
     * 页面布局是否初始化完成
     */
    protected boolean mIsPrepared = false;
    /**
     * 是否是第一次加载数据
     */
    protected boolean mIsFirstTimeLoadData = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mIsFirstTimeLoadData && mIsPrepared) {
            mIsFirstTimeLoadData = false;
            getDataLazy();
        }
    }

}


















