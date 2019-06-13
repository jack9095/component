package com.kuanquan.universalcomponents.viewmodel

import com.base.library.base.network.http.HttpHelper
import com.base.library.base.viewmodel.BaseViewModel
import com.kuanquan.universalcomponents.kotlinTest.MainApiService

open class MainBaseViewModel : BaseViewModel() {
        var serviceApi: MainApiService? = null
    init {
         serviceApi = HttpHelper.getInstance().create(MainApiService::class.java)
    }
}