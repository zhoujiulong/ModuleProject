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
 * GlideApp 是自动生成的哦
 * 图片加载工具类
 */
public class ImageLoader implements IImageLoaderClient {

    private volatile static ImageLoader instance;
    private IImageLoaderClient client;

    private ImageLoader() {
        createClient();
    }

    private void createClient() {
        client = new GlideImageLoaderClient();
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    @Override
    public void init(Context context) {
    }

    @Override
    public File getCacheDir(Context context) {
        if (client == null) createClient();
        return client.getCacheDir(context);
    }

    @Override
    public void clearMemoryCache(Context context) {
        if (client == null) createClient();
        client.clearMemoryCache(context);
    }

    @Override
    public void clearDiskCache(Context context) {
        if (client == null) createClient();
        client.clearDiskCache(context);
    }

    @Override
    public void getBitmapFromCache(Context context, String url, IGetBitmapListener listener) {
        if (client == null) createClient();
        client.getBitmapFromCache(context, url, listener);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView) {
        if (client == null) createClient();
        client.displayImage(context, url, imageView);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView) {
        if (client == null) createClient();
        client.displayImage(fragment, url, imageView);
    }

    @Override
    public void displayImage(Activity activity, String url, ImageView imageView) {
        if (client == null) createClient();
        client.displayImage(activity, url, imageView);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId) {
        if (client == null) createClient();
        client.displayImage(context, url, imageView, placeholderResId, errorResId);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId) {
        if (client == null) createClient();
        client.displayImage(fragment, url, imageView, placeholderResId, errorResId);
    }

    @Override
    public void displayImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId) {
        if (client == null) createClient();
        client.displayImage(activity, url, imageView, placeholderResId, errorResId);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size) {
        if (client == null) createClient();
        client.displayImage(context, url, imageView, placeholderResId, errorResId, size);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size) {
        if (client == null) createClient();
        client.displayImage(fragment, url, imageView, placeholderResId, errorResId, size);
    }

    @Override
    public void displayImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId, ImageSize size) {
        if (client == null) createClient();
        client.displayImage(activity, url, imageView, placeholderResId, errorResId, size);
    }

    @Override
    public void displayBlurImage(Context context, int resId, int blurRadius, IGetDrawableListener listener) {
        if (client == null) createClient();
        client.displayBlurImage(context, resId, blurRadius, listener);
    }

    @Override
    public void displayBlurImage(Context context, String url, int blurRadius, final IGetDrawableListener listener) {
        if (client == null) createClient();
        client.displayBlurImage(context, url, blurRadius, listener);
    }

    @Deprecated
    @Override
    public void displayBlurImage(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius) {
        if (client == null) createClient();
        client.displayBlurImage(context, url, imageView, placeholderResId, errorResId, blurRadius);
    }

    @Override
    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius) {
        if (client == null) createClient();
        client.displayBlurImage(fragment, url, imageView, placeholderResId, errorResId, blurRadius);
    }

    @Override
    public void displayBlurImage(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId, int blurRadius) {
        if (client == null) createClient();
        client.displayBlurImage(activity, url, imageView, placeholderResId, errorResId, blurRadius);
    }

    @Override
    public void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView) {
        if (client == null) createClient();
        client.displayImageThumbnail(context, url, thumbnailSize, imageView);
    }

    @Override
    public void displayImageThumbnail(Fragment fragment, String url, float thumbnailSize, ImageView imageView) {
        if (client == null) createClient();
        client.displayImageThumbnail(fragment, url, thumbnailSize, imageView);
    }

    @Override
    public void displayImageThumbnail(Activity activity, String url, float thumbnailSize, ImageView imageView) {
        if (client == null) createClient();
        client.displayImageThumbnail(activity, url, thumbnailSize, imageView);
    }

    @Override
    public void disPlayImageProgress(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        if (client == null) createClient();
        client.disPlayImageProgress(context, url, imageView, placeholderResId, errorResId, onProgressListener);
    }

    @Override
    public void disPlayImageProgress(Fragment fragment, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        if (client == null) createClient();
        client.disPlayImageProgress(fragment, url, imageView, placeholderResId, errorResId, onProgressListener);
    }

    @Override
    public void disPlayImageProgress(Activity activity, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        if (client == null) createClient();
        client.disPlayImageProgress(activity, url, imageView, placeholderResId, errorResId, onProgressListener);
    }
}
