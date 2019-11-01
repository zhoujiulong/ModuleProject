package com.zhoujiulong.widgetlib.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.zhoujiulong.baselib.http.other.RequestErrorType
import com.zhoujiulong.widgetlib.R

/**
 * @author zhoujiulong
 * @createtime 2019/2/26 13:58
 * LoadingView
 */
class LoadingView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(mContext, attrs, defStyleAttr) {
    private var mContentView: View? = null
    private val mLoadingView: View by lazy {
        View.inflate(
            mContext,
            R.layout.loadingview_loading,
            null
        )
    }
    private val mErrorView: View by lazy {
        View.inflate(
            mContext,
            R.layout.loadingview_error,
            null
        )
    }
    private val mIntentErrorView: View by lazy {
        View.inflate(
            mContext,
            R.layout.loadingview_intent_error,
            null
        )
    }
    private val mIntentErrorBottomTv: TextView by lazy { mIntentErrorView.findViewById<TextView>(R.id.tv_intent_error_bottom) }
    private val mIvErrorIcon: ImageView by lazy { mErrorView.findViewById<ImageView>(R.id.iv_error) }
    private val mTvErrorCenterMsg: TextView by lazy { mErrorView.findViewById<TextView>(R.id.tv_error_center) }
    private val mTvErrorBottomMsg: TextView by lazy { mErrorView.findViewById<TextView>(R.id.tv_error_bottom) }
    private val mEmptyView: View by lazy {
        View.inflate(
            mContext,
            R.layout.loadingview_empty,
            null
        )
    }
    private val mIvEmptyIcon: ImageView by lazy { mEmptyView.findViewById<ImageView>(R.id.iv_empty) }
    private val mTvEmptyCenterMsg: TextView by lazy { mEmptyView.findViewById<TextView>(R.id.tv_empty_center) }
    private val mTvEmptyBottomMsg: TextView by lazy { mEmptyView.findViewById<TextView>(R.id.tv_empty_bottom) }
    private val mViewLoading: ImageView by lazy { mLoadingView.findViewById<ImageView>(R.id.view_loading) }

    //0：刷新，1：显示内容，2：网络错误，3：空页面，4：其它错误
    private var mInitType = 0
    @DrawableRes
    private var mBackgroundRes = -1
    @DrawableRes
    private var mErrorIconRes = -1
    private var mErrorCenterMsgStr: String? = null
    private var mErrorBottomMsgStr: String? = null
    @DrawableRes
    private var mEmptyIconRes = -1
    private var mEmptyCenterMsgStr: String? = null
    private var mEmptyBottomMsgStr: String? = null
    private var mLoadingAni: AnimationDrawable? = null
    private var mTopPadding = 0
    private var mBottomPadding = 0

    init {
        if (attrs != null) {
            val ta = mContext.obtainStyledAttributes(attrs, R.styleable.LoadingView)
            mInitType = ta.getInt(R.styleable.LoadingView_loading_init_type, 0)
            mBackgroundRes = ta.getResourceId(R.styleable.LoadingView_loading_background, -1)
            mErrorIconRes = ta.getResourceId(R.styleable.LoadingView_loading_error_icon, -1)
            mErrorCenterMsgStr = ta.getString(R.styleable.LoadingView_loading_error_center_msg)
            mErrorBottomMsgStr = ta.getString(R.styleable.LoadingView_loading_error_bottom_bt_msg)
            mEmptyIconRes = ta.getResourceId(R.styleable.LoadingView_loading_empty_icon, -1)
            mEmptyCenterMsgStr = ta.getString(R.styleable.LoadingView_loading_empty_center_msg)
            mEmptyBottomMsgStr = ta.getString(R.styleable.LoadingView_loading_empty_bottom_bt_msg)
            mTopPadding = ta.getDimensionPixelOffset(R.styleable.LoadingView_loading_top_padding, 0)
            mBottomPadding =
                ta.getDimensionPixelOffset(R.styleable.LoadingView_loading_bottom_padding, 0)
            ta.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            mContentView = getChildAt(0)
        }
        mLoadingView.setPadding(0, mTopPadding, 0, mBottomPadding)
        mErrorView.setPadding(0, mTopPadding, 0, mBottomPadding)
        mEmptyView.setPadding(0, mTopPadding, 0, mBottomPadding)
        mIntentErrorView.setPadding(0, mTopPadding, 0, mBottomPadding)
        addView(mLoadingView)
        addView(mErrorView)
        addView(mEmptyView)
        addView(mIntentErrorView)

        if (mBackgroundRes != -1) {
            mLoadingView.setBackgroundResource(mBackgroundRes)
            mErrorView.setBackgroundResource(mBackgroundRes)
            mEmptyView.setBackgroundResource(mBackgroundRes)
            mIntentErrorView.setBackgroundResource(mBackgroundRes)
        }

        mViewLoading.setImageResource(R.drawable.anim_loading)
        mLoadingAni = mViewLoading.drawable as AnimationDrawable


        setViewData()

        //0：刷新，1：显示内容，2：网络错误，3：空页面，4：其它错误
        when (mInitType) {
            0 -> showLoading()
            1 -> showContent()
            2 -> showIntentError()
            3 -> showEmpty()
            4 -> showError()
        }
    }

