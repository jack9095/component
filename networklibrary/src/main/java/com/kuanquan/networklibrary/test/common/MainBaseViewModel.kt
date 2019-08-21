package com.kuanquan.networklibrary.test.common

import com.kuanquan.networklibrary.BaseViewModel
import com.kuanquan.networklibrary.http.HttpHelper

open class MainBaseViewModel : BaseViewModel() {
        var serviceApi: RequestService? = null
    init {
         serviceApi = HttpHelper.getInstance()?.create(RequestService::class.java)
    }
}