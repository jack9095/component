package com.kuanquan.home.jetpack.http.repository

import com.kuanquan.home.jetpack.http.RetrofitClient
import com.kuanquan.home.jetpack.http.base.BaseRepository
import com.kuanquan.home.jetpack.http.base.ResponseData
import com.kuanquan.home.jetpack.http.databean.Data

class ArticleRepository : BaseRepository() {

    suspend fun getDatas(): ResponseData<List<Data>> = request {
        RetrofitClient.reqApi.getDatas()
    }
}