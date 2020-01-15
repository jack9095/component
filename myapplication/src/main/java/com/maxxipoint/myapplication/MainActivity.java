package com.maxxipoint.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.*;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    WebView web_view;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web_view = findViewById(R.id.web_view);
        mEditText = findViewById(R.id.mine_et_adapter);
        setSetting(web_view);

        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // ===========调用微信支付页面===========
                // 如下方案可在非微信内部WebView的H5页面中调出微信支付
                if (url.startsWith("weixin://wap/pay?") || url.startsWith("weixin") || url.startsWith("wechat")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }

                // ===========调用QQ钱包页面（mqqapi测试效果作用较大）===========
                if (url.startsWith("mqqapi") || url.startsWith("mqqwpa")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }

                // ===========调用支付宝支付页面===========
                // ------ 对alipays:相关的scheme处理 -------
                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                                .setMessage("未检测到支付宝客户端，请安装后重试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("取消", null).show();
                    }
                    return true;
                }
                // ------- 处理结束 -------
                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    protected void setSetting(WebView mWebView) {
        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.setWebViewClient(new MyWebViewClient());

        WebSettings settings = mWebView.getSettings();
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
//        settings.defaultTextEncodingName = "utf-8"
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NORMAL);
//        settings.cacheMode = WebSettings.LOAD_NORMAL
        settings.setDomStorageEnabled(true);
//        settings.domStorageEnabled = true
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setAllowFileAccess(true);
//        settings.allowFileAccess = true
        settings.setBuiltInZoomControls(true);
//        settings.builtInZoomControls = true
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.setUseWideViewPort(true);
//        settings.useWideViewPort = true
        settings.setLoadWithOverviewMode(true);
//        settings.loadWithOverviewMode = true
        settings.setSavePassword(true);
//        settings.savePassword = true
        settings.setSaveFormData(true);
//        settings.saveFormData = true
        settings.setSupportZoom(true);
        settings.setDatabaseEnabled(true);
//        settings.databaseEnabled = true
        settings.setAppCacheMaxSize(java.lang.Long.MAX_VALUE);
        settings.setPluginState(WebSettings.PluginState.ON);
//        settings.pluginState = WebSettings.PluginState.ON
        settings.setBlockNetworkImage(false);
//        settings.blockNetworkImage = false//解决图片不显示
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.LOAD_NORMAL);
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        } else {
            //5.1以下需要开启软件加速，避免白屏，5.1以上不能开启会影响webView效率
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
    }

    public void onClick(View view) {
        web_view.loadUrl(mEditText.getText().toString().trim());
    }
}
