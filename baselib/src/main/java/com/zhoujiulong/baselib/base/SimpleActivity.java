package com.zhoujiulong.baselib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : SimpleActivity
 */
public abstract class SimpleActivity extends FragmentActivity implements View.OnClickListener {

    protected final String TAG = this.getClass().getName();

    protected Context mContext;
    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityManager.getInstance().addActivity(this);

        mContext = this;
        mActivity = this;

        initView();
        initPresenter();
        attachView();
        initListener();
        initData();
        getData();
    }

    @Override
    protected void onDestroy() {
        detachView();
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }


    /* ********************************************** 初始化相关方法 **************************************************** */
    /* ********************************************** 初始化相关方法 **************************************************** */

    /**
     * 获取布局文件资源 id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化逻辑处理层
     */
    protected abstract void initPresenter();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 获取数据
     */
    protected abstract void getData();

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


    /* ********************************************** 点击输入框外部隐藏软键盘 **************************************************** */
    /* ********************************************** 点击输入框外部隐藏软键盘 **************************************************** */

    /**
     * 点击输入框外部是否要隐藏键盘，默认为 true
     */
    protected boolean mNeedClickOutHideInput = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mNeedClickOutHideInput && ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v     View
     * @param event Event
     * @return boolean
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param view View
     */
    public final void hideSoftInput(@NonNull View view) {
        IBinder token = view.getWindowToken();
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}













