package com.kuanquan.universalcomponents.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.base.library.utils.ToastUtils;
import com.base.library.widget.BannerIndicatorView;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.AllWatchBean;
import com.kuanquan.universalcomponents.bean.BannerBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 大家读喜欢
 */
public class LayoutViewPagerHot extends FrameLayout implements ViewPagerIndicator.OnPageClickListener {

    ViewPagerIndicator mAppBanner;
    BannerIndicatorView mBannerIndicatorView;
    List<BannerBean> datas = new ArrayList<>();

    public LayoutViewPagerHot(@NonNull Context context) {
        super(context);
        initView();
    }

    public LayoutViewPagerHot(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LayoutViewPagerHot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LayoutViewPagerHot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.layout_view_pager_hot, this, true);
        mAppBanner = root.findViewById(R.id.app_banner);

       mBannerIndicatorView = root.findViewById(R.id.indicator_view);
    }

    public void setData(List<BannerBean> lists) {
        if (lists != null && lists.size() > 0) {
            datas.clear();
            datas.addAll(lists);
            mAppBanner.setData(lists, this);

            mBannerIndicatorView.bindWithViewPager(mAppBanner,lists.size());
            mBannerIndicatorView.setCurrentPosition(0);
        }
    }

    @Override
    public void onPageClick(AllWatchBean info) {
        ToastUtils.showMessage(getContext(), "点击事件");
//        Intent intent = new Intent();
//        intent.setClass(getContext(), HomeModelActivity.class);
//        getContext().startActivity(intent);
    }

//    @Override
//    public void onPageSelected(int position) {
//
//    }
}
