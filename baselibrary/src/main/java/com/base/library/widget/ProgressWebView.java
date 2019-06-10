package com.base.library.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.base.library.R;

/**
 * Created by fei.wang on 2019/5/8.
 */

public class ProgressWebView extends WebView {
    private ProgressBar progressbar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 6, 0, 0));
        progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_bar));

        addView(progressbar);
        //        setWebViewClient(new WebViewClient(){});
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new MyWebViewClient());


        WebSettings settings = getSettings();   // 获取WebSettings
//        settings.setBuiltInZoomControls(false); // 进行控制缩放
        settings.setSupportZoom(false);      // 设置WebView是否支持使用屏幕控件或手势进行缩放，默认是true，支持缩放。
        settings.setJavaScriptEnabled(true); // 如果访问的页面中有JavaScript，则WebView必须设置支持JavaScript，否则显示空白页面
//        settings.setMediaPlaybackRequiresUserGesture(true); // 设置WebView是否通过手势触发播放媒体，默认是true，需要手势触发。
//        settings.setDisplayZoomControls(true); // 设置WebView使用内置缩放机制时，是否展现在屏幕缩放控件上，默认true，展现在控件上。
//        settings.setAllowFileAccess(true); // 设置在WebView内部是否允许访问文件，默认允许访问。
//        settings.setSupportMultipleWindows(false); // 设置WebView是否支持多屏窗口，参考WebChromeClient#onCreateWindow，默认false，不支持
//        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 设置脚本是否允许自动打开弹窗，默认false，不允许
//        settings.setDomStorageEnabled(true);  // 设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
//        /*  重写缓存被使用到的方法，该方法基于Navigation Type，加载普通的页面，将会检查缓存同时重新验证是否需要加载，
//            如果不需要重新加载，将直接从缓存读取数据，允许客户端通过指定LOAD_DEFAULT、
//            LOAD_CACHE_ELSE_NETWORK、LOAD_NO_CACHE、LOAD_CACHE_ONLY其中之一重写该行为方法，默认值LOAD_DEFAULT*/
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        /*设置WebView是否使用viewport，当该属性被设置为false时，加载页面的宽度总是适应WebView控件宽度；当被设置为true，当前页面包含viewport属性标签，
//        在标签中指定宽度值生效，如果页面不包含viewport标签，无法提供一个宽度值，这个时候该方法将被使用*/
//        settings.setUseWideViewPort(true);
//        if (Build.VERSION.SDK_INT > 6) {
//            settings.setAppCacheEnabled(true); // 设置Application缓存API是否开启，默认false
//            settings.setLoadWithOverviewMode(true); //设置WebView是否使用预览模式加载界面。
//        }
//        String path = getContext().getFilesDir().getPath();
//        settings.setGeolocationEnabled(true);   // 设置是否开启定位功能，默认true，开启定位
//        settings.setGeolocationDatabasePath(path); // 设置WebView保存地理位置信息数据路径，指定的路径Application具备写入权限
//        getSettings().setDefaultTextEncodingName("UTF-8");
//        getSettings().setAllowContentAccess(true); // 设置WebView是否使用其内置的变焦机制，该机制结合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
//        //图片加载
//        if(Build.VERSION.SDK_INT >= 19){
//            getSettings().setLoadsImagesAutomatically(true);
//        }else {
//            getSettings().setLoadsImagesAutomatically(false);
//        }
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // 设置当一个安全站点企图加载来自一个不安全站点资源时WebView的行为
        }
    }

    //浏览器

    private class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (progressbar.getVisibility() == GONE) {
                progressbar.setVisibility(VISIBLE);
            }
            progressbar.setProgress(newProgress);
            if (newProgress >= 100) {
                progressbar.setVisibility(GONE);
            }
            super.onProgressChanged(view, newProgress);
        }


    }

    private class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.i("TAG", "onReceivedError");
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
