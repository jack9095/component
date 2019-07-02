package com.kuanquan.mine

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.base.library.base.BaseViewModelActivity
import com.kuanquan.mine.adapter.MyReceivingAddressAdapter
import com.kuanquan.mine.viewmodel.MyReceivingAddressViewModel
import kotlinx.android.synthetic.main.activity_my_receiving_address.*

class MyReceivingAddressActivity : BaseViewModelActivity<MyReceivingAddressViewModel>() {

    lateinit var adapter: MyReceivingAddressAdapter

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.iv_back -> {
                finish()
           }
       }
    }

    override fun createViewModel(): MyReceivingAddressViewModel {
        return createViewModel(this,MyReceivingAddressViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_my_receiving_address
    }

    override fun initView() {
        super.initView()

        top_navigation_a_f.setTvTitle("我的收货地址")
        top_navigation_a_f.setClick(this)

        recycler_view_address.layoutManager = LinearLayoutManager(this)
        adapter = MyReceivingAddressAdapter(mViewModel.getData())
        recycler_view_address.adapter = adapter

        refresh_Layout_address.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(1000/*,false*/) // 传入false表示刷新失败
        }

        refresh_Layout_address.setOnLoadMoreListener { refreshlayout ->
            refreshlayout.finishLoadMore(500/*,false*/) // 传入false表示加载失败
        }
    }

    override fun initData() {

    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {

    }

}
