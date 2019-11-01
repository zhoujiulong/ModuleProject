package com.zhoujiulong.baselib.http.listener


import java.io.File

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : 下载监听
 */
abstract class DownLoadListener {

    /**
     * 下载开始
     */
    fun onStart() {}

    /**
     * 下载进度
     */
    fun onProgress(progress: Int) {

    }

    /**
     * 下载失败
     */
    abstract fun onFail(errorInfo: String)

    /**
     * 下载完成的文件
     */
    abstract fun onDone(file: File)

}

















