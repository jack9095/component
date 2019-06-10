package com.base.library.utils.glide.core;

import android.content.Context;
import android.widget.ImageView;

import com.base.library.utils.glide.listener.IGetBitmapListener;
import com.base.library.utils.glide.okhttp.OnProgressListener;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.io.File;


public interface IImageLoaderClient {

    void init(Context context);

    // 获取缓存
    File getCacheDir(Context context);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    // 获取缓存中的图片
    void getBitmapFromCache(Context context, String url, IGetBitmapListener iGetBitmapListener);

    // 是否需要缓存
    void displayImage(Context context, String url, ImageView imageView, boolean isCache);

    // 加入过渡动画
    void displayImage(Context context, String url, ImageView imageView, int defRes, int time);

    // 是否跳过内存缓存
    void displayImage(Context context, String url, ImageView imageView, int defRes, boolean cacheInMemory);

    // 加载资源文件的同时，对图片进行处理 圆形、圆角、模糊
    void displayImageInResource(Context context, int resId, ImageView imageView, BitmapTransformation transformations);

    // 加载本地gif
    void displayImageInResourceGif(Context context, int resId, ImageView imageView, BitmapTransformation transformations);

    // 加载网络图，对图片进行处理 圆形、圆角、模糊
    void displayImageNetUrl(Context context, String resId, ImageView imageView, BitmapTransformation transformations);

    // 普通加载
    void displayImageNetUrl(Context context, String resId, int defRes, ImageView imageView);

    // 加载网络的gif图
    void displayImageNetUrlGif(Context context, String resId, ImageView imageView, BitmapTransformation transformations);

    // 圆形图
    void displayCircleImage(Context context, String url, ImageView imageView, int defRes);

    void displayCircleImage(Context context, int url, ImageView imageView, int defRes);

    // 圆角图
    void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius);

    // 加载资源文件 不做磁盘缓存
    void displayImageInResource(Context context, int resId, ImageView imageView);

    // transformation 需要装换的那种图像的风格，错误图片，或者是，正在加载中的错误图
    void displayImageInResourceTransform(Context context, int resId, ImageView imageView, Transformation transformation, int errorResId);

    // 这是对网络图片，进行的图片操作，使用的glide中的方法
    void displayImageByNet(Context context, String url, ImageView imageView, int defRes, Transformation transformation);

    // 加载缩图图     int thumbnailSize = 10;//越小，图片越小，低网络的情况，图片越小
    void displayImageThumbnail(Context context, String url, String backUrl, int thumbnailSize, ImageView imageView);

    // 如果没有两个url的话，也想，记载一个缩略图
    void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView);

    // 监听图片的下载进度，是否完成，百分比 也可以加载本地图片
    void disPlayImageProgressByOnProgressListener(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener);

}