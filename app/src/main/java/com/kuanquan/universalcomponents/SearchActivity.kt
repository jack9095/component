package com.kuanquan.universalcomponents

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.base.library.base.BaseViewModelActivity
import com.base.library.utils.KeyBoard
import com.base.library.utils.LogUtil
import com.kuanquan.universalcomponents.adapter.SearchAdapter
import com.kuanquan.universalcomponents.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseViewModelActivity<SearchViewModel>() {

    lateinit var adapter: SearchAdapter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.search_cancel -> finish()
        }
    }

    override fun createViewModel(): SearchViewModel {
        return createViewModel(this, SearchViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        super.initView()
        addOnClickListeners(this, R.id.search_cancel)
        search_recycler_view.layoutManager = LinearLayoutManager(this)

        val content = et_earch.text.toString().trimMargin()

        et_earch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {  //搜索按键 action
                    KeyBoard.hintKbTwo(this@SearchActivity,this@SearchActivity.window)
                    if (TextUtils.isEmpty(content)) {
                        return true
                    }
                    LogUtil.e("开始搜索")
                    return true
                }
                return false
            }
        })
    }

    override fun initData() {
        adapter = SearchAdapter(mViewModel.default())
        search_recycler_view.adapter = adapter
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {
    }
}