package com.example.mycustomkeyboard;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WebviewActivity extends Activity {

    private WebView web;
    private MyKeyBoardViewWeb keyBoardView;
    private RelativeLayout root;
    private RelativeLayout keyboardRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        root = (RelativeLayout) findViewById(R.id.root);
        keyBoardView = (MyKeyBoardViewWeb) findViewById(R.id.mykeyboard);
        keyboardRoot = (RelativeLayout) findViewById(R.id.mykeyboard_root);

        web = findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.addJavascriptInterface(new DemoJavaScriptInterface(),"demo");
        web.loadUrl("file:///android_asset/index.html");
    }

    public class DemoJavaScriptInterface {

        @JavascriptInterface
        public void showInput(final int height) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (keyBoardView.getVisibility() != View.VISIBLE) {
                        keyBoardView.setAttach(web, height, root, keyboardRoot);
                    }
                }
            });
        }
    }
}
