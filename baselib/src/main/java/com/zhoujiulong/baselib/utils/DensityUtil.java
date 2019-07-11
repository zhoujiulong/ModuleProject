package com.zhoujiulong.baselib.utils;

import android.content.Context;
import android.support.annotation.DimenRes;

/**
 * 尺寸转换工具类
 */
public class DensityUtil {

    /**
     * 根据资源文件中的配置获取对应的px
     * @param context 上下文
     * @param dimRes 资源文件中的 id
     * @return px
     */
    public static int getPxByResId(Context context, @DimenRes int dimRes) {
        return context.getResources().getDimensionPixelSize(dimRes);
    }

    /**
     * @param context 上下文
     * @param dpValue dp数值
     * @return dp to  px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param context 上下文
     * @param pxValue px的数值
     * @return px to dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}