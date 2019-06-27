package com.kuanquan.universalcomponents.dialog

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.base.library.base.BaseViewModelActivity
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.adapter.CouponAdapter
import com.kuanquan.universalcomponents.viewmodel.CouponViewModel
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : BaseViewModelActivity<CouponViewModel>() {

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.coupon_finish -> finish()
        }
    }

    override fun createViewModel(): CouponViewModel {
        return createViewModel(this,CouponViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_coupon
    }

    override fun initView() {
        super.initView()
        addOnClickListeners(this,R.id.coupon_finish)
        coupon_recycler_view.layoutManager = LinearLayoutManager(this)
        coupon_recycler_view.adapter = CouponAdapter(mViewModel.getData())
    }

    override fun initData() {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {
    }

}
