package com.kuanquan.networklibrary.test.common

import com.kuanquan.networklibrary.http.BaseResponse
import com.kuanquan.networklibrary.test.DataResult
import retrofit2.http.GET

interface RequestService {

    @GET("data/Android/2/1")
    suspend fun getSuspendAndroidGank(): BaseResponse<DataResult>

    @GET("data/iOS/2/1")
    suspend fun getSuspendIOSGank(): BaseResponse<DataResult>
}