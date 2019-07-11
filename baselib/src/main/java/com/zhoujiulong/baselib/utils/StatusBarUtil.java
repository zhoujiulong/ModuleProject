/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhoujiulong.baselib.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author cginechen
 * @date 2016-03-27
 */
public class StatusBarUtil {

    private final static int STATUSBAR_TYPE_DEFAULT = 0;
    private final static int STATUSBAR_TYPE_MIUI = 1;
    private final static int STATUSBAR_TYPE_FLYME = 2;
    private final static int STATUSBAR_TYPE_ANDROID6 = 3; // Android 6.0
    private final static int STATUSBAR_NO_MATCH = 4;

    @IntDef({STATUSBAR_TYPE_DEFAULT, STATUSBAR_NO_MATCH, STATUSBAR_TYPE_MIUI, STATUSBAR_TYPE_FLYME, STATUSBAR_TYPE_ANDROID6})
    @Retention(RetentionPolicy.SOURCE)
    private @interface StatusBarType {
    }

    private static @StatusBarType
    int mStatuBarType = STATUSBAR_TYPE_DEFAULT;

    public static boolean canTranslucent() {
        try {
            // Essential Phone 在 Android 8 之前沉浸式做得不全，系统不从状态栏顶部开始布局却会下发 WindowInsets，无语系列：ZTK C2016只能时间和电池图标变色。。。。
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !(DeviceUtil.isEssentialPhone() && Build.VERSION.SDK_INT < 26) && supportTransclentStatusBar6();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置沉浸式状态栏，安卓6.0以上
     */
    public static boolean translucent(Activity activity) {
        try {
            if (canTranslucent()) {
                Window window = activity.getWindow();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    handleDisplayCutoutMode(window);
                }
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(Color.TRANSPARENT);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置状态栏黑色字体图标，
     *
     * @param activity 需要被处理的 Activity
     */
    public static boolean setStatusBarLightMode(Activity activity) {
        try {
            if (canTranslucent()) {
                if (activity == null) return false;
                // 无语系列：ZTK C2016只能时间和电池图标变色。。。。
                if (DeviceUtil.isZTKC2016()) {
                    return false;
                }
                if (mStatuBarType == STATUSBAR_TYPE_DEFAULT) initStatusBarType(activity);

                if (mStatuBarType == STATUSBAR_TYPE_MIUI) {
                    return MIUISetStatusBarLightMode(activity.getWindow(), true);
                } else if (mStatuBarType == STATUSBAR_TYPE_FLYME) {
                    return FlymeSetStatusBarLightMode(activity.getWindow(), true);
                } else if (mStatuBarType == STATUSBAR_TYPE_ANDROID6) {
                    return Android6SetStatusBarLightMode(activity.getWindow(), true);
                }
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置状态栏白色字体图标
     */
    public static boolean setStatusBarDarkMode(Activity activity) {
        if (canTranslucent()) {
            if (activity == null) return false;
            if (mStatuBarType == STATUSBAR_TYPE_DEFAULT) initStatusBarType(activity);

            if (mStatuBarType == STATUSBAR_TYPE_MIUI) {
                return MIUISetStatusBarLightMode(activity.getWindow(), false);
            } else if (mStatuBarType == STATUSBAR_TYPE_FLYME) {
                return FlymeSetStatusBarLightMode(activity.getWindow(), false);
            } else if (mStatuBarType == STATUSBAR_TYPE_ANDROID6) {
                return Android6SetStatusBarLightMode(activity.getWindow(), false);
            }
            return false;
        } else {
            return false;
        }
    }

    @TargetApi(28)
    private static void handleDisplayCutoutMode(final Window window) {
        View decorView = window.getDecorView();
        if (ViewCompat.isAttachedToWindow(decorView)) {
            realHandleDisplayCutoutMode(window, decorView);
        } else {
            decorView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    v.removeOnAttachStateChangeListener(this);
                    realHandleDisplayCutoutMode(window, v);
                }

                @Override
                public void onViewDetachedFromWindow(View v) {

                }
            });
        }
    }

    @TargetApi(28)
    private static void realHandleDisplayCutoutMode(Window window, View decorView) {
        if (decorView.getRootWindowInsets() != null && decorView.getRootWindowInsets().getDisplayCutout() != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(params);
        }
    }

    @TargetApi(23)
    private static int changeStatusBarModeRetainFlag(Window window, int out) {
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        return out;
    }

    private static int retainSystemUiFlag(Window window, int out, int type) {
        int now = window.getDecorView().getSystemUiVisibility();
        if ((now & type) == type) {
            out |= type;
        }
        return out;
    }


    /**
     * 设置状态栏字体图标为深色，Android 6
     *
     * @param window 需要设置的窗口
     * @param light  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @TargetApi(23)
    private static boolean Android6SetStatusBarLightMode(Window window, boolean light) {
        View decorView = window.getDecorView();
        int systemUi = light ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        systemUi = changeStatusBarModeRetainFlag(window, systemUi);
        decorView.setSystemUiVisibility(systemUi);
        if (DeviceUtil.isMIUIV9()) {
            // MIUI 9 低于 6.0 版本依旧只能回退到以前的方案
            // https://github.com/Tencent/QMUI_Android/issues/160
            MIUISetStatusBarLightMode(window, light);
        }
        return true;
    }

    /**
     * 设置状态栏字体图标为深色，需要 MIUIV6 以上
     *
     * @param window 需要设置的窗口
     * @param light  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回 true
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean light) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (light) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    /**
     * 更改状态栏图标、文字颜色的方案是否是MIUI自家的， MIUI9 && Android 6 之后用回Android原生实现
     * 见小米开发文档说明：https://dev.mi.com/console/doc/detail?pId=1159
     */
    private static boolean isMIUICustomStatusBarLightModeImpl() {
        if (DeviceUtil.isMIUIV9() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        return DeviceUtil.isMIUIV5() || DeviceUtil.isMIUIV6() ||
                DeviceUtil.isMIUIV7() || DeviceUtil.isMIUIV8();
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为 Flyme 用户
     *
     * @param window 需要设置的窗口
     * @param light  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean light) {
        boolean result = false;
        if (window != null) {
            // flyme 在 6.2.0.0A 支持了 Android 官方的实现方案，旧的方案失效
            Android6SetStatusBarLightMode(window, light);
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (light) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    /**
     * 检测 Android 6.0 是否可以启用 window.setStatusBarColor(Color.TRANSPARENT)。
     * ZUK Z1是个另类，自家应用可以实现字体颜色变色，但没开放接口
     */
    private static boolean supportTransclentStatusBar6() {
        return !(DeviceUtil.isZUKZ1() || DeviceUtil.isZTKC2016());
    }

    private static void initStatusBarType(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isMIUICustomStatusBarLightModeImpl() && MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                mStatuBarType = STATUSBAR_TYPE_MIUI;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                mStatuBarType = STATUSBAR_TYPE_FLYME;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Android6SetStatusBarLightMode(activity.getWindow(), true);
                mStatuBarType = STATUSBAR_TYPE_ANDROID6;
            } else {
                mStatuBarType = STATUSBAR_NO_MATCH;
            }
        }
    }

}
