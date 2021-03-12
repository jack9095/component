package com.kuanquan.networklibrary.test.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.kuanquan.networklibrary.http.BaseResponse
import com.kuanquan.networklibrary.test.DataResult
import com.kuanquan.networklibrary.test.common.MainBaseViewModel
import com.kuanquan.networklibrary.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
//                val androidResult = serviceApi?.getSuspendAndroid()
//                val iosResult = serviceApi?.getSuspendIOS()

                val postData = serviceApi?.postData("恢复发货", 90)
                LogUtil.e(TAG, "简洁成功 = ${postData.toString()}")

//                serviceApi?.postData("恢复发货", 90)?.enqueue(object :Callback<BaseResponse>{
//                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
//                        LogUtil.e(TAG, "异常 = $t")
//                    }
//
//                    override fun onResponse(
//                        call: Call<BaseResponse>,
//                        response: Response<BaseResponse>
//                    ) {
//                        val baseResponse = response.body() as? BaseResponse
//                        LogUtil.e(TAG, "成功 = ${response.body().toString()}")
//                    }
//
//                })
                mutableListOf<DataResult.DataModel>().apply {
//                    iosResult?.results?.let { addAll(it) }
//                    androidResult?.results?.let { addAll(it) }
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }
}