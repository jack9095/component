package com.kuanquan.universalcomponents.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.base.library.base.BaseViewModelFragment
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.adapter.MineCardTicketAdapter
import com.kuanquan.universalcomponents.adapter.MineOrderAdapter
import com.kuanquan.universalcomponents.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseViewModelFragment<MineViewModel>() {
    override fun onClick(v: View?) {

    }

    override fun createViewModel(): MineViewModel {
        return createViewModel(this,MineViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        super.initView()
        //  分、卡券、收藏
        card_ticket_collection.layoutManager = GridLayoutManager(getActivity(),4)
        card_ticket_collection.adapter = MineCardTicketAdapter(mViewModel.oneData())

        // 我的订单
        mine_order_state.layoutManager = GridLayoutManager(getActivity(),4)
        mine_order_state.adapter = MineOrderAdapter(mViewModel.twoData())

        // 我的服务
        mine_service_state.layoutManager = GridLayoutManager(getActivity(),4)
        mine_service_state.adapter = MineOrderAdapter(mViewModel.threeData())
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {
    }
}