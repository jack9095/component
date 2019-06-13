package com.kuanquan.universalcomponents.fragment

import android.os.Bundle
import android.view.View
import com.base.library.base.BaseViewModelFragment
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.viewmodel.ClassifyViewModel
import kotlinx.android.synthetic.main.fragment_classify.*

class ClassifyFragment : BaseViewModelFragment<ClassifyViewModel>() {
    override fun onClick(v: View?) {
    }

    override fun createViewModel(): ClassifyViewModel {
        return createViewModel(this,ClassifyViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_classify
    }

    override fun initView() {
        super<BaseViewModelFragment>.initView()
        top_navigation_a_f.setTvTitle("分类")
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