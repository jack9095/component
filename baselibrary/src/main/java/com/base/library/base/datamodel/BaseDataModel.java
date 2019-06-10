package com.base.library.base.datamodel;


//import com.dingxin.workspace.base.network.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import com.base.library.base.network.schedulers.BaseSchedulerProvider;

/**
 * 在生命周期的某个时刻取消订阅。
 * 一个很常见的模式就是使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅
 */
public abstract class BaseDataModel {

    // 线程切换
    protected BaseSchedulerProvider schedulerProvider;

    // 可以缓解Rx内存占用不能释放的问题
    protected CompositeDisposable mCompositeDisposable;

    // 添加订阅
    protected void addDisposable(Disposable disposable) {
        initSchedulerProvider();
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    // 移除订阅
    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
//            mCompositeDisposable.dispose();
        }
    }

    // 初始化线程调度
    private void initSchedulerProvider(){
        if (schedulerProvider == null) {
//            schedulerProvider = SchedulerProvider.getInstance();
        }
    }
}
