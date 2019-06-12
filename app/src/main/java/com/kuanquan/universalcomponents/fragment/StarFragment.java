package com.kuanquan.universalcomponents.fragment;

import android.os.Bundle;
import android.view.View;
import com.base.library.base.BaseViewModelFragment;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.viewmodel.StarViewModel;

/**
 * 明星馆
 */
public class StarFragment extends BaseViewModelFragment<StarViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_star;
    }

    @Override
    public void initView() {
        super.initView();
        mTopNavigationLayout = view.findViewById(R.id.top_navigation_a_f);
        mTopNavigationLayout.setTvTitle("明星馆");
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
    protected StarViewModel createViewModel() {
        return createViewModel(this,StarViewModel.class);
    }

    @Override
    protected void dataObserver() {

    }

    @Override
    public void onClick(View v) {

    }
}
