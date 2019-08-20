package com.kuanquan.home.coroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kuanquan.home.R
import com.kuanquan.home.coroutine.bean.Gank
import com.kuanquan.home.coroutine.repository.Repository
import com.kuanquan.home.coroutine.repository.TAGF
import kotlinx.android.synthetic.main.activity_main_home.*
import kotlinx.coroutines.*

class HomeCoroutineActivity : AppCompatActivity() {

    // 懒加载
    val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)
        // 切换协程-顺序异步请求
        syncWithContextBtn.setOnClickListener {
            presenterScope.launch {
                val time = System.currentTimeMillis()
                showLoadingView()
                try {
                    val ganks = Repository.querySyncWithContext()
                    showLoadingSuccessView(ganks)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    showLoadingErrorView()
                } finally {
                    Log.d(TAGF, "耗时：${System.currentTimeMillis() - time}")
                }
            }
        }
        // 不切换协程-顺序异步请求
        syncNoneWithContext.setOnClickListener {
            presenterScope.launch {
                val time = System.currentTimeMillis()
                showLoadingView()
                try {
                    val ganks = Repository.querySyncNoneWithContext()
                    showLoadingSuccessView(ganks)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    showLoadingErrorView()
                } finally {
                    Log.d(TAGF, "耗时：${System.currentTimeMillis() - time}")
                }
            }
        }
        // 切换协程-并发异步请求
        asyncWithContextForAwait.setOnClickListener {
            presenterScope.launch {
                val time = System.currentTimeMillis()
                showLoadingView()
                try {
                    val ganks = Repository.queryAsyncWithContextForAwait()
                    showLoadingSuccessView(ganks)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    Log.d(TAGF, "error: ${e.message}")
                    showLoadingErrorView()
                } finally {
                    Log.d(TAGF, "耗时：${System.currentTimeMillis() - time}")
                }
            }
        }
        // 切换协程-并发同步请求
        asyncWithContextForNoAwait.setOnClickListener {
            presenterScope.launch {
                val time = System.currentTimeMillis()
                showLoadingView()
                try {
                    val ganks = Repository.queryAsyncWithContextForNoAwait()
                    showLoadingSuccessView(ganks)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    showLoadingErrorView()
                } finally {
                    Log.d(TAGF, "耗时：${System.currentTimeMillis() - time}")
                }
            }
        }
        // Adapter适配协程请求
        adapterBtn.setOnClickListener {
            presenterScope.launch {
                val time = System.currentTimeMillis()
                showLoadingView()
                try {
                    val ganks = Repository.adapterCoroutineQuery()
                    showLoadingSuccessView(ganks)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    showLoadingErrorView()
                } finally {
                    Log.d(TAGF, "耗时：${System.currentTimeMillis() - time}")
                }
            }
        }
        // Retrofit协程官方支持
        retrofitBtn.setOnClickListener {
            presenterScope.launch {
                val time = System.currentTimeMillis()
                showLoadingView()
                try {
                    val ganks = Repository.retrofitSuspendQuery()
                    showLoadingSuccessView(ganks)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    showLoadingErrorView()
                } finally {
                    Log.d(TAGF, "耗时：${System.currentTimeMillis() - time}")
                }
            }
        }
        // 取消请求
        cancelBtn.setOnClickListener {
//            presenterScope.cancel()
        }
    }

    fun showLoadingView() {
        loadingBar.showSelf()
    }

    fun showLoadingSuccessView(granks: List<Gank>) {
        textView.text = "请求结束，数据条数：${granks.size}"
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show()
        loadingBar.hideSelf()
        Log.d(TAGF, "请求结果：$granks")
    }

    fun showLoadingErrorView() {
        loadingBar.hideSelf()
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterScope.cancel()
    }
}

fun View.showSelf() {
    this.visibility = View.VISIBLE
}

fun View.hideSelf() {
    this.visibility = View.GONE
}