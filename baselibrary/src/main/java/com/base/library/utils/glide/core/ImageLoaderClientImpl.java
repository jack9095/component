package com.base.library.utils.glide.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.widget.ImageView;

import com.base.library.utils.glide.listener.IGetBitmapListener;
import com.base.library.utils.glide.okhttp.OnProgressListener;
import com.base.library.utils.glide.okhttp.ProgressManager;
import com.base.library.utils.glide.tranform.BlurBitmapTranformation;
import com.base.library.utils.glide.tranform.GlideCircleTransformation;
import com.base.library.utils.glide.tranform.RoundBitmapTranformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.lang.ref.WeakReference;

public class ImageLoaderClientImpl implements IImageLoaderClient {

    private WeakReference<Context> mContext;

    @Override
    public void init(Context context) {
        mContext = new WeakReference<Context>(context);
    }

    @Override
    public File getCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    @UiThread
    @Override
    public void clearMemoryCache(Context context) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
            GlideApp.get(mContext.get()).clearMemory();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void clearDiskCache(final Context context) {
        new AsyncTask<Void, Void, Void> (){
            @Override
            protected Void doInBackground(Void... params) {
                if (context != null)
                    mContext = new WeakReference<Context>(context);
                    Glide.get(mContext.get()).clearDiskCache();

                return null;
            }
        };
    }

