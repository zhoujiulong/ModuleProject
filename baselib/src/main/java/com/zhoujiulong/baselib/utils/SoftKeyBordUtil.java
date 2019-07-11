package com.zhoujiulong.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftKeyBordUtil {

    /**
     * 打开软键盘
     */
    public static void openKeybord(EditText mEditText, Activity activity) {
        if (isSoftInputShow(activity)) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(View view, Activity activity) {
        if (!isSoftInputShow(activity)) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 判断当前软键盘是否打开
     */
    public static boolean isSoftInputShow(Activity activity) {
        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputmanger != null) {
                return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
            }
        }
        return false;
    }

}
