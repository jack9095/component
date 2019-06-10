//package com.base.library.utils.glide;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//
//import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
//import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
//
///**
// * Created by fei.wang on 2019/4/29.
// */
//public class GlideCircleTransform extends BitmapTransformation {
//    public GlideCircleTransform(Context context) {
//        super(context);
//    }
//
//    /**
//     * @param pool        服用Bitmap的缓冲池
//     * @param toTransform 需要变换的图片
//     * @param outWidth    图像的宽
//     * @param outHeight   图像的高
//     * @return
//     */
//    @Override
//    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//        return circleCrop(pool, toTransform);
//    }
//
//    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
//        if (source == null) {
//            return null;
//        }
//
//        int size = Math.min(source.getWidth(), source.getHeight());
//        int x = (source.getWidth() - size) / 2;
//        int y = (source.getHeight() - size) / 2;
//
//        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
//
//        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
//        }
//
//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        paint.setAntiAlias(true);
//        float r = size / 2f;
//        canvas.drawCircle(r, r, r, paint);
//        return result;
//    }
//
//    /**
//     * 每个Transformation的标识ID
//     * 一般以本类名称即可
//     *
//     * @return
//     */
//    @Override
//    public String getId() {
//        return getClass().getName();
//    }
//}