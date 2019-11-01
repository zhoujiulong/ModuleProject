package com.zhoujiulong.widgetlib.widget

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zhoujiulong.baselib.utils.ScreenUtil
import com.zhoujiulong.baselib.utils.StatusBarUtil
import com.zhoujiulong.baselib.utils.ToastUtil
import com.zhoujiulong.widgetlib.R

/**
 * @author zhoujiulong
 * @createtime 2019/2/26 10:44
 * 标题栏
 */
class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.support.v7.appcompat.R.attr.toolbarStyle
) : Toolbar(context, attrs, defStyleAttr), OnClickListener {

    private val mTopEmptyView by lazy { findViewById<View>(R.id.top_empty_view) }
    private val mIvBack by lazy { findViewById<ImageView>(R.id.iv_back) }
    private val mTvTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private var mTitleStr: String? = null
    private val mTvRightTitle by lazy { findViewById<TextView>(R.id.tv_right) }
    private var mRightTitleStr: String? = null
    private var mTitleBackgroundColor: Int = 0
    private val mIvRightIcon by lazy { findViewById<ImageView>(R.id.iv_right_icon) }
    @DrawableRes
    private var mIvRightIconRes: Int = 0
    private var mIsShowBackIcon: Boolean = false
    //状态栏是否透明
    private val mNeedSetStatuBarTransparent = true

    private var mBackListener: OnClickListener? = null
    private var mRightTitleListener: OnClickListener? = null
    private var mRightIconListener: OnClickListener? = null

    init {
        init(context, attrs)
        initView(context)
        if (mNeedSetStatuBarTransparent) initStatusBar()
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
            mTitleStr = ta.getString(R.styleable.TitleBar_title_text)
            mRightTitleStr = ta.getString(R.styleable.TitleBar_title_right_text)
            mTitleBackgroundColor = ta.getColor(R.styleable.TitleBar_title_background, -1)
            mIsShowBackIcon = ta.getBoolean(R.styleable.TitleBar_title_show_backicon, true)
            mIvRightIconRes = ta.getResourceId(R.styleable.TitleBar_title_right_icon, -1)
            ta.recycle()
        }

        if (mTitleBackgroundColor != -1) {
            setBackgroundColor(mTitleBackgroundColor)
        }
        setTitleTextAppearance(context, R.style.TitleText)
        popupTheme = R.style.ThemeOverlay_AppCompat_Light
        setContentInsetsRelative(0, 0)
    }

    private fun initView(context: Context) {
        val titleView = View.inflate(context, R.layout.titlebar, null)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(titleView, params)
        if (mTitleStr != null) mTvTitle.text = mTitleStr

        if (mRightTitleStr != null) {
            mTvRightTitle.visibility = View.VISIBLE
            mTvRightTitle.text = mRightTitleStr
        }
        mTvRightTitle.setOnClickListener(this)

        mIvBack.visibility = if (mIsShowBackIcon) View.VISIBLE else View.GONE
        mIvBack.setOnClickListener(this)

        mIvRightIcon.setOnClickListener(this)
        if (mIvRightIconRes != -1) {
            mIvRightIcon.visibility = View.VISIBLE
            mIvRightIcon.setImageResource(mIvRightIconRes)
        }
    }

    private fun initStatusBar() {
        if (context is Activity) {
            val activity = context as Activity
            if (StatusBarUtil.translucent(activity)) {
                StatusBarUtil.setStatusBarLightMode(activity)
                val params = mTopEmptyView.layoutParams
                params.height = ScreenUtil.getStatusBarHeight(activity)
                mTopEmptyView.layoutParams = params
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_back -> when {
                mBackListener != null -> mBackListener?.onClick(view)
                context is Activity -> (context as Activity).finish()
                else -> ToastUtil.toast("TitleBar: context 不是 Activity")
            }
            R.id.tv_right -> mRightTitleListener?.onClick(view)
            R.id.iv_right_icon -> mRightIconListener?.onClick(view)
        }
    }

    /**
     * 设置拦截返回按钮点击事件监听，自己处理
     */
    fun setOnBackListener(action: (View) -> Unit) {
        mBackListener = OnClickListener(action)
    }

    /**
     * 设置右侧标题点击时间监听
     */
    fun setOnRightTitleListener(action: (View) -> Unit) {
        mRightTitleListener = OnClickListener(action)
    }

    /**
     * 设置标题
     */
    fun setTitleText(title: String) {
        mTitleStr = title
        mTvTitle.text = title
    }

    /**
     * 设置副标题
     */
    fun setRightTitleText(rightStr: String) {
        mRightTitleStr = rightStr
        mTvRightTitle.visibility = View.VISIBLE
        mTvRightTitle.text = rightStr
    }

    /**
     * 设置标题右侧图标点击事件
     */
    fun setRightIconListener(action: (View) -> Unit) {
        this.mRightIconListener = OnClickListener(action)
    }
}






















