package com.kuanquan.universalcomponents.javaTest.hook.openpage;

import android.content.Context;
import android.content.Intent;
import com.base.library.utils.LogUtil;

/**
 * 定义接口 （JDK动态代理基于接口实现的）
 */
public interface INavigation {

    default void openPage(String activityName, Context context){  // 用了 java8 的 default 方法
        LogUtil.e("打开","activity名称 = " + activityName);
        context.startActivity(new Intent(context, OpenPagerActivity.class));
    }
}
