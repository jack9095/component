package com.kuanquan.universalcomponents.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.base.library.base.BaseViewModelFragment
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.viewmodel.ClassifyViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_classify.*

class ClassifyFragment : BaseViewModelFragment<ClassifyViewModel>() {

    var recyclerView: RecyclerView? = null
    var refreshLayout: SmartRefreshLayout? = null

    override fun onClick(v: View?) {
    }

    override fun createViewModel(): ClassifyViewModel {
        return createViewModel(this,ClassifyViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_classify
    }

    override fun initView() {
        super.initView()
        top_navigation_a_f.setTvTitle("分类")
        top_navigation_a_f.setHintLeftTextView(true)

        recyclerView = view.findViewById(R.id.common_recycler_view)
        refreshLayout = view.findViewById(R.id.common_refresh_Layout)

        refreshLayout?.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(1000/*,false*/) //传入false表示刷新失败
        }

        refreshLayout?.setOnLoadMoreListener { refreshlayout ->
            refreshlayout.finishLoadMore(500/*,false*/)//传入false表示加载失败
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {

    }
}