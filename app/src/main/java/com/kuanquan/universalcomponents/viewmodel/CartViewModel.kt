package com.kuanquan.universalcomponents.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.base.library.base.constant.StateConstants
import com.base.library.base.network.schedulers.SchedulerProvider
import com.kuanquan.universalcomponents.kotlinTest.UserBean

class CartViewModel : MainBaseViewModel() {
    var liberate: MutableLiveData<UserBean> = MutableLiveData()

    fun requestData(){
        val baseResponseObservable = serviceApi?.postInfo()
        addDisposable(
            baseResponseObservable
//                ?.subscribeOn(Schedulers.io())
//                ?.observeOn(AndroidSchedulers.mainThread())
                ?.compose(SchedulerProvider.getInstance().applySchedulers())
                ?.subscribe({ bean ->
                    if (bean != null && bean.code == 0) {
                        liberate.value = bean.data
                        loadState.value = StateConstants.SUCCESS_STATE
                    }
                }, { loadState.setValue(StateConstants.ERROR_STATE) })
        )
    }
}