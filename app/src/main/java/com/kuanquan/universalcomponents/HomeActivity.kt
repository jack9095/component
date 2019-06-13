package com.kuanquan.universalcomponents

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.base.library.base.BaseViewModelActivity
import com.base.library.utils.LogUtil
import com.kuanquan.universalcomponents.fragment.*
import com.kuanquan.universalcomponents.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseViewModelActivity<MainViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        if (bottom_navigation_view != null) {
            bottom_navigation_view.initView(this, R.id.main_frame,
                HomeFragment(), ClassifyFragment(), StarFragment(), CartFragment(), MineFragment()
            )
        } else {
            LogUtil.e("View 为空")
        }
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {

    }

    override fun onClick(v: View?) {

    }

    override fun createViewModel(): MainViewModel {
        return createViewModel(this,MainViewModel::class.java)
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt("index", bottom_navigation_view.index)
//        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedIndex = savedInstanceState?.getInt("index")
        if (savedIndex != bottom_navigation_view.index) {
            if (bottom_navigation_view.fragments[0].isAdded) {
                supportFragmentManager.beginTransaction().hide(bottom_navigation_view.fragments[0]).commit()
            }
            when (savedIndex) {
                1 -> bottom_navigation_view.disPlay(1)
                2 -> bottom_navigation_view.disPlay(2)
                3 -> bottom_navigation_view.disPlay(3)
            }
        }
    }
}
