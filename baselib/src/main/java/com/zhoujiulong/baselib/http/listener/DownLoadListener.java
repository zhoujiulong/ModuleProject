package com.zhoujiulong.baselib.http.listener;


import java.io.File;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : 下载监听
 */
public abstract class DownLoadListener {

    /**
     * 下载开始
     */
    public void onStart() {
    }

    /**
     * 下载进度
     */
    public void onProgress(int progress) {

    }

    /**
     * 下载失败
     */
    public abstract void onFail(String errorInfo);

    /**
     * 下载完成的文件
     */
    public abstract void onDone(File file);

}

















