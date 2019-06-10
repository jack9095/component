package com.base.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionsUtil {
    public static boolean isListEmpty(List list) {
        if (list == null) {
            return true;
        } else {
            return list.isEmpty();
        }
    }

    public static boolean isMapEmpty(Map map) {
        if (map == null) {
            return true;
        } else {
            return map.isEmpty();
        }
    }

    public static boolean isSetEmpty(Set set) {
        if (set == null) {
            return true;
        } else {
            return set.isEmpty();
        }
    }

    public static void setTextView(TextView tv, String str){
        if (!TextUtils.isEmpty(str) && !TextUtils.equals("null",str)) {
            tv.setText(str);
        } else {
            tv.setText("");
        }
    }

    public static void setTextViewOne(TextView tv, String str){
        if (!TextUtils.isEmpty(str) && !TextUtils.equals("null",str)) {
            tv.setText(str);
        } else {
            tv.setText("--");
        }
    }

    /**
     * 获取应用程序的版本号
     * @return
     */
    public static String getVersionName(Context context){
        //1.包的管理者，获取应用程序中清单文件中信息
        PackageManager packageManager = context.getPackageManager();
        try {
            //2.根据包名获取应用程序相关信息
            //packageName : 应用程序的包名
            //flags ： 指定信息的标签，指定了标签就会获取相应标签对应的相关信息
            //PackageManager.GET_ACTIVITIES : 获取跟activity相关的信息
            //getPackageName() : 获取应用程序的包名
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            //3.获取应用程序版本号名称
            String versionName = packageInfo.versionName;
            LogUtil.e("版本名称 = ",versionName);
            long longVersionCode = packageInfo.versionCode;
            LogUtil.e("版本号 = ",longVersionCode);
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //找不到包名的异常
            e.printStackTrace();
            return null;
        }
    }

}
