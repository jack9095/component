package com.kuanquan.home.coroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.base.library.utils.LogUtil
import com.kuanquan.home.R
import com.kuanquan.home.coroutine.bean.DataModel
import com.kuanquan.home.coroutine.repository.Repository
import com.kuanquan.home.coroutine.repository.TAGF
import kotlinx.android.synthetic.main.activity_main_home.*
import kotlinx.coroutines.*

/**
 * 协程，是线程中的，也就是说一个线程中可能包含多个协程，协程与协程之间是可以嵌套的
 */
class HomeCoroutineActivity : AppCompatActivity() {

    lateinit var job: Job

    // 懒加载
    /*
    TODO
    通过创建一个Job的实例来控制我们协程的生命周期，把它绑定到activity的生命周期上。
    在activity被创建时，使用工厂方法Job()创建一个job实例;当activity被销毁时，它如下这样被取消： job.cancel()
    https://www.jianshu.com/p/40bbea76489f

    Dispatchers 调度器，决定协程在哪个线程上执行
     */
    val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + job)
    }

    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        super.onDestroy()
        presenterScope.cancel()
        job.cancel() // Cancel job on activity destroy. After destroy all children jobs will be cancelled automatically
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)
        job = Job()
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
                    LogUtil.e(TAGF, "耗时：${System.currentTimeMillis() - time}")
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
                    LogUtil.e(TAGF, "耗时：${System.currentTimeMillis() - time}")
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
                    LogUtil.e(TAGF, "耗时：${System.currentTimeMillis() - time}")
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
                    LogUtil.e(TAGF, "耗时：${System.currentTimeMillis() - time}")
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
                    LogUtil.e(TAGF, "耗时：${System.currentTimeMillis() - time}")
                }
            }
        }
        // Retrofit协程官方支持
        retrofitBtn.setOnClickListener {
            presenterScope.launch {
                val time = System.currentTimeMillis()
                showLoadingView()
//                delay(6000)
                try {
                    val ganks = Repository.retrofitSuspendQuery()
                    showLoadingSuccessView(ganks)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    showLoadingErrorView()
                } finally {
                    LogUtil.e(TAGF, "耗时：${System.currentTimeMillis() - time}")
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

    fun showLoadingSuccessView(granks: List<DataModel>) {
        textView.text = "请求结束，数据条数：${granks.size}"
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show()
        loadingBar.hideSelf()
        Log.d(TAGF, "请求结果：$granks")
    }

    fun showLoadingErrorView() {
        loadingBar.hideSelf()
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
    }

}

fun View.showSelf() {
    this.visibility = View.VISIBLE
}

fun View.hideSelf() {
    this.visibility = View.GONE
}