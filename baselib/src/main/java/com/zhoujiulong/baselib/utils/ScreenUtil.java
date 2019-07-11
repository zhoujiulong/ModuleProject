package com.zhoujiulong.baselib.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.ScrollView;

import java.lang.reflect.Field;

import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about screen
 * </pre>
 */
public final class ScreenUtil {

    private ScreenUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) ContextUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return ContextUtil.getContext().getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) ContextUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return ContextUtil.getContext().getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        //尝试第一种获取方式
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
            if (statusBarHeight > 0) {
                return statusBarHeight;
            }
        }
        if (statusBarHeight <= 0) {
            //第一种失败时, 尝试第二种获取方式
            Rect rectangle = new Rect();
            Window window = activity.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            statusBarHeight = rectangle.top;
            if (statusBarHeight > 0) {
                return statusBarHeight;
            }
        }
        if (statusBarHeight <= 0) {
            try {
                Class<?> c = null;
                Object obj = null;
                Field field = null;
                int x = 0;
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = activity.getResources().getDimensionPixelSize(x);
                return statusBarHeight;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return DensityUtil.dip2px(activity, 24);
    }

    /**
     * 获取导航栏高度
     */
    public static int getNavigationHeight(Context context) {
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 是否存在导航栏
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        if (context instanceof Activity) {
            WindowManager windowManager = ((Activity) context).getWindowManager();
            Display d = windowManager.getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                d.getRealMetrics(realDisplayMetrics);
            }

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        }
        return false;
    }

    /**
     * Return the density of screen.
     *
     * @return the density of screen
     */
    public static float getScreenDensity() {
        return ContextUtil.getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * Return the screen density expressed as dots-per-inch.
     *
     * @return the screen density expressed as dots-per-inch
     */
    public static int getScreenDensityDpi() {
        return ContextUtil.getContext().getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * Set full screen.
     *
     * @param activity The activity.
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Set non full screen.
     *
     * @param activity The activity.
     */
    public static void setNonFullScreen(@NonNull final Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Toggle full screen.
     *
     * @param activity The activity.
     */
    public static void toggleFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & fullScreenFlag) == fullScreenFlag) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * Return whether screen is full.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    /**
     * Set the screen to landscape.
     *
     * @param activity The activity.
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * Set the screen to portrait.
     *
     * @param activity The activity.
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * Return whether screen is landscape.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLandscape() {
        return ContextUtil.getContext().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Return whether screen is portrait.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait() {
        return ContextUtil.getContext().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Return the rotation of screen.
     *
     * @param activity The activity.
     * @return the rotation of screen
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity The activity.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar True to delete status bar, false otherwise.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * 获取控件的截图
     */
    public static Bitmap screenShotView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 获取控件的截图
     *
     * @param backgroundColorRes 背景颜色
     * @param radius             背景圆角大小
     */
    public static Bitmap screenShotView(ScrollView scrollView, @ColorRes int backgroundColorRes, @DimenRes int radius) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        h = h > scrollView.getMeasuredHeight() ? h : scrollView.getMeasuredHeight();
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        RectF rectF = new RectF(0, 0, scrollView.getWidth(), h);
        int rads = DensityUtil.getPxByResId(ContextUtil.getContext(), radius);
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(ContextUtil.getContext(), backgroundColorRes));
        canvas.drawRoundRect(rectF, rads, rads, paint);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * Return whether screen is locked.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) ContextUtil.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * Set the duration of sleep.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration The duration.
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                ContextUtil.getContext().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * Return the duration of sleep.
     *
     * @return the duration of sleep.
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    ContextUtil.getContext().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * Return whether device is tablet.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTablet() {
        return (ContextUtil.getContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Adapt the screen for vertical slide.
     *
     * @param designWidthInDp The size of design diagram's width, in dp,
     *                        e.g. the design diagram width is 720px, in XHDPI device,
     *                        the designWidthInDp = 720 / 2.
     */
    public static void adaptScreen4VerticalSlide(final Activity activity,
                                                 final int designWidthInDp) {
        adaptScreen(activity, designWidthInDp, true);
    }

    /**
     * Adapt the screen for horizontal slide.
     *
     * @param designHeightInDp The size of design diagram's height, in dp,
     *                         e.g. the design diagram height is 1080px, in XXHDPI device,
     *                         the designHeightInDp = 1080 / 3.
     */
    public static void adaptScreen4HorizontalSlide(final Activity activity,
                                                   final int designHeightInDp) {
        adaptScreen(activity, designHeightInDp, false);
    }

    /**
     * Cancel adapt the screen.
     *
     * @param activity The activity.
     */
    public static void cancelAdaptScreen(final Activity activity) {
        final DisplayMetrics appDm = ContextUtil.getContext().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = appDm.density;
        activityDm.scaledDensity = appDm.scaledDensity;
        activityDm.densityDpi = appDm.densityDpi;
    }

    /**
     * Reference from: https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     */
    private static void adaptScreen(final Activity activity,
                                    final float sizeInDp,
                                    final boolean isVerticalSlide) {
        final DisplayMetrics appDm = ContextUtil.getContext().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / sizeInDp;
        } else {
            activityDm.density = activityDm.heightPixels / sizeInDp;
        }
        activityDm.scaledDensity = activityDm.density * (appDm.scaledDensity / appDm.density);
        activityDm.densityDpi = (int) (160 * activityDm.density);
    }
}