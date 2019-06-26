/*  使用
        // 拦截tablayout点击事件
        View.OnTouchListener tabOnClickListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                        int pos = (int) view.getTag();
                        if (pos==0) {
                            // 拦截第一个item点击添加自定义逻辑
                            return true;
                        }
                        if (pos==1) {
                            // 拦截第二个item点击
                            return true;
                        }
                            return false;
                }
        };
        TabLayoutAddOnClickHelper.AddOnClick(tabLayout,tabOnClickListener);
 */

package com.base.library.widget;

import android.support.design.widget.TabLayout;
import android.view.View;

import java.lang.reflect.Field;

/**
 * TabLayout 添加点击事件的帮助类
 */

public class TabLayoutAddOnClickHelper {
    public static void AddOnClick(TabLayout tabLayout, View.OnTouchListener listener) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View view = getTabView(tabLayout, i);
            if (view == null) continue;
            view.setTag(i);
            view.setOnTouchListener(listener);
        }
    }

    // 获取tabview
    private static View getTabView(TabLayout tabLayout, int index) {
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        if (tab == null) return null;
        View tabView = null;
        Field view = null;
        try {
            view = TabLayout.Tab.class.getDeclaredField("mView");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        view.setAccessible(true);
        try {
            tabView = (View) view.get(tab);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return tabView;
    }
}
