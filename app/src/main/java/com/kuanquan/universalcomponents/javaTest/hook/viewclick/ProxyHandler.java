package com.kuanquan.universalcomponents.javaTest.hook.viewclick;

import android.view.View;
import com.base.library.utils.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 实现 InvocationHandler
 */
public class ProxyHandler implements InvocationHandler {

    private View.OnClickListener mListener;

    public ProxyHandler(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这里也可以进行数据埋点等操作
        LogUtil.e("代理拦截操作");
        return method.invoke(mListener,args);
    }
}
