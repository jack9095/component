package com.maxxipoint.camera.camera;

import android.graphics.*;
import android.os.*;
import android.support.annotation.RequiresApi;
import android.util.*;
import android.view.*;
import com.maxxipoint.camera.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyCameraActivity extends BaseCameraActivity {

    // TextureView 监听
    private final TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            Log.e("SurfaceTextureListener", "onSurfaceTextureAvailable");
            //当TextureView可用的时候 打开预览摄像头
            Log.e(TAG, "width:" + width + "  height:" + height);
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
//            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        // 这个方法要注意一下，因为每有一帧画面，都会回调一次此方法
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();
        /**
         *  当屏幕关闭并重新打开时，SurfaceTexture 已经可用，“onSurfaceTextureAvailable”将不被调用。
         *  在这种情况下，我们可以打开相机并从这里开始预览（否则，我们等待 SurfaceTextureListener 中的表面准备就绪）。
         */
        if (mTextureView.isAvailable()) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.picture:
                break;
        }
        if (v.getId() == R.id.picture) {
            lockFocus();
        }
    }
}
