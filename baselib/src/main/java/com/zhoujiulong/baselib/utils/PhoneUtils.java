package com.zhoujiulong.baselib.utils;

import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * @author zhoujiulong
 * @createtime 2019/3/7 16:14
 */
public class PhoneUtils {

    /**
     * 获取 App 版本号
     */
    public static String getAppVersion() {
        String version = "";
        try {
            PackageManager manager = ContextUtil.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(ContextUtil.getContext().getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取设备 ID
     */
    public static String getSystemId() {
        return Settings.System.getString(ContextUtil.getContext().getContentResolver(), Settings.System.ANDROID_ID);
    }

    /**
     * 跳转到权限设置界面
     */
    public static void goAppDetailSetting(Context context) {
        new PermissionSettingPage().start(context, false);
    }

    /**
     * 将文本复制到系统的剪切板
     */
    public static void clipTextToSystem(Context context, String text) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("xingfugo", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    /**
     * 获取用户选择的联系人信息
     *
     * @return list【0】:name   list[1] :phone
     */
    public static String[] getPhoneContacts(Uri uri) {
        try {
            String[] contact = {"", ""};
            //得到ContentResolver对象
            ContentResolver cr = ContextUtil.getContext().getContentResolver();
            //取得电话本中开始一项的光标
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                if (nameFieldColumnIndex < 0) return null;
                contact[0] = cursor.getString(nameFieldColumnIndex);
                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                if (nameColumnIndex < 0) return contact;
                String phoneNum = cursor.getString(nameColumnIndex);
                if (!TextUtils.isEmpty(phoneNum)) {
                    phoneNum = phoneNum.replace(" ", "");
                    contact[1] = phoneNum;
                }
                cursor.close();
            } else {
                return null;
            }
            return contact;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否开启了 wifi
     */
    public static boolean isWifiOpened(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.isWifiEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否使用代理(WiFi状态下的,避免被抓包)
     */
    public static boolean isWifiProxy() {
        try {
            String proxyAddress;
            proxyAddress = System.getProperty("http.proxyHost");
            String portstr = System.getProperty("http.proxyPort");
            int proxyPort = Integer.parseInt((portstr != null ? portstr : "-1"));
            return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 跳转到开启定位页面
     */
    public static void goOpenLocation(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }


}





















