package com.kuanquan.universalcomponents.fragment

import android.os.Bundle
import android.view.View
import com.base.library.base.BaseViewModelFragment
import com.base.library.widget.TopNavigationLayout
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.kotlinTest.KotlinClassConstructor
import com.kuanquan.universalcomponents.viewmodel.HomeViewModel

class HomeFragment : BaseViewModelFragment<HomeViewModel>() {

    override fun onClick(v: View?) {

    }

    override fun createViewModel(): HomeViewModel {
       return createViewModel(this,HomeViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        mTopNavigationLayout = view?.findViewById<TopNavigationLayout>(R.id.top_navigation_a_f)
        mTopNavigationLayout.setTvTitle("首页")
        mTopNavigationLayout.setHintLeftTextView(true)
    }

    override fun initData(savedInstanceState: Bundle?) {
        KotlinClassConstructor(9)
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {

    }
}