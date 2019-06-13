package com.kuanquan.universalcomponents.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.base.library.base.BaseViewModelFragment
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.fragment_shop_cart.*

class CartFragment : BaseViewModelFragment<CartViewModel>() {
    override fun onClick(v: View?) {
    }

    override fun createViewModel(): CartViewModel {
        return createViewModel(this,CartViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_shop_cart
    }

    override fun initView() {
        super.initView()
        top_navigation_a_f.setTvTitle("卡车")
        top_navigation_a_f.setHintLeftTextView(true)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {
        mViewModel.liberate.observe(this, Observer { bean ->
            bean?.name = "2"
            bean?.code = "9"
        })
    }
}