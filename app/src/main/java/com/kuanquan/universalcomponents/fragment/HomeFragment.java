package com.kuanquan.universalcomponents.fragment;

import android.os.Bundle;
import android.view.View;
import com.base.library.base.BaseViewModelFragment;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.viewmodel.HomeViewModel;

/**
 * 首页
 */
public class HomeFragment extends BaseViewModelFragment<HomeViewModel> {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        super.initView();
        mTopNavigationLayout = view.findViewById(R.id.top_navigation_a_f);
        mTopNavigationLayout.setTvTitle("首页");
        mTopNavigationLayout.setHintLeftTextView(true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected HomeViewModel createViewModel() {
        return createViewModel(this,HomeViewModel.class);
    }

    @Override
    protected void dataObserver() {

    }

    @Override
    public void onClick(View v) {

    }
}
