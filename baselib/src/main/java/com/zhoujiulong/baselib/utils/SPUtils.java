package com.zhoujiulong.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * Created by 0169670 on 2016/11/14.
 * SharedPreferences 存储工具类
 * 除登录信息外其它地方的 key 放到主项目的 SPConstant 类中
 */
public class SPUtils {

    /**
     * 文件名
     */
    private static final String SHAREPREFERENCES_FILENAME = "worldunion_homeplus_sp";
    /**
     * 默认模式，只允许应用本身访问数据
     */
    private static final int MODE = Context.MODE_PRIVATE;

    private SPUtils() {
    }

    /**
     * 获取 ShredPreferences
     */
    private static SharedPreferences getSharedPreferences() {
        return ContextUtil.getContext().getSharedPreferences(SHAREPREFERENCES_FILENAME, MODE);
    }

    /**
     * 清空数据
     */
    public static void clearSharePreference() {
        getSharedPreferences().edit().clear().apply();
    }

    /**
     * 删除指定 SharePreferencs
     */
    public static void clearSharePreferencrForName(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    /**
     * 保存String
     */
    public static void putString(String key, String value) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获得String类型
     */
    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    /**
     * 存储boolean类型数据
     */
    public static void putBoolean(String key, boolean value) {
        Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获得Boolean类型数据
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * 保存int类型
     */
    public static void putInt(String key, int value) {
        Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 保存long类型
     */
    public static void putLong(String key, long value) {
        Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获得int类型
     */
    public static int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }

    /**
     * 获得int类型
     */
    public static long getLong(String key, long defValue) {
        return getSharedPreferences().getLong(key, defValue);
    }

    /**
     * SharedPreferences 对象数据初始化
     */
    public static void putObject(String key, Object object) {
        String jsonObject = JsonUtil.object2String(object);
        putString(key, jsonObject);
    }

    /**
     * SharedPreferencesȡ 获取对象
     */
    public static <T> T getObject(String key, Class<T> cls) {
        String json = getString(key, "");
        if (!TextUtils.isEmpty(json)) {
            try {
                return JsonUtil.jsonToBean(json, cls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
