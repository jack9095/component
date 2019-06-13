package com.kuanquan.universalcomponents.fragment

import android.os.Bundle
import android.view.View
import com.base.library.base.BaseViewModelFragment
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_classify.*

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
        top_navigation_a_f.setHintLeftTextView(true)
        top_navigation_a_f.setTvTitle("我的")
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {
    }
}