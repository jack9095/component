package com.kuanquan.universalcomponents.fragment;

import android.os.Bundle;
import android.view.View;
import com.base.library.base.BaseViewModelFragment;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.viewmodel.MineViewModel;

/**
 * 我的页面
 */
public class MineFragment extends BaseViewModelFragment<MineViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        super.initView();
        mTopNavigationLayout = view.findViewById(R.id.top_navigation_a_f);
        mTopNavigationLayout.setTvTitle("我的");
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
    protected MineViewModel createViewModel() {
        return createViewModel(this,MineViewModel.class);
    }

    @Override
    protected void dataObserver() {

    }

    @Override
    public void onClick(View v) {

    }
}
