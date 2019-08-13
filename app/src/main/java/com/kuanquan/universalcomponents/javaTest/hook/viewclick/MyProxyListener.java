package com.kuanquan.universalcomponents.javaTest.hook.viewclick;

import android.view.View;
import com.base.library.utils.LogUtil;

/***
 * 准备要代理的类
 */
public class MyProxyListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        LogUtil.e("要代理的类","click");
    }
}
