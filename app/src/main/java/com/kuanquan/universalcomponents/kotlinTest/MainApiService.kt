package com.kuanquan.universalcomponents.kotlinTest

import com.base.library.base.network.response.BaseResponse
import io.reactivex.Observable
import retrofit2.http.POST

interface MainApiService {

    @POST("ee/api/xx/ff")
    fun postInfo(): Observable<BaseResponse<UserBean>>
}