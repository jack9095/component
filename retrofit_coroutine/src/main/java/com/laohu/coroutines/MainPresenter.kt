package com.laohu.coroutines

import android.util.Log
import com.laohu.coroutines.base.BasePresenter
import com.laohu.coroutines.model.repository.Repository
import com.laohu.coroutines.model.repository.TAG
import kotlinx.coroutines.launch

class MainPresenter: MainContract.Presenter, BasePresenter<MainContract.View>() {

    // 切换协程-顺序异步请求
    override fun syncWithContext() {
        presenterScope.launch {
            val time = System.currentTimeMillis()
            view.showLoadingView()
            try {
                val ganks = Repository.querySyncWithContext()
                view.showLoadingSuccessView(ganks)
            } catch (e: Throwable) {
                e.printStackTrace()
                view.showLoadingErrorView()
            } finally {
                Log.d(TAG, "耗时：${System.currentTimeMillis() - time}")
            }
        }
    }
    // 不切换协程-顺序异步请求
    override fun syncNoneWithContext() {
        presenterScope.launch {
            val time = System.currentTimeMillis()
            view.showLoadingView()
            try {
                val ganks = Repository.querySyncNoneWithContext()
                view.showLoadingSuccessView(ganks)
            } catch (e: Throwable) {
                e.printStackTrace()
                view.showLoadingErrorView()
            } finally {
                Log.d(TAG, "耗时：${System.currentTimeMillis() - time}")
            }
        }
    }
    // 切换协程-并发异步请求
    override fun asyncWithContextForAwait() {
        presenterScope.launch {
            val time = System.currentTimeMillis()
            view.showLoadingView()
            try {
                val ganks = Repository.queryAsyncWithContextForAwait()
                view.showLoadingSuccessView(ganks)
            } catch (e: Throwable) {
                e.printStackTrace()
                Log.d(TAG, "error: ${e.message}")
                view.showLoadingErrorView()
            } finally {
                Log.d(TAG, "耗时：${System.currentTimeMillis() - time}")
            }
        }
    }
    // 切换协程-并发同步请求
    override fun asyncWithContextForNoAwait() {
        presenterScope.launch {
            val time = System.currentTimeMillis()
            view.showLoadingView()
            try {
                val ganks = Repository.queryAsyncWithContextForNoAwait()
                view.showLoadingSuccessView(ganks)
            } catch (e: Throwable) {
                e.printStackTrace()
                view.showLoadingErrorView()
            } finally {
                Log.d(TAG, "耗时：${System.currentTimeMillis() - time}")
            }
        }
    }
    // Adapter适配协程请求
    override fun adapterCoroutineQuery() {
        presenterScope.launch {
            val time = System.currentTimeMillis()
            view.showLoadingView()
            try {
                val ganks = Repository.adapterCoroutineQuery()
                view.showLoadingSuccessView(ganks)
            } catch (e: Throwable) {
                e.printStackTrace()
                view.showLoadingErrorView()
            } finally {
                Log.d(TAG, "耗时：${System.currentTimeMillis() - time}")
            }
        }
    }
    // Retrofit协程官方支持
    override fun retrofitCoroutine() {
        presenterScope.launch {
            val time = System.currentTimeMillis()
            view.showLoadingView()
            try {
                val ganks = Repository.retrofitSuspendQuery()
                view.showLoadingSuccessView(ganks)
            } catch (e: Throwable) {
                e.printStackTrace()
                view.showLoadingErrorView()
            } finally {
                Log.d(TAG, "耗时：${System.currentTimeMillis() - time}")
            }
        }
    }
}