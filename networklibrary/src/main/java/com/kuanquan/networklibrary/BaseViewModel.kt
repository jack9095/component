package com.kuanquan.networklibrary

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.*

open class BaseViewModel: ViewModel(),LifecycleObserver {

    val loadState = MutableLiveData<String>()
    val requestLiveData = MutableLiveData<String>()

    val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        presenterScope.cancel()
    }

}