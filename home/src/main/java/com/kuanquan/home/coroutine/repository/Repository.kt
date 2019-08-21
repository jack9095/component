package com.kuanquan.home.coroutine.repository

import com.base.library.utils.LogUtil
import com.kuanquan.home.coroutine.ApiSource
import com.kuanquan.home.coroutine.await
import com.kuanquan.home.coroutine.bean.DataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

// withContext{}不会创建新的协程，在指定协程上运行挂起代码块，并挂起该协程直至代码块运行完成,最后一行为返回值，或者有 return
const val TAGF = "TestCoroutine"   // 可见性为public final static，可以直接访问, 定义常量应该加 const 修饰
object Repository {

    /**
     * 两个请求在子线程中顺序执行，非同时并发
     * 切换协程-顺序异步请求
     */
    suspend fun querySyncWithContext(): List<DataModel> {
        return withContext(Dispatchers.Main) {
            try {
                // 执行网络请求
                val androidResult = ApiSource.instance.getAndroidGank().await()

                val iosResult = ApiSource.instance.getIOSGank().await()

                val result = mutableListOf<DataModel>().apply {
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
    suspend fun querySyncNoneWithContext(): List<DataModel> {
        return try {
            val androidResult = ApiSource.instance.getAndroidGank().await()

            val iosResult = ApiSource.instance.getIOSGank().await()

            val result = mutableListOf<DataModel>().apply {
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
    suspend fun queryAsyncWithContextForAwait(): List<DataModel> {
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

                val result = mutableListOf<DataModel>().apply {
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
    suspend fun queryAsyncWithContextForNoAwait(): List<DataModel> {
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

                val result = mutableListOf<DataModel>().apply {
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
    suspend fun adapterCoroutineQuery(): List<DataModel> {
        return withContext(Dispatchers.Main) {
            try {
                val androidDeferred = ApiSource.callAdapterInstance.getAndroidGank()

                val iosDeferred = ApiSource.callAdapterInstance.getIOSGank()

                val androidResult = androidDeferred.await().results

                val iosResult = iosDeferred.await().results

                val result = mutableListOf<DataModel>().apply {
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
    suspend fun retrofitSuspendQuery(): List<DataModel> {
        return withContext(Dispatchers.Main) {
            try {
                val androidResult = ApiSource.instance.getSuspendAndroidGank()
                val iosResult = ApiSource.instance.getSuspendIOSGank()
                mutableListOf<DataModel>().apply {
                    addAll(iosResult.results)
                    addAll(androidResult.results)
                }
            } catch (e: Throwable) {
                LogUtil.e("Retrofit 协程官方支持异常 = ",e)
                throw e
            }
        }
    }
}