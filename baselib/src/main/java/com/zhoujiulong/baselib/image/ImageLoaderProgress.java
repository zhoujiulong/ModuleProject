package com.zhoujiulong.baselib.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.zhoujiulong.baselib.image.listener.OnProgressListener;
import com.zhoujiulong.baselib.image.okhttpprogress.ProgressManager;
import com.zhoujiulong.baselib.utils.ToastUtil;

import java.lang.ref.WeakReference;

/**
 * @author zhoujiulong
 * @createtime 2019/4/30 17:06
 * 带进度的图片加载
 */
class ImageLoaderProgress {

    private boolean mLastStatus = false;
    private WeakReference<Handler> mMainThreadHandlerRef;
    private long mTotalBytes = 0;
    private long mLastBytesRead = 0;
    private OnProgressListener mOnProgressListener;
    private OnProgressListener mInternalProgressListener;

    public ImageLoaderProgress() {
        mMainThreadHandlerRef = new WeakReference<>(new Handler(Looper.getMainLooper()));
    }

    public void displayImage(Context context, final String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        displayImage(context, null, null, url, imageView, placeholderResId, errorResId, onProgressListener);
    }

    public void displayImage(Activity activity, final String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        displayImage(null, activity, null, url, imageView, placeholderResId, errorResId, onProgressListener);
    }

    public void displayImage(Fragment fragment, final String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        displayImage(null, null, fragment, url, imageView, placeholderResId, errorResId, onProgressListener);
    }

    private void displayImage(Context context, Activity activity, Fragment fragment, final String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        if (mMainThreadHandlerRef.get() == null) {
            mMainThreadHandlerRef = new WeakReference<>(new Handler(Looper.getMainLooper()));
        }
        mOnProgressListener = onProgressListener;
        mInternalProgressListener = new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, int percent, final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
                if (totalBytes == 0) return;
                if (mLastBytesRead == bytesRead && mLastStatus == isDone) return;
                mLastBytesRead = bytesRead;
                mTotalBytes = totalBytes;
                mLastStatus = isDone;
                mainThreadCallback(imageUrl, bytesRead, totalBytes, isDone, exception);
                if (isDone) {
                    ProgressManager.removeProgressListener(this);
                }
            }
        };
        GlideRequests glide;
        if (activity != null) {
            glide = GlideApp.with(activity);
        } else if (fragment != null) {
            glide = GlideApp.with(fragment);
        } else if (context != null) {
            glide = GlideApp.with(context);
        } else {
            ToastUtil.INSTANCE.toast("参数缺少");
            return;
        }
        glide.load(url)
                .apply(new RequestOptions().placeholder(placeholderResId).error(errorResId))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mainThreadCallback(url, mLastBytesRead, mTotalBytes, true, e);
                        ProgressManager.removeProgressListener(mInternalProgressListener);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mainThreadCallback(url, mLastBytesRead, mTotalBytes, true, null);
                        ProgressManager.removeProgressListener(mInternalProgressListener);
                        return false;
                    }
                }).into(imageView);
        ProgressManager.addProgressListener(mInternalProgressListener);
    }

    private void mainThreadCallback(final String url, final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
        Handler handler = mMainThreadHandlerRef.get();
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f);
                    if (mOnProgressListener != null) {
                        mOnProgressListener.onProgress(url, percent, bytesRead, totalBytes, isDone, exception);
                    }
                }
            });
        }
    }

}































