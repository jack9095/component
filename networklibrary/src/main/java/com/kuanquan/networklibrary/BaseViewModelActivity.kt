package com.kuanquan.networklibrary

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders

abstract class BaseViewModelActivity<VM : BaseViewModel>: BaseActivity() {
    protected lateinit var viewModel: VM

    override fun initView() {
        providerVMClass()?.let { viewModelClass ->
            viewModel = ViewModelProviders.of(this).get(viewModelClass)
            viewModel.loadState.observe(this, Observer { state ->
                state?.let {
                    showToast(it)
                }
            })
            dataObserver()
            lifecycle.addObserver(viewModel)
        }

    }

    open fun providerVMClass(): Class<VM>? = null

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

//    val observer = Observer<String> {
//        it?.let {
//            showToast(it)
//        }
//    }
}