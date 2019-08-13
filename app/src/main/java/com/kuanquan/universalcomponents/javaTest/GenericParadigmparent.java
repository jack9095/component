package com.kuanquan.universalcomponents.javaTest;

import com.base.library.utils.LogUtil;

public class GenericParadigmparent {

    private ClassLoader classLoader;

    public GenericParadigmparent() {
        classLoader = GenericParadigmparent.class.getClassLoader();
        while (classLoader != null) {
            LogUtil.e("获取 classLoader = ", classLoader);
            classLoader = classLoader.getParent();
        }
    }
}
