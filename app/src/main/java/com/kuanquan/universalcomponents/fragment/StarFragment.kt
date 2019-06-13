package com.kuanquan.universalcomponents.fragment

import android.os.Bundle
import android.view.View
import com.base.library.base.BaseViewModelFragment
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.viewmodel.StarViewModel
import kotlinx.android.synthetic.main.fragment_star.*

class StarFragment : BaseViewModelFragment<StarViewModel>() {
    override fun onClick(v: View?) {
    }

    override fun createViewModel(): StarViewModel {
        return createViewModel(this,StarViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_star
    }

    override fun initView() {
        super.initView()
        top_navigation_a_f.setTvTitle("行星")
        top_navigation_a_f.setHintLeftTextView(true)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {

    }
}