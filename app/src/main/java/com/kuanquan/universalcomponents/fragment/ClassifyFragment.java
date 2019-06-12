package com.kuanquan.universalcomponents.fragment;

import android.os.Bundle;
import android.view.View;
import com.base.library.base.BaseViewModelFragment;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.viewmodel.ClassifyViewModel;

/**
 * 分类
 */
public class ClassifyFragment extends BaseViewModelFragment<ClassifyViewModel> {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView() {
        super.initView();
        mTopNavigationLayout = view.findViewById(R.id.top_navigation_a_f);
        mTopNavigationLayout.setTvTitle("分类");
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
    protected ClassifyViewModel createViewModel() {
        return createViewModel(this,ClassifyViewModel.class);
    }

    @Override
    protected void dataObserver() {

    }

    @Override
    public void onClick(View v) {

    }
}
