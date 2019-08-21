package com.kuanquan.networklibrary.http

import java.io.Serializable

/**
 * 返回结果封装
 */
class BaseResponse<T>: Serializable {
    var code: Int = 0 // 返回的code   0 成功
    var data: T? = null // 具体的数据结果
    var msg: String? = null // message 可用来返回接口的说明
}