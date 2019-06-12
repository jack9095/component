package com.kuanquan.universalcomponents.fragment;

import android.os.Bundle;
import android.view.View;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.library.base.BaseViewModelFragment;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.viewmodel.ShopCartViewModel;

/**
 * 购物车
 */
public class ShopCartFragment extends BaseViewModelFragment<ShopCartViewModel> {

    @Override
    public void onClick(View view) {
        ARouter.getInstance().build("/work/latestagent").navigation();
    }

    @Override
    protected ShopCartViewModel createViewModel() {
        return createViewModel(this,ShopCartViewModel.class);
    }

    @Override
    protected void dataObserver() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    public void initView() {
        super.initView();
        mTopNavigationLayout = view.findViewById(R.id.top_navigation_a_f);
        mTopNavigationLayout.setTvTitle("购物车");
        mTopNavigationLayout.setHintLeftTextView(true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