    /**
     * 获取缓存中的图片
     */
    @Override
    public void getBitmapFromCache(Context context, String url, final IGetBitmapListener listener) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
           GlideApp.with(mContext.get()).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
               @Override
               public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                   if (listener != null) {
                       listener.onBitmap(resource);
                   }
               }
           });
    }

    /**
     * @param isCache 是否是缓存 如果是：缓存策略缓存原始数据  不是的话 ：缓存策略DiskCacheStrategy.NONE：什么都不缓存
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView, boolean isCache) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(url).skipMemoryCache(isCache).diskCacheStrategy(isCache ? DiskCacheStrategy.AUTOMATIC : DiskCacheStrategy.NONE).into(imageView);
    }

    /**
     * 使用.placeholder()方法在某些情况下会导致图片显示的时候出现图片变形的情况
     * 这是因为Glide默认开启的crossFade动画导致的TransitionDrawable绘制异常
     * 设置一个加载失败和加载中的动画过渡，V4.0的使用的方法
     * @param defRes defRes 可以是个new ColorDrawable(Color.BLACK) 也可以是张图片
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes,int time) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).transition(new DrawableTransitionOptions().crossFade(time)).placeholder(defRes).error(defRes).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    @Override
    public void displayCircleImage(Context context, String url, ImageView imageView, int defRes) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes,defRes)).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    @Override
    public void displayCircleImage(Context context, int url, ImageView imageView, int defRes) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes,defRes)).into(imageView);
    }

    /**
     * 加载圆角图片
     */
    @Override
    public void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(roundRequestOptions(defRes,defRes,radius)).into(imageView);
    }

    public RequestOptions requestOptions(int placeholderResId, int errorResId) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
    }

    /**
     * 加载圆图
     */
    public RequestOptions circleRequestOptions(int placeholderResId, int errorResId) {
        return requestOptions(placeholderResId, errorResId)
                .transform(new GlideCircleTransformation());
    }

    public RequestOptions roundRequestOptions(int placeholderResId, int errorResId, int radius) {
        return requestOptions(placeholderResId, errorResId)
                .transform(new RoundBitmapTranformation(radius));
    }

    /**
     * skipMemoryCache( true )去特意告诉Glide跳过内存缓存  是否跳过内存，还是不跳过
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes, boolean cacheInMemory) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes).skipMemoryCache(cacheInMemory).into(imageView);
    }

    private RequestOptions blurRequestOptions(int defRes, int defRes1, int blurRadius) {
        return requestOptions(defRes, defRes1)
                .transform(new BlurBitmapTranformation(blurRadius));
    }

    /**
     *  加载资源文件
     */
    @Override
    public void displayImageInResource(Context context, int resId, ImageView imageView) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    /**
     * @param transformation 需要变换那种图像
     */
    @Override
    public void displayImageInResourceTransform(Context context, int resId, ImageView imageView, Transformation transformation, int errorResId) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).apply(requestOptionsTransform(errorResId,errorResId,transformation)).into(imageView);
    }

    @Override
    public void displayImageByNet(Context context, String url, ImageView imageView, int defRes, Transformation transformation) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptionsTransform(defRes,defRes,transformation)).into(imageView);
    }

    @Override
    public void disPlayImageProgressByOnProgressListener(Context context, final String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(new RequestOptions()
                        .placeholder(placeholderResId)
                        .error(errorResId))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mainThreadCallback(url,mLastBytesRead, mTotalBytes, true, e);
                        ProgressManager.removeProgressListener(internalProgressListener);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mainThreadCallback(url,mLastBytesRead, mTotalBytes, true, null);
                        ProgressManager.removeProgressListener(internalProgressListener);
                        return false;
                    }
                })
                .into(imageView);

        this.mOnProgressListener = onProgressListener;
        mMainThreadHandler = new Handler(Looper.getMainLooper());
        internalProgressListener = new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
                if (totalBytes == 0) return;
                if (mLastBytesRead == bytesRead && mLastStatus == isDone) return;

                mLastBytesRead = bytesRead;
                mTotalBytes = totalBytes;
                mLastStatus = isDone;
                mainThreadCallback(imageUrl,bytesRead, totalBytes, isDone, exception);

                if (isDone) {
                    ProgressManager.removeProgressListener(this);
                }
            }
        };
        ProgressManager.addProgressListener(internalProgressListener);
    }

    /**
     *  加载缩略图
     * @param url 图片url
     * @param backUrl 缩略图的url
     * @param thumbnailSize 如果需要放大放小的数值
     */
    @Override
    public void displayImageThumbnail(Context context, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        if (context != null) {
            mContext = new WeakReference<Context>(context);
            if (thumbnailSize == 0) {
                GlideApp.with(mContext.get())
                        .load(url)
                        .thumbnail(Glide.with(context)
                                .load(backUrl))
                        .into(imageView);
            } else {
                GlideApp.with(mContext.get())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .thumbnail(GlideApp.with(context)
                                .load(backUrl)
                                .override(thumbnailSize))// API 来强制 Glide 在缩略图请求中加载一个低分辨率图像
                        .into(imageView);
            }
        }
    }

    /**
     * thumbnail 方法有一个简化版本，它只需要一个 sizeMultiplier 参数。
     * 如果你只是想为你的加载相同的图片，但尺寸为 View 或 Target 的某个百分比的话特别有用：
     */
    @Override
    public void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView) {
        if (context != null) {
            mContext = new WeakReference<Context>(context);
            if (thumbnailSize >= 0.0F && thumbnailSize <= 1.0F) {
                GlideApp.with(mContext.get())
                        .load(url)
                        .thumbnail(/*sizeMultiplier=*/ thumbnailSize)
                        .into(imageView);
            } else {
                throw new IllegalArgumentException("thumbnailSize 的值必须在0到1之间");
            }
        }
    }

    private Handler mMainThreadHandler;
    private long mTotalBytes = 0;
    private long mLastBytesRead = 0;
    private void mainThreadCallback(final String url, final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f);
                if (mOnProgressListener != null) {
                    mOnProgressListener.onProgress(url, bytesRead, totalBytes, isDone, exception);
                }
            }
        });
    }
    private boolean mLastStatus = false;
    private OnProgressListener mOnProgressListener;
    private OnProgressListener internalProgressListener;

    public RequestOptions requestOptionsTransform(int placeholderResId, int errorResId, Transformation transformation) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId).transform(transformation);
    }
    /**
     * 加载资源文件的同时，对图片进行处理
     */
    @Override
    public void displayImageInResource(Context context, int resId, ImageView imageView, BitmapTransformation transformations) {
        if (context != null) {
            mContext = new WeakReference<Context>(context);
            GlideApp.with(mContext.get()).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).transform(transformations).into(imageView);
        }
    }

    @Override
    public void displayImageInResourceGif(Context context, int resId, ImageView imageView, BitmapTransformation transformations) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).asGif().load(resId).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
    }

    @Override
    public void displayImageNetUrl(Context context, String resId, ImageView imageView, BitmapTransformation transformations) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
//        GlideApp.with(mContext.get()).load(resId).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).transform(transformations).into(imageView);
        GlideApp.with(mContext.get()).load(resId).skipMemoryCache(true).diskCacheStrategy( DiskCacheStrategy.NONE ).transform(transformations).into(imageView);
    }

    @Override
    public void displayImageNetUrl(Context context, String resId, int defRes, ImageView imageView) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        if (defRes == 0) {
            GlideApp.with(mContext.get()).load(resId).into(imageView);
        } else {
            GlideApp.with(mContext.get()).load(resId).placeholder(defRes).error(defRes).into(imageView);
        }
    }

    @Override
    public void displayImageNetUrlGif(Context context, String resId, ImageView imageView, BitmapTransformation transformations) {
        if (context != null)
            mContext = new WeakReference<Context>(context);
        GlideApp.with(mContext.get()).asGif().load(resId).diskCacheStrategy(DiskCacheStrategy.RESOURCE).transform(transformations).into(imageView);
    }

}
