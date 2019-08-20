package com.laohu.coroutines

import com.laohu.coroutines.base.MvpPresenter
import com.laohu.coroutines.base.MvpView
import com.laohu.coroutines.pojo.Gank

class MainContract {
    interface View: MvpView {
        fun showLoadingView()
        fun showLoadingSuccessView(granks: List<Gank>)
        fun showLoadingErrorView()
    }

    interface Presenter: MvpPresenter<View> {
        fun syncWithContext()
        fun syncNoneWithContext()
        fun asyncWithContextForAwait()
        fun asyncWithContextForNoAwait()
        fun adapterCoroutineQuery()
        fun retrofitCoroutine()
    }
}
