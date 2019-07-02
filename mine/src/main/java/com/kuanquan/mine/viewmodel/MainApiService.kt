package com.kuanquan.mine.viewmodel

import com.base.library.base.network.response.BaseResponse
import com.kuanquan.mine.bean.MyReceivingAddressBean
import io.reactivex.Observable
import retrofit2.http.POST

interface MainApiService {

    @POST("ee/api/xx/ff")
    fun postInfo(): Observable<BaseResponse<MyReceivingAddressBean>>
}