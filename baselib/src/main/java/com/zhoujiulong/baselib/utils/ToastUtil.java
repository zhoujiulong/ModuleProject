package com.zhoujiulong.baselib.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.zhoujiulong.baselib.R;

/**
 * Toast工具类
 */
public class ToastUtil {

    private static Toast mToast;
    private static TextView mTvMsg;

    /**
     * 显示一个Toast
     *
     * @param strResId 字符串资源ID
     */
    public static void toast(int strResId) {
        toast(strResId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个Toast
     *
     * @param msg 信息
     */
    public static void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个Toast
     *
     * @param strResId 字符串资源ID
     */
    public static void toastLong(int strResId) {
        toast(strResId, Toast.LENGTH_LONG);
    }

    /**
     * 显示一个Toast
     *
     * @param msg 信息
     */
    public static void toastLong(String msg) {
        toast(msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示一个Toast
     *
     * @param strResId 字符串资源ID
     * @param time     显示时间
     */
    public static void toast(int strResId, int time) {
        toast(ContextUtil.getContext().getResources().getString(strResId), time);
    }

    /**
     * 显示一个Toast
     *
     * @param msg  信息
     * @param time 显示时间
     */
    public static void toast(String msg, int time) {
        if (mToast == null) {
            View v = LayoutInflater.from(ContextUtil.getContext()).inflate(R.layout.layout_toast, null);
            mTvMsg = v.findViewById(R.id.tv_msg);
            mToast = new Toast(ContextUtil.getContext());
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setView(v);
            mToast.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
                    //弹窗消失，置为空，不然会出现不弹出提示
                    mToast = null;
                    mTvMsg = null;
                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                }
            });
        }
        mTvMsg.setText(msg);
        mToast.setDuration(time);
        mToast.show();
    }

    /**
     * 成功提示
     */
    public static void showSuccess(String msg) {
        View v = LayoutInflater.from(ContextUtil.getContext()).inflate(R.layout.layout_state_toast, null);
        TextView tvMsg = v.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvMsg.setVisibility(android.text.TextUtils.isEmpty(msg) ? View.GONE : View.VISIBLE);
        ImageView ivIcon = v.findViewById(R.id.iv_icon);
        ivIcon.setImageResource(R.mipmap.ic_lib_toastsuccess);
        Toast toast = new Toast(ContextUtil.getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(v);
        toast.show();
    }

    /**
     * 失败提示
     */
    public static void showFaild(String msg) {
        View v = LayoutInflater.from(ContextUtil.getContext()).inflate(R.layout.layout_state_toast, null);
        Toast toast = new Toast(ContextUtil.getContext());
        TextView tvMsg = v.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvMsg.setVisibility(android.text.TextUtils.isEmpty(msg) ? View.GONE : View.VISIBLE);
        ImageView ivIcon = v.findViewById(R.id.iv_icon);
        ivIcon.setImageResource(R.mipmap.ic_lib_tost_failed);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(v);
        toast.show();
    }
}






















