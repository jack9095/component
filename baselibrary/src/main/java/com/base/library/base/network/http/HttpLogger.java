package com.base.library.base.network.http;

import android.support.annotation.NonNull;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络请求日志类
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private static String TAG = HttpLogger.class.getSimpleName();

    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(@NonNull String message) {
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
        }
        if ((message.startsWith("{") && message.endsWith("}")) || (message.startsWith("[") && message.endsWith("]"))) {
//            LogUtil.json(message);
        }
        mMessage.append(message.concat("\n"));
        if (message.startsWith("<-- END HTTP")) {
//            LogUtil.e(TAG, mMessage.toString());
        }
    }
}