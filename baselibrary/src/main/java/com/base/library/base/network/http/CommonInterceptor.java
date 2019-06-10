package com.base.library.base.network.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.library.base.constant.SpUtils;
import com.base.library.base.network.response.BaseResponse;
import com.base.library.utils.GsonUtils;
import com.base.library.utils.LogUtil;
import com.base.library.utils.SharedPreferencesUtils;

/**
 * 添加通用请求参数的拦截器
 */
public class CommonInterceptor implements Interceptor {
    private static final String TAG = CommonInterceptor.class.getSimpleName();

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        String language = SharedPreferencesUtils.getSharePrefString(SpUtils.LANGUAGE);
        String token = SharedPreferencesUtils.getSharePrefString(SpUtils.TOKEN);
        LogUtil.e("Authorization = ",token);

        Request request = chain.request().newBuilder()
                .addHeader("Authorization", token)
                .addHeader("X-CHARACTER-KEY", language)
                .build();

        LogUtil.e(TAG + "   request  URL Method", "request:" + request.toString());
        LogUtil.e(TAG + "   request  head", "request:" + request.headers().toString());
//        RequestBody body = request.body();
//        if (body instanceof FormBody) {
//            FormBody formBody = (FormBody) body;
//            for (int i = 0; i < formBody.size(); i++) {
//                LogUtil.e(TAG + "   request  body", "key: " + formBody.name(i) + "   value: " + formBody.value(i));
//            }
//        }

        Response response = chain.proceed(request);
        MediaType mediaType = response.body().contentType();

        String content = "";
        if (response != null && response.body() != null) {
            content = response.body().string();
        }

        LogUtil.e(TAG + "   response", "response body:" + content);
        if (!TextUtils.isEmpty(content)) {
            BaseResponse baseBean = GsonUtils.toObject(content, BaseResponse.class);
            if (baseBean != null && baseBean.code == 9) { // 未登录或会话已失效
                ARouter.getInstance().build("/main/login").navigation();
            }
        }
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}
