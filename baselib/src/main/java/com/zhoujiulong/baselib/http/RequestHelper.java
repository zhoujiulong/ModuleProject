package com.zhoujiulong.baselib.http;

import android.support.annotation.NonNull;
import com.zhoujiulong.baselib.http.listener.DownLoadListener;
import com.zhoujiulong.baselib.http.listener.OnTokenInvalidListener;
import com.zhoujiulong.baselib.http.listener.RequestListener;
import com.zhoujiulong.baselib.http.other.RequestErrorType;
import com.zhoujiulong.baselib.http.response.BaseResponse;
import com.zhoujiulong.baselib.utils.ContextUtil;
import com.zhoujiulong.baselib.utils.NetworkUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/03/24
 * Desc : 网络请求辅助类
 */
class RequestHelper {

    private static RequestHelper mInstance;
    private OnTokenInvalidListener mOnTokenInvalidListener;

    /**
     * 获取数据正常 code
     */
    private final int REQUEST_SUCCESS_CODE = 0;
    /**
     * 未知错误 code
     */
    private final int REQUEST_FAILD_CODE = -1;
    /**
     * Token 失效 code
     */
    private final int ON_TOKEN_INVALID_CODE_1025 = 1025;

    private RequestHelper() {
    }

    static RequestHelper getInstance() {
        if (mInstance == null) {
            synchronized (RequestHelper.class) {
                if (mInstance == null) {
                    mInstance = new RequestHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置 Token 失效回调
     */
    void setOnTokenInvalidListener(OnTokenInvalidListener onTokenInvalidListener) {
        mOnTokenInvalidListener = onTokenInvalidListener;
    }

    /**
     * 发送请求
     *
     * @param tag      请求标记，用于取消请求用
     * @param listener 请求完成后的回调
     * @param <T>      请求返回的数据对应的类型，第一层必须继承 BaseResponse
     */
    <T> void sendRequest(@NonNull final String tag, @NonNull Call<T> call, @NonNull final RequestListener<T> listener) {
        if (!NetworkUtil.isNetworkAvailable(ContextUtil.getContext())) {
            listener.requestError(null, RequestErrorType.NO_INTERNET, "网络连接失败", REQUEST_FAILD_CODE);
            return;
        }
        RequestManager.getInstance().addCall(tag, call);
        call.enqueue(new Callback<T>() {//异步请求
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (!RequestManager.getInstance().hasRequest(tag)) return;
                RequestManager.getInstance().removeCall(tag, call);
                int code = response.code();
                if (code != 200) {//接口请求失败
                    if (code == 502 || code == 404) {
                        listener.requestError(null, RequestErrorType.COMMON_ERROR, "服务器异常，请稍后重试", code);
                    } else if (code == 504) {
                        listener.requestError(null, RequestErrorType.COMMON_ERROR, "网络不给力,请检查网路", code);
                    } else {
                        listener.requestError(null, RequestErrorType.COMMON_ERROR, "网络好像出问题了哦", code);
                    }
                } else if (response.body() == null) {//返回数据为空
                    listener.requestError(null, RequestErrorType.COMMON_ERROR, "返回数据为空！", code);
                } else {//接口请求成功
                    T body = response.body();
                    if (body instanceof BaseResponse) {//判断返回的数据类型是否是继承 BaseResponse
                        BaseResponse baseResponse = (BaseResponse) body;
                        if (REQUEST_SUCCESS_CODE == baseResponse.code) {//获取数据正常
                            listener.requestSuccess(response.body());
                            //{"message":"未登录或token失效","code":1002}
                        } else if (ON_TOKEN_INVALID_CODE_1025 == baseResponse.code) {//Token失效
                            if (mOnTokenInvalidListener != null && !listener.checkLogin(baseResponse.code, baseResponse.message)) {
                                listener.requestError(response.body(), RequestErrorType.TOKEN_INVALID, baseResponse.message, baseResponse.code);
                                mOnTokenInvalidListener.onTokenInvalid(baseResponse.code, baseResponse.message);
                            }
                        } else {//从后台获取数据失败，其它未定义的错误
                            listener.requestError(response.body(), RequestErrorType.COMMON_ERROR, baseResponse.message, baseResponse.code);
                        }
                    } else {//Service类中的返回类型没有继承 BaseResponse
                        listener.requestError(null, RequestErrorType.COMMON_ERROR, "请求返回数据的第一层类型必须继承 BaseResponse！", REQUEST_FAILD_CODE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                if (!RequestManager.getInstance().hasRequest(tag)) return;
                RequestManager.getInstance().removeCall(tag, call);
                listener.requestError(null, RequestErrorType.COMMON_ERROR, "请求失败", REQUEST_FAILD_CODE);
            }
        });
    }

    /**
     * 发送下载网络请求
     *
     * @param tag              请求标记，用于取消请求用
     * @param downLoadFilePath 下载文件保存路径
     * @param downloadListener 下载回调
     */
    void sendDownloadRequest(@NonNull final String fileName, @NonNull final String tag, retrofit2.Call<ResponseBody> call,
                             @NonNull final String downLoadFilePath, @NonNull final DownLoadListener downloadListener) {
        if (!NetworkUtil.isNetworkAvailable(ContextUtil.getContext())) {
            downloadListener.onFail("网络连接失败");
            return;
        }
        RequestManager.getInstance().addCall(tag, call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                if (!RequestManager.getInstance().hasRequest(tag)) return;
                RequestManager.getInstance().removeCall(tag, call);
                if (response.code() != 200) {
                    if (response.code() == 502 || response.code() == 404) {
                        downloadListener.onFail(response.code() + "服务器异常，请稍后重试");
                    } else if (response.code() == 504) {
                        downloadListener.onFail(response.code() + "网络不给力,请检查网路");
                    } else {
                        downloadListener.onFail(response.code() + "网络好像出问题了哦");
                    }
                    return;
                }
                // 储存下载文件的目录
                File saveFile = new File(downLoadFilePath);
                if (!saveFile.exists() || !saveFile.isDirectory()) {
                    boolean mkDirSuccess = saveFile.mkdir();
                    if (!mkDirSuccess) downloadListener.onFail("创建本地的文件夹失败");
                    return;
                }
                final File file = new File(saveFile, fileName);
                downloadListener.onStart();
                final DisposableObserver<Integer> disposable = new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        downloadListener.onProgress(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        downloadListener.onFail("下载文件失败：" + e.getMessage());
                        RequestManager.getInstance().removeDisposable(tag, this);
                    }

                    @Override
                    public void onComplete() {
                        downloadListener.onDone(file);
                        RequestManager.getInstance().removeDisposable(tag, this);
                    }
                };
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) {
                        InputStream is = null;
                        FileOutputStream fos = null;
                        try {
                            is = response.body().byteStream();
                            long total = response.body().contentLength();
                            fos = new FileOutputStream(file);
                            long sum = 0;
                            int len;
                            byte[] buf = new byte[2048];
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                                sum += len;
                                int progress = (int) (sum * 1.0f / total * 100);
                                emitter.onNext(progress);
                            }
                            fos.flush();
                            emitter.onComplete();
                        } catch (Exception e) {
                            emitter.onError(e);
                        } finally {
                            try {
                                if (is != null)
                                    is.close();
                            } catch (IOException e) {
                                emitter.onError(e);
                            }
                            try {
                                if (fos != null)
                                    fos.close();
                            } catch (IOException e) {
                                emitter.onError(e);
                            }
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onTerminateDetach()//切断与上游的引用关系
                        .subscribe(disposable);
                RequestManager.getInstance().addDisposable(tag, disposable);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                if (!RequestManager.getInstance().hasRequest(tag)) return;
                RequestManager.getInstance().removeCall(tag, call);
                downloadListener.onFail("下载失败" + throwable.getMessage());
            }
        });
    }
}
















