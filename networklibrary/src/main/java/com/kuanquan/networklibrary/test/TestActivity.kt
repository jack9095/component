package com.kuanquan.networklibrary.test

import com.kuanquan.networklibrary.BaseViewModelActivity
import com.kuanquan.networklibrary.R
import com.kuanquan.networklibrary.test.viewmodel.UserViewModel

class TestActivity : BaseViewModelActivity<UserViewModel>() {

    override fun providerVMClass(): Class<UserViewModel>?  = UserViewModel::class.java

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        viewModel.loadData()
    }

    override fun dataObserver() {

    }

}
