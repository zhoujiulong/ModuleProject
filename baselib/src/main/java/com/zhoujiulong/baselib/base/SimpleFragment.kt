package com.zhoujiulong.baselib.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : SimpleFragment
 */
abstract class SimpleFragment : Fragment(), View.OnClickListener {

    protected val TAG = this.javaClass.name

    protected var mContext: Context? = null
    protected var mActivity: Activity? = null
    protected lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(getLayoutId(), container, false)

        mContext = activity
        mActivity = activity
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPresenter()
        attachView()
        initListener()
        initData()
        getData()

        mIsPrepared = true
        if (userVisibleHint && mIsFirstTimeLoadData) {
            mIsFirstTimeLoadData = false
            getDataLazy()
        }
    }

    override fun onDestroyView() {
        detachView()
        mContext = null
        mActivity = null
        super.onDestroyView()
    }

    /* ********************************************** 初始化相关方法 **************************************************** */
    /* ********************************************** 初始化相关方法 **************************************************** */

    /**
     * 获取布局资源 id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化控件
     */
    protected abstract fun initView()

    /**
     * 初始化逻辑处理层
     */
    protected abstract fun initPresenter()

    protected abstract fun attachView()

    /**
     * 初始化监听事件
     */
    protected abstract fun initListener()

    /**
     * 初始化数据,设置数据
     */
    protected abstract fun initData()

    /**
     * 获取网络数据
     */
    protected abstract fun getData()

    /**
     * 懒加载数据在 ViewPager 管理的 Fragment 中才能使用
     */
    protected fun getDataLazy() {}

    protected abstract fun detachView()

    /**
     * 設置點擊
     */
    fun setOnClick(@IdRes vararg viewIds: Int) {
        for (id in viewIds) {
            findViewById<View>(id).setOnClickListener(this)
        }
    }

    /**
     * 設置點擊
     */
    fun setOnClick(vararg views: View) {
        for (view in views) {
            view.setOnClickListener(this)
        }
    }

    fun <T : View> findViewById(@IdRes viewId: Int): T {
        return mRootView.findViewById(viewId)
    }

    /* ********************************************** 懒加载数据在 ViewPager 管理的 Fragment 中才能使用 **************************************************** */
    /* ********************************************** 懒加载数据在 ViewPager 管理的 Fragment 中才能使用 **************************************************** */

    /**
     * 页面布局是否初始化完成
     */
    protected var mIsPrepared = false
    /**
     * 是否是第一次加载数据
     */
    protected var mIsFirstTimeLoadData = true

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mIsFirstTimeLoadData && mIsPrepared) {
            mIsFirstTimeLoadData = false
            getDataLazy()
        }
    }

}


















