package com.maxxipoint.camera2;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;
import android.view.TextureView;

public abstract class BaseMainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener{

    protected CameraDevice mCameraDevice;
    protected String mCameraID = "1";

    protected TextureView mTextureView;
    protected Size mSize;
    protected CaptureRequest.Builder mCaptureRequestBuilder;
    protected ImageReader mImageReader;

    protected Handler mHandler;
    protected HandlerThread mThreadHandler;

    // 这里定义的是ImageReader回调的图片的大小
    protected int mImageWidth = 1920;
    protected int mImageHeight = 1080;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 很多过程都变成了异步的了，所以这里需要一个子线程的looper
    protected void initLooper() {
        mThreadHandler = new HandlerThread("CAMERA2");
        mThreadHandler.start();
        mHandler = new Handler(mThreadHandler.getLooper());
    }

    // 可以通过TextureView或者SurfaceView
    protected void initView() {
        mTextureView = (TextureView) findViewById(R.id.texture_view);
        mTextureView.setSurfaceTextureListener(this);
    }

    protected void onPause() {
        if (null != mCameraDevice) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
        if (null != mImageReader) {
            mImageReader.close();
            mImageReader = null;
        }
        super.onPause();
    }
}
