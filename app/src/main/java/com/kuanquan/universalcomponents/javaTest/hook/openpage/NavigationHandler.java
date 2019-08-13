package com.kuanquan.universalcomponents.javaTest.hook.openpage;

import com.base.library.utils.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 处理代理方法
 */
public class NavigationHandler implements InvocationHandler {

    private INavigation target;

    public NavigationHandler(INavigation target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //
        LogUtil.e("准备打开页面");
        Object invoke = method.invoke(target, args);
        LogUtil.e("已经打开页面");
        return invoke;
    }
}
