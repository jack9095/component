package com.kuanquan.networklibrary.test.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kuanquan.networklibrary.test.DataResult
import com.kuanquan.networklibrary.test.common.MainBaseViewModel
import com.kuanquan.networklibrary.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserViewModel: MainBaseViewModel() {

    private val TAG = this.javaClass.simpleName

    val dataLiveData : MutableLiveData<List<DataResult.DataModel>> by lazy {
        MutableLiveData<List<DataResult.DataModel>>().also {
//            loadData()
        }
    }

    fun loadData(){
        presenterScope.launch {
            val time = System.currentTimeMillis()
            requestLiveData.value = ""
            try {
                val responseDatas = retrofitSuspendQuery()
                // 解析接口返回的数据
                dataLiveData.value = responseDatas
            } catch (e: Throwable) {
                LogUtil.e(TAG, "异常：$e")
            } finally {
                loadState.value = "加载完成"
                LogUtil.e(TAG, "耗时：${System.currentTimeMillis() - time}")
            }
        }
    }

    // Retrofit协程官方支持
    suspend fun retrofitSuspendQuery(): List<DataResult.DataModel> {
        return withContext(Dispatchers.Main) {
            try {
                val androidResult = serviceApi?.getSuspendAndroid()
                val iosResult = serviceApi?.getSuspendIOS()
                mutableListOf<DataResult.DataModel>().apply {
                    iosResult?.results?.let { addAll(it) }
                    androidResult?.results?.let { addAll(it) }
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }
}