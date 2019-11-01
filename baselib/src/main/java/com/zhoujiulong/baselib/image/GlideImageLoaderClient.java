package com.zhoujiulong.baselib.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhoujiulong.baselib.image.bean.ImageSize;
import com.zhoujiulong.baselib.image.listener.IGetBitmapListener;
import com.zhoujiulong.baselib.image.listener.IGetDrawableListener;
import com.zhoujiulong.baselib.image.listener.OnProgressListener;
import com.zhoujiulong.baselib.image.tranform.BlurBitmapTranformation;
import com.zhoujiulong.baselib.utils.ToastUtil;

import java.io.File;


/**
 * Created by shiming on 2016/10/26.
 * des:
 * with(Context context). 使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制。
 * with(Activity activity).使用Activity作为上下文，Glide的请求会受到Activity生命周期控制。
 * with(FragmentActivity activity).Glide的请求会受到FragmentActivity生命周期控制。
 * with(android.app.Fragment fragment).Glide的请求会受到Fragment 生命周期控制。
 * with(android.support.v4.app.Fragment fragment).Glide的请求会受到Fragment生命周期控制。
 */

public class GlideImageLoaderClient implements IImageLoaderClient {

    @Override
    public void init(Context context) {
    }

    @Override
    public File getCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    @UiThread
    @Override
    public void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void clearDiskCache(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //必须在子线程中  This method must be called on a background thread.
                Glide.get(context).clearDiskCache();
                return null;
            }
        };
    }

    /**
     * 获取缓存中的图片
     */
    @Override
    public void getBitmapFromCache(Context context, String url, final IGetBitmapListener listener) {
        GlideApp.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (listener != null) {
                    listener.onBitmap(resource);
                }
            }
        });
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).into(imageView);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment).load(url).into(imageView);
    }

    @Override
    public void displayImage(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity).load(url).into(imageView);
    }


    @Override
    public void displayImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId) {
        GlideApp.with(context).load(url).placeholder(placeholderResId).error(errorResId).into(imageView);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId) {
        GlideApp.with(fragment).load(url).placeholder(placeholderResId).error(errorResId).into(imageView);
    }

    @Override
    public void displayImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId) {
        GlideApp.with(activity).load(url).placeholder(placeholderResId).error(errorResId).into(imageView);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size) {
        GlideApp.with(context).load(url).placeholder(placeholderResId).error(errorResId).override(size.getWidth(), size.getHeight()).into(imageView);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size) {
        GlideApp.with(fragment).load(url).placeholder(placeholderResId).error(errorResId).override(size.getWidth(), size.getHeight()).into(imageView);
    }

    @Override
    public void displayImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size) {
        GlideApp.with(activity).load(url).placeholder(placeholderResId).error(errorResId).override(size.getWidth(), size.getHeight()).into(imageView);
    }

    @Override
    public void displayBlurImage(Context context, int resId, int blurRadius, final IGetDrawableListener listener) {
        GlideApp.with(context).load(resId).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (listener != null) {
                    listener.onDrawable(resource);
                }
            }
        });
    }

    @Override
    public void displayBlurImage(Context context, String url, int blurRadius, final IGetDrawableListener listener) {
        GlideApp.with(context).load(url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (listener != null) {
                    listener.onDrawable(resource);
                }
            }
        });
    }

    @Override
    public void displayBlurImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius) {
        GlideApp.with(context).load(url).placeholder(placeholderResId).error(errorResId).transform(new BlurBitmapTranformation(blurRadius)).into(imageView);
    }

    @Override
    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius) {
        GlideApp.with(fragment).load(url).placeholder(placeholderResId).error(errorResId).transform(new BlurBitmapTranformation(blurRadius)).into(imageView);
    }

    @Override
    public void displayBlurImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius) {
        GlideApp.with(activity).load(url).placeholder(placeholderResId).error(errorResId).transform(new BlurBitmapTranformation(blurRadius)).into(imageView);
    }

    @Override
    public void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView) {
        if (thumbnailSize >= 0.0F && thumbnailSize <= 1.0F) {
            GlideApp.with(context).load(url).thumbnail(thumbnailSize).into(imageView);
        } else {
            ToastUtil.INSTANCE.toast("thumbnailSize 的值必须在0到1之间");
        }
    }

    @Override
    public void displayImageThumbnail(Fragment fragment, String url, float thumbnailSize, ImageView imageView) {
        if (thumbnailSize >= 0.0F && thumbnailSize <= 1.0F) {
            GlideApp.with(fragment).load(url).thumbnail(thumbnailSize).into(imageView);
        } else {
            ToastUtil.INSTANCE.toast("thumbnailSize 的值必须在0到1之间");
        }
    }

    @Override
    public void displayImageThumbnail(Activity activity, String url, float thumbnailSize, ImageView imageView) {
        if (thumbnailSize >= 0.0F && thumbnailSize <= 1.0F) {
            GlideApp.with(activity).load(url).thumbnail(thumbnailSize).into(imageView);
        } else {
            ToastUtil.INSTANCE.toast("thumbnailSize 的值必须在0到1之间");
        }
    }

    @Override
    public void disPlayImageProgress(Context context, final String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        new ImageLoaderProgress().displayImage(context, url, imageView, placeholderResId, errorResId, onProgressListener);
    }

    @Override
    public void disPlayImageProgress(Fragment fragment, final String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        new ImageLoaderProgress().displayImage(fragment, url, imageView, placeholderResId, errorResId, onProgressListener);
    }

    @Override
    public void disPlayImageProgress(Activity activity, final String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        new ImageLoaderProgress().displayImage(activity, url, imageView, placeholderResId, errorResId, onProgressListener);
    }


}
