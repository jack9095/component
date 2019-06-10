package com.base.library.utils.glide.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.base.library.utils.glide.invocation.ImageLoaderManager;
import com.base.library.utils.glide.okhttp.ProgressManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;


@GlideModule
public class AppGlideModuleKQ extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }
    /**
     * 磁盘缓存
       Glide 使用 DiskLruCacheWrapper 作为默认的 磁盘缓存 。
       DiskLruCacheWrapper 是一个使用 LRU 算法的固定大小的磁盘缓存。默认磁盘大小为 250 MB ，
       位置是在应用的 缓存文件夹 中的一个 特定目录 。
     * 个人不建议去设置缓存，除非专门做图片APP
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
//        int memoryCacheSizeBytes = 1024 * 1024 * 250; // 250mb
        System.out.println("大小 = " + ImageLoaderManager.getInstance().getSize());
        System.out.println("文件名称 = " + ImageLoaderManager.getInstance().getFileName());
        int memoryCacheSizeBytes = 1024 * 1024 * (ImageLoaderManager.getInstance().getSize() != 0 ? ImageLoaderManager.getInstance().getSize() : 250);
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, TextUtils.isEmpty(ImageLoaderManager.getInstance().getFileName()) ? ImageLoaderManager.getInstance().getFileName() : "gaoDunImages", memoryCacheSizeBytes));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
