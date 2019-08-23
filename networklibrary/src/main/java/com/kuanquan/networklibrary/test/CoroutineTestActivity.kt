package com.kuanquan.networklibrary.test

import android.arch.lifecycle.Observer
import com.kuanquan.networklibrary.BaseViewModelActivity
import com.kuanquan.networklibrary.R
import com.kuanquan.networklibrary.test.viewmodel.UserViewModel
import com.kuanquan.networklibrary.util.GsonUtils
import com.kuanquan.networklibrary.util.LogUtil

class CoroutineTestActivity : BaseViewModelActivity<UserViewModel>() {

    private val TAG = this.javaClass.simpleName

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
        viewModel.loadData()
        viewModel.loadData()
        viewModel.loadData()
        viewModel.loadData()
    }

    override fun dataObserver() {
        viewModel.dataLiveData.observe(this, Observer {
            LogUtil.e(TAG, "数据：" + GsonUtils.toJson(it))
        })
    }

}
