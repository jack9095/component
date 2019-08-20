package com.kuanquan.home.jetpack.http.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 2019/5/31
 * 显示修饰符  open
 * invoke()   https://blog.csdn.net/mlsnatalie/article/details/88557502
 */
open class BaseRepository {

    // 被 suspend 修饰的挂起函数
    suspend fun <T : Any> request(call: suspend () -> ResponseData<T>): ResponseData<T> {
        // withContext{}不会创建新的协程，在指定协程上运行挂起代码块，并挂起该协程直至代码块运行完成,最后一行为返回值，或者有 return
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.apply {
            // 这儿可以对返回结果 errorCode 做一些特殊处理，比如 token 失效等，可以通过抛出异常的方式实现
            // 例：当 token 失效时，后台返回 errorCode 为 100，下面代码实现,再到 baseActivity 通过观察 error 来处理
            when (errorCode) {
                100 -> throw TokenInvalidException()
            }
        }
    }

    class TokenInvalidException(msg: String = "token invalid") : Exception(msg)
}