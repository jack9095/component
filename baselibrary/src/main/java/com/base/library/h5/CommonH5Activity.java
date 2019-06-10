package com.base.library.h5;

import android.annotation.SuppressLint;
import android.view.View;

import com.base.library.R;
import com.base.library.base.constant.EventType;
import com.base.library.bean.H5ShareBeanQuest;
import com.base.library.utils.LogUtil;
import com.base.library.widget.TopNavigationLayout;

/**
 * 通用的H5页面
 */
public class CommonH5Activity extends BaseCommonH5Activity implements JSInterface.OnClickH5Listener {

    private static final String TAG = CommonH5Activity.class.getSimpleName();
    TopNavigationLayout mTopNavigationLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initView() {
        mTopNavigationLayout = findViewById(R.id.top_navigation_a_f);
        mTopNavigationLayout.setClick(this);
        myWebView = findViewById(R.id.web_view);
        settings = myWebView.getSettings();
        setWebSetting();
        setHttpAndHttps();
        JSInterface mJSInterface = new JSInterface(this);
        myWebView.addJavascriptInterface(mJSInterface,"app");
    }

    @Override
    protected void initData() {
        String h5_title = getIntent().getStringExtra("h5_title");
        mTopNavigationLayout.setTvTitle(h5_title);
        String h5_url = getIntent().getStringExtra("h5_url");
        LogUtil.e("传递到h5的数据 = ", h5_url);
        // WebView加载web资源
        myWebView.loadUrl(h5_url);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onClick(View view) {
//       finish();
//        myWebView.loadUrl("javascript: anchorChapter()");
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void share(String url, String content, String title, String picUrl, String shareWord) {
        postEventBus(EventType.H5_JUMP_WENJUAN,new H5ShareBeanQuest(url,content,title,picUrl,shareWord));
    }

}
