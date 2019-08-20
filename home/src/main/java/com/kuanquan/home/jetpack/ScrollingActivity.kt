package com.kuanquan.home.jetpack

import android.arch.lifecycle.Observer
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.kuanquan.home.R
import com.kuanquan.home.jetpack.http.adapter.KotlinDataAdapter
import com.kuanquan.home.jetpack.http.base.BaseViewModelActivity
import com.kuanquan.home.jetpack.http.databean.Data
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.article_item.view.*
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : BaseViewModelActivity<ScrollingViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_scrolling

    override fun providerVMClass(): Class<ScrollingViewModel>? = ScrollingViewModel::class.java

    private val TAG = this.javaClass.simpleName

    private val datas = mutableListOf<Data>()

    override fun initView() {
        recycleView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        recycleView.adapter = createAdapter()
    }

    override fun initData() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        viewModel.getActicle().observe(this, Observer{
            // 获取到数据
            it?.run {
                datas.addAll(it)
                recycleView.adapter?.notifyDataSetChanged()
            }
        })
    }

    private fun createAdapter(): KotlinDataAdapter<Data> {
        return KotlinDataAdapter.Builder<Data>()
                .setData(datas)
                .setLayoutId(R.layout.article_item)
                .addBindView { itemView, itemData ->
                    itemView.tv_name.text = itemData.name
                    itemView.tv_role.text = itemData.name
                }
                .onItemClick { _, itemData ->
                    showToast("点击了 ${itemData.name}")
                }
                .create()
    }
}
