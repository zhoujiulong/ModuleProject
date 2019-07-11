package com.zhoujiulong.baselib.http;

import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : HttpUtil 辅助类，管理和取消请求
 */
class RequestManager {

    private static RequestManager mInstance;

    /**
     * 保存没有完成的请求
     */
    private static Map<String, List<Call>> mCallMap = new ConcurrentHashMap<>();

    private RequestManager() {
    }

    static RequestManager getInstance() {
        if (mInstance == null) {
            synchronized (RequestManager.class) {
                if (mInstance == null) {
                    mInstance = new RequestManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 是否还有请求
     */
    synchronized boolean hasRequest(String tag) {
        return mCallMap.containsKey(tag) && mCallMap.get(tag) != null && mCallMap.get(tag).size() > 0;
    }

    /**
     * 根据请求的标记 tag 取消请求
     */
    synchronized void cancelRequestWithTag(String tag) {
        if (tag == null) return;
        if (mCallMap.containsKey(tag) && mCallMap.get(tag) != null) {
            List<Call> callList = mCallMap.get(tag);
            if (callList.size() > 0) {
                for (Call call : callList) {
                    if (call != null && !call.isCanceled()) call.cancel();
                }
                callList.clear();
            }
            mCallMap.remove(tag);
        }
    }

    /**
     * 发送请求，将请求添加到 map 中进行保存
     */
    synchronized void addCall(String tag, Call call) {
        if (tag == null || call == null) return;
        if (mCallMap.containsKey(tag) && mCallMap.get(tag) != null) {
            mCallMap.get(tag).add(call);
        } else {
            ArrayList<Call> callList = new ArrayList<>();
            callList.add(call);
            mCallMap.put(tag, callList);
        }
    }

    /**
     * 请求结束后，将请求从 map 中移除
     */
    synchronized void removeCall(String tag, Call call) {
        if (tag == null || call == null) return;
        if (mCallMap.containsKey(tag) && mCallMap.get(tag) != null) {
            List<Call> callList = mCallMap.get(tag);
            if (callList.contains(call)) {
                callList.remove(call);
            }
            if (callList.size() == 0) {
                mCallMap.remove(tag);
            }
        }
    }

}
















