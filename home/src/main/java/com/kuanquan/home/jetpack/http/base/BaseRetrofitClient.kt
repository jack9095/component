package com.kuanquan.home.jetpack.http.base

//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 2019/5/31
 * BaseRetrofitClient
 */
abstract class BaseRetrofitClient {

    /**
     * 获取对应的 service
     * @param service service class
     * @param baseUrl basUrl
     * @return service
     */
    fun <S> getService(service: Class<S>,baseUrl: String): S {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(service)
    }
}