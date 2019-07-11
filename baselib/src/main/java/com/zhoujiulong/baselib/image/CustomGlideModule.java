package com.zhoujiulong.baselib.image;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.zhoujiulong.baselib.image.okhttpprogress.ProgressManager;

import java.io.InputStream;


@GlideModule
public class CustomGlideModule extends AppGlideModule {
    /**
     * OkHttp 是一个底层网络库(相较于 Cronet 或 Volley 而言)，尽管它也包含了 SPDY 的支持。
     * OkHttp 与 Glide 一起使用可以提供可靠的性能，并且在加载图片时通常比 Volley 产生的垃圾要少。
     * 对于那些想要使用比 Android 提供的 HttpUrlConnection 更 nice 的 API，
     * 或者想确保网络层代码不依赖于 app 安装的设备上 Android OS 版本的应用，OkHttp 是一个合理的选择。
     * 如果你已经在 app 中某个地方使用了 OkHttp ，这也是选择继续为 Glide 使用 OkHttp 的一个很好的理由，就像选择其他网络库一样。
     * 添加 OkHttp 集成库的 Gradle 依赖将使 Glide 自动开始使用 OkHttp 来加载所有来自 http 和 https URL 的图片
     */
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }

    /**
     * 磁盘缓存
     * Glide 使用 DiskLruCacheWrapper 作为默认的 磁盘缓存 。
     * DiskLruCacheWrapper 是一个使用 LRU 算法的固定大小的磁盘缓存。默认磁盘大小为 250 MB ，
     * 位置是在应用的 缓存文件夹 中的一个 特定目录 。
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
    }

    /**
     * 为了维持对 Glide v3 的 GlideModules 的向后兼容性，
     * Glide 仍然会解析应用程序和所有被包含的库中的 AndroidManifest.xml 文件，
     * 并包含在这些清单中列出的旧 GlideModules 模块类。
     * 如果你已经迁移到 Glide v4 的 AppGlideModule 和 LibraryGlideModule ，你可以完全禁用清单解析。
     * 这样可以改善 Glide 的初始启动时间，并避免尝试解析元数据时的一些潜在问题。要禁用清单解析，
     * 请在你的 AppGlideModule 实现中复写 isManifestParsingEnabled() 方法：
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
