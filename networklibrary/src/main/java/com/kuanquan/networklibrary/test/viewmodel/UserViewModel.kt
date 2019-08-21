package com.kuanquan.networklibrary.test.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kuanquan.networklibrary.http.BaseResponse
import com.kuanquan.networklibrary.test.DataModel
import com.kuanquan.networklibrary.test.common.MainBaseViewModel
import com.kuanquan.networklibrary.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel: MainBaseViewModel() {

    private val TAG = this.javaClass.simpleName

    val dataLiveData : MutableLiveData<BaseResponse<Any>> by lazy {
        MutableLiveData<BaseResponse<Any>>().also {
//            loadData()
        }
    }

    private fun loadData(){
        presenterScope.launch {
            val time = System.currentTimeMillis()

            // 显示进度
            try {
                val responseDatas = retrofitSuspendQuery()
                // 解析接口返回的数据

            } catch (e: Throwable) {
                LogUtil.e(TAG, "异常：$e")
                // 隐藏进度
            } finally {
                LogUtil.e(TAG, "耗时：${System.currentTimeMillis() - time}")
            }
        }
    }

    // Retrofit协程官方支持
    suspend fun retrofitSuspendQuery(): List<DataModel> {
        return withContext(Dispatchers.Main) {
            try {
                val androidResult = serviceApi?.getSuspendAndroidGank()
                val iosResult = serviceApi?.getSuspendIOSGank()
                mutableListOf<DataModel>().apply {
                    iosResult?.data?.results?.let { addAll(it) }
                    androidResult?.data?.results?.let { addAll(it) }
                }
            } catch (e: Throwable) {
                LogUtil.e("Retrofit 协程官方支持异常 = ",e)
                throw e
            }
        }
    }
}