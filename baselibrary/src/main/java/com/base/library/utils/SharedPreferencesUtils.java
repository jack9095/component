package com.base.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：SharedPreferencesUtils
 * 类描述：
 * 创建人：fei.wang
 * 创建日期： 2016/11/7.
 * 版本：V1.0
 */
public class SharedPreferencesUtils {
    private static SharedPreferences mSharedPreferences = null;
    private static SharedPreferences.Editor mEditor = null;
    private static SharedPreferencesUtils sharePref = null;

    /**
     * @Title: 创建SharedPreferencesUtils的单例
     * @Description: 在Application全局中注册SP
     * @param PREF_NAME  名称
     * @param context   上下文
     * @return
     */
    public synchronized static SharedPreferencesUtils getInstance(String PREF_NAME, Context context) {
        if (sharePref != null) {
            return sharePref;
        } else {
            return new SharedPreferencesUtils(PREF_NAME, context);
        }

    }

    /**私有化构造函数并创建SharedPreferences*/
    private SharedPreferencesUtils(String PREF_NAME, Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static String getSharePrefString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    /**获取String类型的值*/
    public static String getSharePrefString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    /**获取boolean类型的值，第二个参数一般为false*/
    public static boolean getSharePrefBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    /**获取boolean类型的值，第二个参数一般为false*/
    public static boolean getSharePrefBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public static int getSharePrefInteger(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    public static int getSharePrefInteger(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public static long getSharePrefLong(String key) {
        return mSharedPreferences.getLong(key, -1);
    }

    public static long getSharePrefLong(String key, int value) {
        return mSharedPreferences.getLong(key, -1);
    }

    public static float getSharePrefFloat(String key) {
        return mSharedPreferences.getFloat(key, -1);
    }

    /**存储String值，返回true表示存储成功*/
    public static boolean putSharePrefString(String key, String value) {
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    /**存储boolean值，返回true表示存储成功*/
    public static boolean putSharePrefBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    /**存储float值，返回true表示存储成功*/
    public static boolean putSharePrefFloat(String key, float value) {
        mEditor.putFloat(key, value);
        return mEditor.commit();
    }

    /**存储long值，返回true表示存储成功*/
    public static boolean putSharePrefLong(String key, long value) {
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    /**存储int值，返回true表示存储成功*/
    public static boolean putSharePrefInteger(String key, int value) {
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    /**
     * 存储List<String>
     *
     * @param key
     *            List<String>对应的key
     * @param strList
     *            对应需要存储的List<String>
     */
    public static void putStrListValue(String key, List<String> strList) {
        if (null == strList) {
            return;
        }
        // 保存之前先清理已经存在的数据，保证数据的唯一性
//        removeStrList(key);
        int size = strList.size();
        putSharePrefInteger(key + "size", size);
        for (int i = 0; i < size; i++) {
            putSharePrefString(key + i, strList.get(i));
        }
    }

    /**
     * 取出List<String>
     *
     * @param key
     *            List<String> 对应的key
     * @return List<String>
     */
    public static List<String> getStrListValue(String key) {
        List<String> strList = new ArrayList<>();
        int size = getSharePrefInteger(key + "size", 0);
        //Log.d("sp", "" + size);
        for (int i = 0; i < size; i++) {
            strList.add(getSharePrefString(key + i, null));
        }
        return strList;
    }

    /**
     * 清空List<String>所有数据
     *
     * @param key
     *            List<String>对应的key
     */
    public static void removeStrList(String key) {
        int size = getSharePrefInteger(key + "size", 0);
        if (0 == size) {
            return;
        }
        removeKey(key + "size");
        for (int i = 0; i < size; i++) {
            removeKey(key + i);
        }
    }

    /**
     * @Description TODO 清空List<String>单条数据
     * @param context
     * @param key
     *            List<String>对应的key
     * @param str
     *            List<String>中的元素String
     */
    public static void removeStrListItem(Context context, String key, String str) {
        int size = getSharePrefInteger(key + "size", 0);
        if (0 == size) {
            return;
        }
        List<String> strList = getStrListValue(key);
        // 待删除的List<String>数据暂存
        List<String> removeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (str.equals(strList.get(i))) {
                if (i >= 0 && i < size) {
                    removeList.add(strList.get(i));
                    removeKey(key + i);
                    putSharePrefInteger(key + "size", size - 1);
                }
            }
        }
        strList.removeAll(removeList);
        // 删除元素重新建立索引写入数据
        putStrListValue(key, strList);
    }

    /**根据key移除对应value*/
    public static boolean removeKey(String key) {
        mEditor.remove(key);
        return mEditor.commit();
    }

    /**全部清空*/
    public static boolean clear() {
        mEditor.clear();
        return mEditor.commit();
    }

}
