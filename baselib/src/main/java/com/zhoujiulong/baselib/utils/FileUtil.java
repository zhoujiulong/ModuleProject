package com.zhoujiulong.baselib.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * @author zhoujiulong
 * @createtime 2019/4/11 9:32
 */
public class FileUtil {

    /**
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            boolean result = downloadFile.createNewFile();
        }
        return downloadFile.getAbsolutePath();
    }

    /**
     * @return 从下载连接中解析出文件名
     */
    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
















