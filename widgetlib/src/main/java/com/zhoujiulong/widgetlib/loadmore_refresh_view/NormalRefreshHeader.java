package com.zhoujiulong.widgetlib.loadmore_refresh_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.zhoujiulong.baselib.utils.DensityUtil;
import com.zhoujiulong.widgetlib.R;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Day : 2017/09/22
 * 描述 : 下拉刷新 Header
 */

@SuppressLint("RestrictedApi")
public class NormalRefreshHeader extends RelativeLayout implements RefreshHeader {

    private ImageView mImageViewAni;
    private AnimationDrawable mRefreshAnim;
    protected int mFinishDuration = 500;
    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Scale;
    protected RefreshKernel mRefreshKernel;

    public NormalRefreshHeader(Context context) {
        this(context, null);
    }

    public NormalRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public NormalRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        mImageViewAni = new ImageView(getContext());
        mImageViewAni.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageViewAni.setImageResource(R.drawable.anim_refresh);
        int ivWidth = DensityUtil.getPxByResId(getContext(), R.dimen.dip80);
        int ivHeight = DensityUtil.getPxByResId(getContext(), R.dimen.dip80);
        LayoutParams lpHeaderLayout = new LayoutParams(ivWidth, ivHeight);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        addView(mImageViewAni, lpHeaderLayout);

    }

    private void initAni() {
        mRefreshAnim = (AnimationDrawable) mImageViewAni.getDrawable();
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {
        if (percent <= 1) {
            mImageViewAni.setScaleX(percent);
            mImageViewAni.setScaleY(percent);
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {
        if (percent <= 1) {
            mImageViewAni.setScaleX(percent);
            mImageViewAni.setScaleY(percent);
        }
    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
        if (mRefreshAnim != null) mRefreshAnim.stop();
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int headHeight, int extendHeight) {
        if (mRefreshAnim == null) initAni();
        mRefreshAnim.start();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (mRefreshAnim != null) mRefreshAnim.stop();
        return mFinishDuration;//延迟500毫秒之后再弹回
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }


    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {
        mRefreshKernel = kernel;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
            case PullDownCanceled:
            case Refreshing:
            case ReleaseToRefresh:
            case Loading:
                break;
        }
    }

}














