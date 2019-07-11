package com.zhoujiulong.baselib.image;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import com.zhoujiulong.baselib.image.bean.ImageSize;
import com.zhoujiulong.baselib.image.listener.IGetBitmapListener;
import com.zhoujiulong.baselib.image.listener.IGetDrawableListener;
import com.zhoujiulong.baselib.image.listener.OnProgressListener;

import java.io.File;


/**
 * Created by shiming on 2016/10/26.
 */

public interface IImageLoaderClient {

    void init(Context context);

    File getCacheDir(Context context);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    void getBitmapFromCache(Context context, String url, IGetBitmapListener listener);

    void displayImage(Context context, String url, ImageView imageView);

    void displayImage(Fragment fragment, String url, ImageView imageView);

    void displayImage(Activity activity, String url, ImageView imageView);

    void displayImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId);

    void displayImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId);

    void displayImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId);

    void displayImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size);

    void displayImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size);

    void displayImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size);

    void displayBlurImage(Context context, int resId, int blurRadius, IGetDrawableListener listener);

    void displayBlurImage(Context context, String url, int blurRadius, IGetDrawableListener listener);

    void displayBlurImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius);

    void displayBlurImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius);

    void displayBlurImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius);

    void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView);

    void displayImageThumbnail(Fragment fragment, String url, float thumbnailSize, ImageView imageView);

    void displayImageThumbnail(Activity activity, String url, float thumbnailSize, ImageView imageView);

    void disPlayImageProgress(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener);

    void disPlayImageProgress(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener);

    void disPlayImageProgress(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener);
}
