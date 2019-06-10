package com.base.library.h5;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.base.library.base.BaseActivity;
import com.base.library.widget.ProgressWebView;

/**
 * 通用的H5页面
 * https://www.jianshu.com/p/2b2e5d417e10
 * https://blog.csdn.net/panghaha12138/article/details/78858034
 * https://www.cnblogs.com/ilovewindy/p/3795111.html
 */
public abstract class BaseCommonH5Activity extends BaseActivity {

    private static final String TAG = BaseCommonH5Activity.class.getSimpleName();
    protected ProgressWebView myWebView;

    protected ValueCallback<Uri> uploadMessage;
    protected ValueCallback<Uri[]> uploadMessageAboveL;
    protected final static int FILE_CHOOSER_RESULT_CODE = 10000;
    protected WebSettings settings;

    // 可以让 js 调用本地相机的方法
    @SuppressLint("SetJavaScriptEnabled")
    protected void setWebSetting(){
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient() {

            //  android 3.0以下：用的这个方法
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // android 3.0以上，android4.0以下：用的这个方法
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //android 4.0 - android 4.3  安卓4.4.4也用的这个方法
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType,
                                        String capture) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //android4.4 无方法。。。

            // Android 5.0及以上用的这个方法
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]>
                    filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }
        });
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"),
                FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    // 5.0 以后的WebView加载的链接为Https开头，但是链接里面的内容，比如图片为Http链接，这时候，图片就会加载不出来，怎么解决
    protected void setHttpAndHttps(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            myWebView.getSettings().setMixedContentMode(myWebView.getSettings().getMixedContentMode());
            //mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    protected void onDestroy() {
        if( myWebView!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = myWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(myWebView);
            }

            myWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            myWebView.getSettings().setJavaScriptEnabled(false);
            myWebView.clearHistory();
            myWebView.clearView();
            myWebView.removeAllViews();
            myWebView.destroy();
            myWebView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        myWebView.onPause();
        myWebView.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onResume() {
        myWebView.onResume();
        myWebView.resumeTimers();
        super.onResume();
    }

//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        if (myWebView.canGoBack()){  // 表示webview二级页面还有没关闭的
//            myWebView.goBack();
//        }else{
//            finish();
//        }
//    }
}
