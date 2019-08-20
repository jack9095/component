package com.kuanquan.home.jetpack.http

import com.kuanquan.home.jetpack.http.base.ResponseData
import com.kuanquan.home.jetpack.http.databean.Data
import retrofit2.http.GET

interface RequestService {
    @GET("wxarticle/chapters/json")
   suspend fun getDatas() : ResponseData<List<Data>>
}