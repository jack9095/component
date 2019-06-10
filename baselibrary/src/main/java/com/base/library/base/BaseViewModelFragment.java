package com.base.library.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.base.library.base.constant.StateConstants;
import com.base.library.base.viewmodel.BaseViewModel;
import com.base.library.widget.TopNavigationLayout;

public abstract class BaseViewModelFragment<T extends BaseViewModel> extends BaseFragment {

    protected T mViewModel;
    protected TopNavigationLayout mTopNavigationLayout;

    @Override
    public void initView() {
        mViewModel = createViewModel();
        if (null != mViewModel) {
            mViewModel.loadState.observe(this, observer);
            dataObserver();
        }
    }

    /**
     * 创建 自定义的 ViewModel
     */
    protected <T extends ViewModel> T createViewModel(Fragment fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);
    }

    /**
     * 创建 自定义的 ViewModel
     */
    protected abstract T createViewModel();

    /**
     *  LiveData 观察者回调实现的方法 (一般网络回调等等)
     */
    protected abstract void dataObserver();

    // lifecycle 中 liveData的监听者
    protected Observer<String> observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            dismissProgressDialog();
            if (!TextUtils.isEmpty(state)) {
                if (StateConstants.ERROR_STATE.equals(state)) {
                    showToast("加载错误");
                } else if (StateConstants.NET_WORK_STATE.equals(state)) {
                    showToast("网络不好，请稍后重试");
                } else if (StateConstants.LOADING_STATE.equals(state)) {
                    showToast("加载中");
                } else if (StateConstants.SUCCESS_STATE.equals(state)) {
//                    showToast("加载成功");
                } else {
                    showToast(state);
                }
            }
        }
    };
}
