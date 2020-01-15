package com.maxxipoint.camera2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.os.*;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.TextureView;
import java.nio.ByteBuffer;
import java.util.Arrays;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends BaseMainActivity {

    private static final int PERMISSIONS_REQUEST_CAMERA = 454;
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSelfPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initView();
                initLooper();
            } else {
                Toast.makeText(this, "请开启权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 检查权限
     */
    private void checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{PERMISSION_CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        } else {
            initView();
            initLooper();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            // 获得所有摄像头的管理者CameraManager
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            // 获得某个摄像头的特征，支持的参数
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(mCameraID);
            // 支持的STREAM CONFIGURATION
            StreamConfigurationMap map = characteristics
                    .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            // 摄像头支持的预览Size数组
            mSize = map.getOutputSizes(SurfaceTexture.class)[0];
            // 打开相机
            cameraManager.openCamera(mCameraID, mCameraDeviceStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    // 这个方法要注意一下，因为每有一帧画面，都会回调一次此方法
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    // 用于接收相机的连接状态的更新
    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {

        // 当相机打开成功之后会回调此方法
        // 一般在此进行获取一个全局的 CameraDevice 实例，开启相机预览等操作
        // mCameraDevice = camera;//获取 CameraDevice 实例
        // createCameraPreviewSession();//创建相机预览会话
        @Override
        public void onOpened(CameraDevice camera) {
            try {
                mCameraDevice = camera;
                startPreview(mCameraDevice);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            // 相机设备失去连接(不能继续使用)时回调此方法，同时当打开相机失败时也会调用此方法而不会调用onOpened()
            // 可在此关闭相机，清除CameraDevice引用
//            camera.close();
//            mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            //相机发生错误时调用此方法
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onClosed(CameraDevice camera) {
            //相机完全关闭时回调此方法
            super.onClosed(camera);
        }
    };

    // 开始预览，主要是camera.createCaptureSession这段代码很重要，创建会话
    private void startPreview(CameraDevice camera) throws CameraAccessException {
        SurfaceTexture texture = mTextureView.getSurfaceTexture();

        // 这里设置的就是预览大小
        texture.setDefaultBufferSize(mSize.getWidth(),mSize.getHeight());
        Surface surface = new Surface(texture);
        try {
            // 设置捕获请求为预览，这里还有拍照啊，录像等
            mCaptureRequestBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        // 就是在这里，通过这个set(key,value)方法，设置曝光啊，自动聚焦等参数！！ 如下举例：
        // mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

        mImageReader = ImageReader.newInstance(mImageWidth, mImageHeight,
                ImageFormat.JPEG/* 此处还有很多格式，比如我所用到YUV等 */, 2/*
                 * 最大的图片数，
                 * mImageReader里能获取到图片数
                 * ，
                 * 但是实际中是2+1张图片，就是多一张
                 */);

        mImageReader.setOnImageAvailableListener(mOnImageAvailableListener,mHandler);
        // 这里一定分别add两个surface，一个 TextureView 的，一个ImageReader的，如果没add，会造成没摄像头预览，或者没有ImageReader的那个回调！！
        mCaptureRequestBuilder.addTarget(surface);
        mCaptureRequestBuilder.addTarget(mImageReader.getSurface());
        camera.createCaptureSession(
                Arrays.asList(surface, mImageReader.getSurface()),
                mSessionStateCallback, mHandler);
    }

    private CameraCaptureSession.StateCallback mSessionStateCallback = new CameraCaptureSession.StateCallback() {

        @Override
        public void onConfigured(CameraCaptureSession session) {
            try {
                updatePreview(session);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    };

    // 更新预览
    private void updatePreview(CameraCaptureSession session) throws CameraAccessException {
        session.setRepeatingRequest(mCaptureRequestBuilder.build(), null, mHandler);
    }

    private ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {

        /**
         * 当有一张图片可用时会回调此方法，但有一点一定要注意： 一定要调用
         * reader.acquireNextImage()和close()方法，否则画面就会卡住！！！！！我被这个坑坑了好久！！！
         * 很多人可能写Demo就在这里打一个Log，结果卡住了，或者方法不能一直被回调。
         **/
        @Override
        public void onImageAvailable(ImageReader reader) {
            Image img = reader.acquireNextImage();
            /**
             * 因为Camera2并没有Camera1的Priview回调！！！所以该怎么能到预览图像的byte[]呢？就是在这里了！！！
             * 我找了好久的办法！！！
             **/
            ByteBuffer buffer = img.getPlanes()[0].getBuffer();
            // 这里就是图片的 byte 数组了
            byte[] bytes = new byte[buffer.remaining()];

            Log.e("huangzheng", "获取到图片啦...   图片大小：" + bytes.length);
            img.close();
        }
    };

}
