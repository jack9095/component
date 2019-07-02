package com.kuanquan.mine.viewmodel

import com.base.library.base.network.http.HttpHelper
import com.base.library.base.viewmodel.BaseViewModel

open class MineBaseViewModel : BaseViewModel() {
        var serviceApi: MainApiService? = null
    init {
         serviceApi = HttpHelper.getInstance().create(MainApiService::class.java)
    }
}