package com.laohu.coroutines

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.laohu.coroutines.model.repository.TAG
import com.laohu.coroutines.pojo.Gank
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    private val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        // 切换协程-顺序异步请求
        syncWithContextBtn.setOnClickListener {
            presenter.syncWithContext()
        }
        // 不切换协程-顺序异步请求
        syncNoneWithContext.setOnClickListener {
            presenter.syncNoneWithContext()
        }
        // 切换协程-并发异步请求
        asyncWithContextForAwait.setOnClickListener {
            presenter.asyncWithContextForAwait()
        }
        // 切换协程-并发同步请求
        asyncWithContextForNoAwait.setOnClickListener {
            presenter.asyncWithContextForNoAwait()
        }
        // Adapter适配协程请求
        adapterBtn.setOnClickListener {
            presenter.adapterCoroutineQuery()
        }
        // Retrofit协程官方支持
        retrofitBtn.setOnClickListener {
            presenter.retrofitCoroutine()
        }
        // 取消请求
        cancelBtn.setOnClickListener {
            presenter.detachView()
        }
    }



    override fun showLoadingView() {
        loadingBar.showSelf()
    }

    override fun showLoadingSuccessView(granks: List<Gank>) {
        loadingBar.hideSelf()
        textView.text = "请求结束，数据条数：${granks.size}"
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "请求结果：$granks")
    }

    override fun showLoadingErrorView() {
        loadingBar.hideSelf()
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onBackPressed() {
        finish()
    }
}

fun View.showSelf() {
    this.visibility = View.VISIBLE
}

fun View.hideSelf() {
    this.visibility = View.GONE
}
