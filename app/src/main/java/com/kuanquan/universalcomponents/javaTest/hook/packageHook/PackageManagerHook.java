package com.kuanquan.universalcomponents.javaTest.hook.packageHook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import com.base.library.utils.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态修改APP的版本号
 */
public class PackageManagerHook {

    /**
     *  越早 hook 越好，推荐在 Application.attachBaseContext() 中调用
     */
    @SuppressLint("PrivateApi")
    public static void hook(final Context context){
        try{
            // 1. 得到 ActivityThread 类
            Class<?> activityThreadClz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = activityThreadClz.getMethod("currentActivityThread");

            // 2. 得到当前的 ActivityThread 对象
            Object activityThread = currentActivityThread.invoke(null);

            // 3. 得到 PackageManager 对象
            Method getPackageManager = activityThreadClz.getMethod("getPackageManager");
            final Object pkgManager = getPackageManager.invoke(activityThread);
            Class<?> packageManagerClz = Class.forName("android.content.pm.IPackageManager");

            // hook sPackageManager
            Field packageManagerField = activityThreadClz.getDeclaredField("sPackageManager");
            packageManagerField.setAccessible(true);

            // 动态代理
            packageManagerField.set(activityThread, Proxy.newProxyInstance(context.getClassLoader(),
                    new Class[]{packageManagerClz},((proxy, method, args) -> {
                        Object result = method.invoke(pkgManager, args);
                        if ("getPackageInfo".equals(method.getName())) {
                            PackageInfo pkgInfo = (PackageInfo) result;

                            // 修改 APP 的版本信息
                            pkgInfo.versionCode = 168;
                            pkgInfo.versionName = "v1.6.8";
                        }
                        return result;
                    })));

        } catch (Exception e){
            LogUtil.e("动态修改版本号异常 = ",e);
        }
    }
}
