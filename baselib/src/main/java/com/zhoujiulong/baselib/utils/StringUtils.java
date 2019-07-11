package com.zhoujiulong.baselib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by win7 on 2019/3/13.
 */

public class StringUtils {
    /**
     * 获取版本号
     **/
    public static String versoinName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取版本号Code
     **/
    public static int versoinCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /****
     * 判断必须包含数字字母
     * **/
    public static boolean isPw(String pw) {
        String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
        if (TextUtils.isEmpty(pw))
            return false;
        else
            return pw.matches(reg);
    }

    public static String getTimeStamp() {
        //获取当前时间戳
        long timeStamp = System.currentTimeMillis();
        return stampToDate(timeStamp);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String getTimeDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());
        formatter.format(curDate);
        return formatter.format(curDate);
    }


    /**
     * 获取随机数
     **/
    public static int getRandom() {
        Random rand = new Random();
        return rand.nextInt(1000);
    }

    /**
     * 只含有汉字、数字、字母、下划线不能以下划线开头和结尾
     **/
    public static boolean userNameMatches(String matche) {
        String format = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,11}";
        if (TextUtils.isEmpty(matche))
            return false;
        else
            return matche.matches(format);
    }

    /**
     * 判断只包含常规字符
     */
    public static boolean isNormalText(String str) {
        if (TextUtils.isEmpty(str)) return true;
        Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
        Matcher matcher = pattern.matcher(str);
        return !matcher.find();
    }

    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 格式化金额并返回元或者万元
     * 清除小数点后无用的0，如果小数点后全是0则去掉小数点
     * @param money 金额（分）
     * @return 小数点后无0
     */
    public static String clearZeroFormatMoney(Long money) {
        if (money == null) return "0";
        String moneyResult = formatMoney(money);
        if(moneyResult.indexOf(".") > 0){
            //正则表达
            moneyResult =moneyResult.replaceAll("0+?$", "");//去掉后面无用的零
            moneyResult= moneyResult.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return moneyResult;
    }

    /**
     * 格式化金额并返回元或者万元
     *
     * @param money 金额（分）
     * @return 0.00 两位小数
     */
    public static String formatMoney(Long money) {
        if (money == null) return "0.00";
        BigDecimal bigDecimalMoney = new BigDecimal(money);
        bigDecimalMoney = bigDecimalMoney.divide(new BigDecimal(100), MathContext.DECIMAL64);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(bigDecimalMoney);
    }

    /**
     * 格式化金额并返回元或者万元
     *
     * @param money 金额（分）
     * @return 0.00 两位小数
     */
    public static String formatMoney(String money) {
        if (money == null) return "0.00";
        BigDecimal bigDecimalMoney = new BigDecimal(money);
        bigDecimalMoney = bigDecimalMoney.divide(new BigDecimal(100), MathContext.DECIMAL64);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(bigDecimalMoney);
    }

    /**
     * 格式化金额并返回元或者万元
     *
     * @param money 金额（分）
     */
    public static String formatMoneyYuan(Long money) {
        if (money == null) return "0";
        BigDecimal bigDecimalMoney = new BigDecimal(money);
        bigDecimalMoney = bigDecimalMoney.divide(new BigDecimal(100), MathContext.DECIMAL64);
        DecimalFormat df = new DecimalFormat("0");
        return df.format(bigDecimalMoney);
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String bigDecimal(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供字符串四位以空格处理
     * @param string     需要空格处理字符串
     * @param scale 需要空格处理字符串 几位
     */
    public static String formatStrFour(String string, int scale) {
        StringBuilder str = new StringBuilder(string.replace(" ",""));
        int i = str.length() / scale;
        int j = str.length() % scale;
        for (int x = (j == 0 ? i - 1 : i); x > 0; x--) {
            if(x<4){//TODO 此处是根据付款码处具体修改的
                str = str.insert(x * scale," ");
            }
        }
        return str.toString();
    }
}
