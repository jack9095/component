package com.maxxipoint.camera.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.*;
import android.hardware.camera2.*;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.util.Size;
import android.view.*;
import android.widget.Toast;
import com.maxxipoint.camera.R;
import com.maxxipoint.camera.camera.util.*;
import com.maxxipoint.camera.wallpaper.AutoFitTextureView;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * https://blog.csdn.net/qq_21898059/article/details/50986290
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public abstract class BaseCameraActivity extends AppCompatActivity implements View.OnClickListener{
    protected static final String TAG = "MyCameraActivity";
    // 相机状态：显示相机预览。
    protected static final int STATE_PREVIEW = 0;
    // 相机状态：等待焦点被锁定。
    protected static final int STATE_WAITING_LOCK = 1;
    // 等待曝光被 Precapture 状态。
    protected static final int STATE_WAITING_PRECAPTURE = 2;
    // 相机状态：等待曝光的状态是不是Precapture。
    protected static final int STATE_WAITING_NON_PRECAPTURE = 3;
    // 相机状态：拍照。
    protected static final int STATE_PICTURE_TAKEN = 4;
    //预览视图
    protected AutoFitTextureView mTextureView;
    //拍照储存文件
    protected File mFile;
    // 默认预览状态
    protected int mState = STATE_PREVIEW;
    // 当前相机的ID。
    protected String mCameraId;
    // 一个信号量以防止应用程序在关闭相机之前退出。
    protected Semaphore mCameraOpenCloseLock = new Semaphore(1);
    // CameraDevice是连接在安卓设备上的单个相机的抽象表示,CameraDevice支持在高帧率下对捕获的图像进行细粒度控制和后期处理
    // CameraDevice是通过CameraManager返回的一个可用摄像头，每个CameraDevice自己会负责建立CameraCaptureSession以及建立CaptureRequest
    protected CameraDevice mCameraDevice;
    protected ImageReader mImageReader;
    protected Integer mSensorOrientation;
    protected Size mPreviewSize;
    // mFlashSupported 为 true 支持闪光灯
    protected boolean mFlashSupported;
    protected CaptureRequest.Builder mPreviewRequestBuilder;
    // CameraCaptureSession：某个CameraDevice的拍照请求会话，很重要的一个类
    protected CameraCaptureSession mCaptureSession;
    // CaptureRequest：一个拍照等的请求对象
    protected CaptureRequest mPreviewRequest;

    protected HandlerThread mBackgroundThread;
    protected Handler mBackgroundHandler;

    /**
     * Max preview width that is guaranteed by Camera2 API
     * Camera2 API保证的最大预览宽度
     */
    protected static final int MAX_PREVIEW_WIDTH = 1920;

    /**
     * Max preview height that is guaranteed by Camera2 API
     * Camera2 API保证的最大预览高度
     */
    protected static final int MAX_PREVIEW_HEIGHT = 1080;

    /**
     * Conversion from screen rotation to JPEG orientation.
     * 从屏幕旋转到JPEG方向的转换。
     */
    protected static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    protected static final int REQUEST_CAMERA_PERMISSION = 1;

    protected static final String FRAGMENT_DIALOG = "dialog";

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Shows a {@link Toast} on the UI thread.
     *
     * @param text The message to show
     */
    protected void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseCameraActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Retrieves the JPEG orientation from the specified screen rotation.
     * 从指定的屏幕旋转检索JPEG方向
     * @param rotation The screen rotation.
     * @return The JPEG orientation (one of 0, 90, 270, and 360)
     */
    protected int getOrientation(int rotation) {
        // Sensor orientation is 90 for most devices, or 270 for some devices (eg. Nexus 5X)
        // We have to take that into account and rotate JPEG properly.
        // For devices with orientation of 90, we simply return our mapping from ORIENTATIONS.
        // For devices with orientation of 270, we need to rotate the JPEG 180 degrees.
        return (ORIENTATIONS.get(rotation) + mSensorOrientation + 270) % 360;
    }

    // 通过 CaptureRequest.Builder 对象设置一些捕捉请求的配置  https://www.cnblogs.com/kingwild/articles/5422329.html
    protected void setAutoFlash(CaptureRequest.Builder requestBuilder) {
        if (mFlashSupported) {
            // CONTROL_AE_MODE 相机自动曝光程序所需的模式
            // 设置自动曝光模式
            requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
        }
    }

    /**
     * Configures the necessary {@link Matrix} transformation to `mTextureView`.
     * This method should be called after the camera preview size is determined in
     * setUpCameraOutputs and also the size of `mTextureView` is fixed.
     *
     * 配置必要的{@link Matrix}转换为“mTextureView”。
     *
     * 在确定相机预览大小后，应调用此方法
     *
     * setUpCameraOutputs和“mTextureView”的大小是固定的。
     *
     * @param viewWidth  The width of `mTextureView`
     * @param viewHeight The height of `mTextureView`
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void configureTransform(int viewWidth, int viewHeight) {
        if (null == mTextureView || null == mPreviewSize) {
            return;
        }
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        mTextureView.setTransform(matrix);
    }

    // 选择最合适的尺寸
    protected static Size chooseOptimalSize(Size[] choices, int textureViewWidth,
                                            int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {

        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        // Collect the supported resolutions that are smaller than the preview Surface
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        // Pick the smallest of those big enough. If there is no one big enough, pick the
        // largest of those not big enough.
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    /**
     * 检查权限
     */
    protected void requestCameraPermission() {
//        if (FragmentCompat.shouldShowRequestPermissionRationale(this.get, Manifest.permission.CAMERA)) {
//            new Camera2BasicFragment.ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
//        } else {
//            FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
//                    REQUEST_CAMERA_PERMISSION);
//        }
    }

    /**
     * 启动一个HandlerThread
     */
    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    /**
     * 初始化
     */
    protected void initView() {
        mTextureView = (AutoFitTextureView) findViewById(R.id.texture);
        findViewById(R.id.picture).setOnClickListener(this);
        findViewById(R.id.info).setOnClickListener(this);
        findViewById(R.id.re_start).setOnClickListener(this);
        //创建文件
        mFile = new File(getExternalFilesDir(null), "pic.jpg");
    }

    /**
     * This a callback object for the {@link ImageReader}. "onImageAvailable" will be called when a
     * still image is ready to be saved.
     * 注册一个监听器，当ImageReader有一个新的Image变得可用时候调用
     * https://blog.csdn.net/SekFu/article/details/78306983
     */
    protected final ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {

        @Override
        public void onImageAvailable(ImageReader reader) {
            //当图片可得到的时候获取图片并保存
            mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage(), mFile));
        }

    };

    /**
     * 设置与相机相关的成员变量。
     * @param width  相机预览的可用尺寸宽度
     * @param height 相机预览的可用尺寸的高度
     */
    protected void setUpCameraOutputs(int width, int height) {
        CameraManager manager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        try {
            // 获取可用摄像头列表
            for (String cameraId : manager.getCameraIdList()) {
                // 获取相机的相关参数。 摄像头的特征，即这个类里封装着该手机摄像头支持的参数
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                // 不使用前置摄像头。
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map == null) {
                    continue;
                }
                // 对于静态图像拍摄，使用最大的可用尺寸。
                Size largest = Collections.max(
                        Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                        new CompareSizesByArea());
                mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                        ImageFormat.JPEG, /*maxImages*/2);
                //添加ImageAvailableListener事件，当图片可得到的时候回到，也就是点击拍照的时候
                mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, mBackgroundHandler);

                // Find out if we need to swap dimension to get the preview size relative to sensor
                // coordinate.
                // 获取手机旋转的角度以调整图片的方向
                int displayRotation = this.getWindowManager().getDefaultDisplay().getRotation();
                //noinspection ConstantConditions
                mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                boolean swappedDimensions = false;
                switch (displayRotation) {
                    case Surface.ROTATION_0:
                    case Surface.ROTATION_180:
                        // 横屏
                        if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                            swappedDimensions = true;
                        }
                        break;
                    case Surface.ROTATION_90:
                    case Surface.ROTATION_270:
                        // 竖屏
                        if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                            swappedDimensions = true;
                        }
                        break;
                    default:
                        Log.e(TAG, "Display rotation is invalid: " + displayRotation);
                }

                Point displaySize = new Point();
                this.getWindowManager().getDefaultDisplay().getSize(displaySize);
                int rotatedPreviewWidth = width;
                int rotatedPreviewHeight = height;
                int maxPreviewWidth = displaySize.x;
                int maxPreviewHeight = displaySize.y;

                if (swappedDimensions) {
                    rotatedPreviewWidth = height;
                    rotatedPreviewHeight = width;
                    maxPreviewWidth = displaySize.y;
                    maxPreviewHeight = displaySize.x;
                }

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                    maxPreviewWidth = MAX_PREVIEW_WIDTH;
                }

                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                    maxPreviewHeight = MAX_PREVIEW_HEIGHT;
                }

                // Danger, W.R.! Attempting to use too large a preview size could  exceed the camera
                // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
                // garbage capture data.
                // 尝试使用太大的预览大小可能会超出摄像头的带宽限制
                mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                        rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                        maxPreviewHeight, largest);

                // 我们将TextureView的宽高比与我们选择的预览大小相匹配。
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    //横屏
                    mTextureView.setAspectRatio(
                            mPreviewSize.getWidth(), mPreviewSize.getHeight());
                } else {
                    // 竖屏
                    mTextureView.setAspectRatio(
                            mPreviewSize.getHeight(), mPreviewSize.getWidth());
                }

                // 检查闪光灯是否支持。
                Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                mFlashSupported = available == null ? false : available;
                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            //不支持Camera2API
            ErrorDialog.newInstance(getString(R.string.camera_error))
                    .show(this.getFragmentManager(), FRAGMENT_DIALOG);
        }
    }

    /**
     * 开启摄像头
     *
     * @param width
     * @param height
     */
    protected void openCamera(int width, int height) {
        // 检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
            return;
        }
        // 设置相机输出
        setUpCameraOutputs(width, height);
        // 配置变换
        configureTransform(width, height);
        CameraManager manager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        try {
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            // 打开相机预览
            manager.openCamera(mCameraId, mStateCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }
    }

    // 为相机预览创建新的 CameraCaptureSession
    protected void createCameraPreviewSession() {
        try {
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            // 将默认缓冲区的大小配置为我们想要的相机预览的大小。 设置分辨率
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            // 预览的输出Surface。
            Surface surface = new Surface(texture);
            //设置了一个具有输出Surface的CaptureRequest.Builder。
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewRequestBuilder.addTarget(surface);

            //创建一个CameraCaptureSession来进行相机预览。
            mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            // 相机已经关闭
                            if (null == mCameraDevice) {
                                return;
                            }
                            // 会话准备好后，我们开始显示预览
                            mCaptureSession = cameraCaptureSession;
                            try {
                                // 自动对焦应
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                                // 闪光灯
                                setAutoFlash(mPreviewRequestBuilder);
                                // 最终开启相机预览并添加事件
                                mPreviewRequest = mPreviewRequestBuilder.build();
                                mCaptureSession.setRepeatingRequest(mPreviewRequest,
                                        mCaptureCallback, mBackgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                            showToast("Failed");
                        }
                    }, null
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理与JPEG捕获有关的事件
     */
    private CameraCaptureSession.CaptureCallback mCaptureCallback
            = new CameraCaptureSession.CaptureCallback() {

        //处理
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void process(CaptureResult result) {
            switch (mState) {
                case STATE_PREVIEW: {
                    //预览状态
                    break;
                }

                case STATE_WAITING_LOCK: {
                    //等待对焦
                    Integer afState = result.get(CaptureResult.CONTROL_AF_STATE);
                    if (afState == null) {
                        captureStillPicture();
                    } else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState ||
                            CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState) {
                        // CONTROL_AE_STATE can be null on some devices
                        Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                        if (aeState == null || aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                            mState = STATE_PICTURE_TAKEN;
                            captureStillPicture();
                        } else {
                            runPrecaptureSequence();
                        }
                    }
                    break;
                }
                case STATE_WAITING_PRECAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null ||
                            aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE ||
                            aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                        mState = STATE_WAITING_NON_PRECAPTURE;
                    }
                    break;
                }
                case STATE_WAITING_NON_PRECAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                        mState = STATE_PICTURE_TAKEN;
                        captureStillPicture();
                    }
                    break;
                }
            }
        }

        @Override
        public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
            process(partialResult);
        }

        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            process(result);
        }

    };

    /**
     * 运行捕获静止图像的预捕获序列。 当我们从{@link #lockFocus（）}的{@link #mCaptureCallback}中得到响应时，应该调用此方法。
     */
    private void runPrecaptureSequence() {
        try {
            // 这是如何告诉相机触发的。
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);
            // 告诉 mCaptureCallback 等待 preapture 序列被设置.
            mState = STATE_WAITING_PRECAPTURE;
            mCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Capture a still picture. This method should be called when we get a response in
     * {@link #mCaptureCallback} from both {@link #lockFocus()}.
     * 拍摄静态图片。
     */
    protected void captureStillPicture() {
        try {
            if (null == mCameraDevice) {
                return;
            }
            // 这是用来拍摄照片的 CaptureRequest.Builder
            final CaptureRequest.Builder captureBuilder =
                    mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // 将 ImageReader 的 surface 作为 CaptureRequest.Builder 的目标
            captureBuilder.addTarget(mImageReader.getSurface());

            // 使用相同的AE和AF模式作为预览。 设置自动对焦模式
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

            // 设置自动曝光模式
            setAutoFlash(captureBuilder);

            // 方向
            int rotation = this.getWindowManager().getDefaultDisplay().getRotation();

            // 根据设备方向计算设置照片的方向
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getOrientation(rotation));

            // 停止连续取景
            mCaptureSession.stopRepeating();

            // 捕获静态图像
            CameraCaptureSession.CaptureCallback CaptureCallback = new CameraCaptureSession.CaptureCallback() {

                // 拍照完成时激发该方法
                @Override
                public void onCaptureCompleted(CameraCaptureSession session,CaptureRequest request,TotalCaptureResult result) {
                    showToast("图片地址: " + mFile);
                    Log.e(TAG, mFile.toString());
                    unlockFocus();
                }
            };

            // 捕获图片
            mCaptureSession.capture(captureBuilder.build(), CaptureCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将焦点锁定为静态图像捕获的第一步。（对焦）
     * 启动静态图像捕获
     */
    protected void lockFocus() {
        try {
            // 相机对焦
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
            // 修改状态
            mState = STATE_WAITING_LOCK;
            mCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback,
                    mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // 解锁焦点
    protected void unlockFocus() {
        try {
            // 重置自动对焦
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            setAutoFlash(mPreviewRequestBuilder);
            mCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback,mBackgroundHandler);
            // 将相机恢复正常的预览状态。
            mState = STATE_PREVIEW;
            // 打开连续取景模式
            mCaptureSession.setRepeatingRequest(mPreviewRequest, mCaptureCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * CameraDevice状态更改时被调用。   用于接收相机的连接状态的更新
     */
    protected final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        // 当相机打开成功之后会回调此方法
        // 一般在此进行获取一个全局的 CameraDevice 实例，开启相机预览等操作
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            // 打开相机时调用此方法。 在这里开始相机预览。
            mCameraOpenCloseLock.release();
            mCameraDevice = cameraDevice;  // 获取 CameraDevice 实例
            //创建CameraPreviewSession
            createCameraPreviewSession(); //创建相机预览会话
        }

        // 相机设备失去连接(不能继续使用)时回调此方法，同时当打开相机失败时也会调用此方法而不会调用onOpened()
        // 可在此关闭相机，清除CameraDevice引用
        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

        //相机发生错误时调用此方法
        @Override
        public void onError(CameraDevice cameraDevice, int error) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
            finish();
        }

        //相机完全关闭时回调此方法
        @Override
        public void onClosed(CameraDevice camera) {
            super.onClosed(camera);
        }
    };
}
