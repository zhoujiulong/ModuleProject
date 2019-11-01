package com.zhoujiulong.baselib.http

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Call
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : HttpUtil 辅助类，管理和取消请求
 */
internal class RequestManager private constructor() {

    companion object {

        private var mInstance: RequestManager? = null

        /**
         * 保存没有完成的请求
         */
        private val mCallMap = ConcurrentHashMap<String, ArrayList<Call<*>>>()
        /**
         * 文件下载订阅
         */
        private val mDisposableMap = ConcurrentHashMap<String, CompositeDisposable>()

        val instance: RequestManager
            get() {
                if (mInstance == null) {
                    synchronized(RequestManager::class.java) {
                        if (mInstance == null) {
                            mInstance = RequestManager()
                        }
                    }
                }
                return mInstance!!
            }
    }

    /**
     * 是否还有请求
     */
    @Synchronized
    fun hasRequest(tag: String): Boolean {
        return mCallMap.containsKey(tag) && mCallMap[tag]?.size ?: -1 > 0
    }

    /**
     * 根据请求的标记 tag 取消请求
     */
    @Synchronized
    fun cancelRequestWithTag(tag: String) {
        if (mCallMap.containsKey(tag) && mCallMap[tag] != null) {
            val callList = mCallMap[tag]
            if (callList != null && callList.isNotEmpty()) {
                for (call in callList) {
                    if (!call.isCanceled) call.cancel()
                }
                callList.clear()
            }
            mCallMap.remove(tag)
        }
        if (mDisposableMap.containsKey(tag) && mDisposableMap[tag] != null) {
            val disposable = mDisposableMap[tag]
            disposable?.dispose()
            mDisposableMap.remove(tag)
        }
    }

    /**
     * 发送请求，将请求添加到 map 中进行保存
     */
    @Synchronized
    fun addCall(tag: String, call: Call<*>) {
        if (mCallMap.containsKey(tag) && mCallMap[tag] != null) {
            mCallMap[tag]!!.add(call)
        } else {
            val callList = ArrayList<Call<*>>()
            callList.add(call)
            mCallMap[tag] = callList
        }
    }

    /**
     * 请求结束后，将请求从 map 中移除
     */
    @Synchronized
    fun removeCall(tag: String, call: Call<*>) {
        if (mCallMap.containsKey(tag) && mCallMap[tag] != null) {
            val callList = mCallMap[tag]
            if (callList!!.contains(call)) {
                callList.remove(call)
            }
            if (callList.size == 0) {
                mCallMap.remove(tag)
            }
        }
    }

    /**
     * 添加文件下载的订阅
     */
    @Synchronized
    fun addDisposable(tag: String, disposable: Disposable) {
        var compositeDisposable: CompositeDisposable? = null
        if (mDisposableMap.containsKey(tag) && mDisposableMap[tag] != null) {
            compositeDisposable = mDisposableMap[tag]
        }
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
            mDisposableMap[tag] = compositeDisposable
        }
        compositeDisposable.add(disposable)
    }

    /**
     * 移除文件下载的订阅
     */
    @Synchronized
    fun removeDisposable(tag: String, disposable: Disposable) {
        if (mDisposableMap.containsKey(tag) && mDisposableMap[tag] != null) {
            val compositeDisposable = mDisposableMap[tag]
            compositeDisposable?.remove(disposable)
        }
    }


}
















