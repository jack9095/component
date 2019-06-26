package com.kuanquan.universalcomponents

import android.view.View
import com.base.library.base.BaseViewModelActivity
import com.kuanquan.universalcomponents.viewmodel.CommodityDetailsViewModel
import kotlinx.android.synthetic.main.category_list_commodity_details_activity.*

/**
 *  商品详情
 */
class CommodityDetailsActivity : BaseViewModelActivity<CommodityDetailsViewModel>() {
    override fun onClick(v: View?) {
    }

    override fun createViewModel(): CommodityDetailsViewModel {
        return createViewModel(this,CommodityDetailsViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.category_list_commodity_details_activity
    }

    override fun initView() {
        super.initView()
//        detail_scroll_view
    }

    override fun initData() {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {
    }

}
