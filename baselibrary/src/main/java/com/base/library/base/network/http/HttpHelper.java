package com.base.library.base.network.http;


import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.base.library.utils.ReflectUtil.checkNotNull;


/**
 * 网络请求帮助类
 */
public class HttpHelper {

    private static volatile HttpHelper mHttpHelper = null;

    private static OkHttpClient mOkHttpClient;  // okhttp的委托

    private static Retrofit mRetrofit;

    private static OkHttpClient.Builder mBuilder;

    private static String BASE_URL;

    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (mHttpHelper == null) {
            synchronized (HttpHelper.class) {
                if (mHttpHelper == null) {
                    mHttpHelper = new HttpHelper();
                }
            }
        }
        return mHttpHelper;
    }

    // TODO 初始化网络请求的方法 建议在 Application 中执行
    public void init(String baseUrl) {
        new Builder()
                .initOkHttp()
                .createRetrofit(baseUrl)
                .build();
    }


    public static class Builder {
        private OkHttpClient mOkHttpClient;

        private OkHttpClient.Builder mBuilder;

        private Retrofit mRetrofit;

        public Builder() {
        }

        /**
         * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志
         *
         * @return Builder
         */
        public Builder initOkHttp() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLogger()); // 初始化日志拦截器
            CommonInterceptor commonInterceptor = new CommonInterceptor();
            /**
             * BASEIC:请求/响应行
             * HEADER:请求/响应行 + 头
             * BODY:请求/响应航 + 头 + 体
             */
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  // 设置日志的打印级别
            if (mBuilder == null) {
                synchronized (HttpHelper.class) {
                    if (mBuilder == null) {
                        mBuilder = new OkHttpClient.Builder()
                                .addInterceptor(interceptor)
                                .addNetworkInterceptor(commonInterceptor)
                                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                        ;
                    }
                }
            }

            return this;
        }

        /**
         * 添加拦截器
         *
         * @param mInterceptor Interceptor
         * @return Builder
         */
        public Builder addInterceptor(Interceptor mInterceptor) {
            checkNotNull(mInterceptor);
            this.mBuilder.addNetworkInterceptor(mInterceptor);
            return this;
        }

        /**
         * create retrofit
         *
         * @param baseUrl baseUrl
         * @return Builder
         */
        public Builder createRetrofit(String baseUrl) {
            checkNotNull(baseUrl);
            Retrofit.Builder builder = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl);
            BASE_URL = baseUrl;
            this.mOkHttpClient = mBuilder.build();
            this.mRetrofit = builder.client(mOkHttpClient)
                    .build();
            return this;
        }

        public void build() {
            HttpHelper.getInstance().build(this);
        }

    }

    private void build(Builder builder) {
        checkNotNull(builder);
        checkNotNull(builder.mBuilder);
        checkNotNull(builder.mOkHttpClient);
        checkNotNull(builder.mRetrofit);
        mBuilder = builder.mBuilder;
        mOkHttpClient = builder.mOkHttpClient;
        mRetrofit = builder.mRetrofit;
    }

    public <T> T create(Class<T> clz) {
        checkNotNull(clz);
        checkNotNull(mRetrofit);
        return mRetrofit.create(clz);
    }

}
