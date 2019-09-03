package com.kuanquan.home.jetpack

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.kuanquan.home.jetpack.http.base.BaseViewModel
import com.kuanquan.home.jetpack.http.databean.Data
import com.kuanquan.home.jetpack.http.repository.ArticleRepository

class ScrollingViewModel : BaseViewModel() {

    private val TAG = ScrollingViewModel::class.java.simpleName

    private val datas: MutableLiveData<List<Data>> by lazy { MutableLiveData<List<Data>>().also {
//        loadDatas()
    } }

    private val strings: MutableLiveData<String> by lazy { MutableLiveData<String>().also {
            it.value = "1"
    } }

    private val repository = ArticleRepository()

    fun getActicle(): LiveData<List<Data>> {
        return datas
    }

    private fun loadDatas() = launchUI {
        val result = repository.getDatas()
        datas.value = result.data
    }
}