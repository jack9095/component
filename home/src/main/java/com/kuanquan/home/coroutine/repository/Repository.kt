package com.kuanquan.home.coroutine.repository

import com.base.library.utils.LogUtil
import com.kuanquan.home.coroutine.ApiSource
import com.kuanquan.home.coroutine.await
import com.kuanquan.home.coroutine.bean.Gank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

const val TAGF = "TestCoroutine"   // 可见性为public final static，可以直接访问, 定义常量应该加 const 修饰
object Repository {

    /**
     * 两个请求在子线程中顺序执行，非同时并发
     * 切换协程-顺序异步请求
     */
    suspend fun querySyncWithContext(): List<Gank> {
        return withContext(Dispatchers.Main) {
            try {
                // 执行网络请求
                val androidResult = ApiSource.instance.getAndroidGank().await()

                val iosResult = ApiSource.instance.getIOSGank().await()

                val result = mutableListOf<Gank>().apply {
                    addAll(iosResult.results)
                    addAll(androidResult.results)
                }
                result
            } catch (e: Throwable) {
                e.printStackTrace()
                throw e
            }
        }
    }

    /**
     * 两个请求在主线程中顺序执行，非同时并发
     * 不切换协程-顺序异步请求
     */
    suspend fun querySyncNoneWithContext(): List<Gank> {
        return try {
            val androidResult = ApiSource.instance.getAndroidGank().await()

            val iosResult = ApiSource.instance.getIOSGank().await()

            val result = mutableListOf<Gank>().apply {
                addAll(iosResult.results)
                addAll(androidResult.results)
            }
            result
        } catch (e: Throwable) {
            e.printStackTrace()
            throw e
        }
    }

    /**
     * 两个请求在子线程中并发执行
     * 切换协程-并发异步请求
     */
    suspend fun queryAsyncWithContextForAwait(): List<Gank> {
        return withContext(Dispatchers.Main) {
            try {
                val androidDeferred = async {
                    val androidResult = ApiSource.instance.getAndroidGank().await()
                    androidResult
                }

                val iosDeferred = async {
                    val iosResult = ApiSource.instance.getIOSGank().await()
                    iosResult
                }

                val androidResult = androidDeferred.await().results
                val iosResult = iosDeferred.await().results

                val result = mutableListOf<Gank>().apply {
                    addAll(iosResult)
                    addAll(androidResult)
                }
                result
            } catch (e: Throwable) {
                e.printStackTrace()
                throw e
            }
        }
    }

    /**
     * 两个请求在子线程中并发执行
     * 切换协程-并发同步请求
     */
    suspend fun queryAsyncWithContextForNoAwait(): List<Gank> {
        return withContext(Dispatchers.IO) {
            try {
                val androidDeferred = async {
                    val androidResult = ApiSource.instance.getAndroidGank().execute()
                    if(androidResult.isSuccessful) {
                        androidResult.body()!!
                    } else {
                        throw Throwable("android request failure")
                    }
                }

                val iosDeferred = async {
                    val iosResult = ApiSource.instance.getIOSGank().execute()
                    if(iosResult.isSuccessful) {
                        iosResult.body()!!
                    } else {
                        throw Throwable("ios request failure")
                    }
                }

                val androidResult = androidDeferred.await().results
                val iosResult = iosDeferred.await().results

                val result = mutableListOf<Gank>().apply {
                    addAll(iosResult)
                    addAll(androidResult)
                }
                result
            } catch (e: Throwable) {
                e.printStackTrace()
                throw e
            }
        }
    }

    // Adapter适配协程请求
    suspend fun adapterCoroutineQuery(): List<Gank> {
        return withContext(Dispatchers.Main) {
            try {
                val androidDeferred = ApiSource.callAdapterInstance.getAndroidGank()

                val iosDeferred = ApiSource.callAdapterInstance.getIOSGank()

                val androidResult = androidDeferred.await().results

                val iosResult = iosDeferred.await().results

                val result = mutableListOf<Gank>().apply {
                    addAll(iosResult)
                    addAll(androidResult)
                }
                result
            } catch (e: Throwable) {
                e.printStackTrace()
                throw e
            }
        }
    }

    // Retrofit协程官方支持
    suspend fun retrofitSuspendQuery(): List<Gank> {
        return withContext(Dispatchers.Main) {
            try {
                val androidResult = ApiSource.instance.getSuspendAndroidGank()
                val iosResult = ApiSource.instance.getSuspendIOSGank()
                mutableListOf<Gank>().apply {
                    addAll(iosResult.results)
                    addAll(androidResult.results)
                }
            } catch (e: Throwable) {
                LogUtil.e("Retrofit协程官方支持异常 = ",e)
                throw e
            }
        }
    }
}