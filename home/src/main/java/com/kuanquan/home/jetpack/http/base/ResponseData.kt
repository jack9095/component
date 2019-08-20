package com.kuanquan.home.jetpack.http.base

/**
 * 2019/5/31
 * 响应结果数据
 * 数据类标记为 data
 */
data class ResponseData<out T>(val errorCode: Int, val errorMsg: String, val data: T)