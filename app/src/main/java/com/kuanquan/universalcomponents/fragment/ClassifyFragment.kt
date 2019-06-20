package com.kuanquan.universalcomponents.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.base.library.base.BaseViewModelFragment
import com.base.library.widget.RecycleGridDivider
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.adapter.ClassifyAdapter
import com.kuanquan.universalcomponents.kotlinTest.UserBean
import com.kuanquan.universalcomponents.viewmodel.ClassifyViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_classify.*

class ClassifyFragment : BaseViewModelFragment<ClassifyViewModel>() {

    var recyclerView: RecyclerView? = null
    var refreshLayout: SmartRefreshLayout? = null
    var classifyAdapter: ClassifyAdapter? = null
    var lists = ArrayList<UserBean>()

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
        mTopNavigationLayout = view.findViewById(R.id.top_navigation_a_f)
        mTopNavigationLayout.setTvTitle("分类")
        mTopNavigationLayout.setHintLeftTextView(true)

        recyclerView = view.findViewById(R.id.common_recycler_view_classify)
        recyclerView?.layoutManager = GridLayoutManager(getActivity(),2)
//        recyclerView?.addItemDecoration(DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL))
        recyclerView?.addItemDecoration(RecycleGridDivider(10))
//        recyclerView?.addItemDecoration(GridDividerItemDecoration(getContext(), 10,true))
        classifyAdapter = ClassifyAdapter()
        recyclerView?.adapter = classifyAdapter

        refreshLayout = view.findViewById(R.id.common_refresh_Layout_classify)

        refreshLayout?.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(1000/*,false*/) // 传入false表示刷新失败
        }

        refreshLayout?.setOnLoadMoreListener { refreshlayout ->
            refreshlayout.finishLoadMore(500/*,false*/) // 传入false表示加载失败
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        for (i in 1..90){
            lists.add(UserBean())
        }
        classifyAdapter?.setData(lists)
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {

    }
}