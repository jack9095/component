package com.base.library.utils;

import android.content.Context;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ASUS on 2018/7/27.
 */

public class KeyBoard {
    //此方法只是关闭软键盘
    public static void hintKbTwo(Context context, Window window) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && window.getCurrentFocus() != null) {
            if (window.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
