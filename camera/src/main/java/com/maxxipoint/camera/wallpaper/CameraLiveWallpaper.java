package com.maxxipoint.camera.wallpaper;

import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.maxxipoint.camera.wallpaper.CameraLiveUtil;

/**
 * 安卓壁纸服务
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraLiveWallpaper extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        CameraEngine engin = new CameraEngine();
        return engin;
    }

    class CameraEngine extends Engine {
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
//            CameraLiveUtil.getIntace().startPreview   (getApplicationContext(),getSurfaceHolder().getSurface(),getSurfaceHolder());
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            Log.e("onSurfaceCreated","Surface 准备完毕");
            CameraLiveUtil.getIntace().startPreview(getApplicationContext(),holder.getSurface(),holder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }
        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            CameraLiveUtil.getIntace().stopPreview();
            Log.e("onDestroy","关闭预览111");
        }
        @Override
        public void onVisibilityChanged(boolean visible) {

            if (visible) {
//                CameraLiveUtil.getIntace().startPreview(getApplicationContext(),getSurfaceHolder().getSurface(),getSurfaceHolder());
                //开启预览
            } else {
                //停止预览
//                CameraLiveUtil.getIntace().stopPreview();
            }
        }
    }
}