    override fun onDetachedFromWindow() {
        if (mLoadingAni != null) mLoadingAni!!.stop()
        super.onDetachedFromWindow()
    }

    private fun setViewData() {
        if (mErrorIconRes != -1) mIvErrorIcon.setImageResource(mErrorIconRes)
        if (!TextUtils.isEmpty(mErrorCenterMsgStr)) mTvErrorCenterMsg.text = mErrorCenterMsgStr
        if (!TextUtils.isEmpty(mErrorBottomMsgStr)) mTvErrorBottomMsg.text = mErrorBottomMsgStr
        if (mEmptyIconRes != -1) mIvEmptyIcon.setImageResource(mEmptyIconRes)
        if (!TextUtils.isEmpty(mEmptyCenterMsgStr)) mTvEmptyCenterMsg.text = mEmptyCenterMsgStr
        if (!TextUtils.isEmpty(mEmptyBottomMsgStr)) mTvEmptyBottomMsg.text = mEmptyBottomMsgStr
    }

    /**
     * 同时设置空页面、网络错误和错误页面底部按钮点击事件回调
     */
    fun setOnBottomTvListener(action: (View) -> Unit) {
        setOnErrorListener(action)
        setOnEmptyListener(action)
        setIntentErrorListener(action)
    }

    /**
     * 设置页面错误和网络错误地步按钮点击事件回调
     */
    fun setOnErrorAndIntentErrorListener(action: (View) -> Unit) {
        setOnErrorListener(action)
        setIntentErrorListener(action)
    }

    /**
     * 设置错误页面底部按钮点击事件回调
     */
    fun setOnErrorListener(action: (View) -> Unit) {
        mTvErrorBottomMsg.setOnClickListener(OnClickListener(action))
        if (mTvErrorBottomMsg.visibility != View.VISIBLE)
            mTvErrorBottomMsg.visibility = View.VISIBLE
    }

    /**
     * 设置空白页面底部按钮点击事件回调
     */
    fun setOnEmptyListener(action: (View) -> Unit) {
        mTvEmptyBottomMsg.setOnClickListener(OnClickListener(action))
        if (mTvEmptyBottomMsg.visibility != View.VISIBLE)
            mTvEmptyBottomMsg.visibility = View.VISIBLE
    }

    /**
     * 设置网络错误页面地步按钮点击事件回调
     */
    fun setIntentErrorListener(action: (View) -> Unit) {
        mIntentErrorBottomTv.setOnClickListener(OnClickListener(action))
        if (mIntentErrorBottomTv.visibility != View.VISIBLE)
            mIntentErrorBottomTv.visibility = View.VISIBLE
    }

    fun showContent() {
        hideAllView()
        mContentView?.apply { visibility = View.VISIBLE }
    }

    fun showLoading() {
        hideAllView()
        mLoadingView.visibility = View.VISIBLE
        mLoadingAni?.start()
    }

    fun showEmpty() {
        hideAllView()
        mEmptyView.visibility = View.VISIBLE
    }

    fun showError(type: RequestErrorType) {
        when (type) {
            RequestErrorType.NO_INTERNET -> showIntentError()
            else -> showError()
        }
    }

    fun showError() {
        hideAllView()
        mErrorView.visibility = View.VISIBLE
    }

    fun showIntentError() {
        hideAllView()
        mIntentErrorView.visibility = View.VISIBLE
    }

    fun setEmptyCenterMsgStr(msg: String) {
        if (TextUtils.isEmpty(msg)) return
        this.mEmptyCenterMsgStr = msg
        mTvEmptyCenterMsg.text = msg
    }

    private fun hideAllView() {
        mLoadingAni?.apply { stop() }
        if (mContentView != null && mContentView!!.visibility == View.VISIBLE) {
            mContentView?.visibility = View.GONE
        }
        if (mLoadingView.visibility == View.VISIBLE) {
            mLoadingView.visibility = View.GONE
        }
        if (mErrorView.visibility == View.VISIBLE) {
            mErrorView.visibility = View.GONE
        }
        if (mEmptyView.visibility == View.VISIBLE) {
            mEmptyView.visibility = View.GONE
        }
        if (mIntentErrorView.visibility == View.VISIBLE) {
            mIntentErrorView.visibility = View.GONE
        }
    }

}





















