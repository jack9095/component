package com.kuanquan.universalcomponents.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.base.library.utils.CollectionsUtil;
import com.base.library.utils.LogUtil;
import com.base.library.utils.ToastUtils;
import com.base.library.widget.BannerIndicatorView;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情顶部 banner
 */
public class LayoutDetailsTopHot extends FrameLayout implements CommodityDetailsTopBanner.OnPageClickListener {

    CommodityDetailsTopBanner mAppBanner;
    TextView mTextView;
    List<BannerBean> datas = new ArrayList<>();

    public LayoutDetailsTopHot(@NonNull Context context) {
        super(context);
        initView();
    }

    public LayoutDetailsTopHot(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LayoutDetailsTopHot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LayoutDetailsTopHot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.layout_details_top_hot, this, true);
        mAppBanner = root.findViewById(R.id.app_banner);
        mTextView = root.findViewById(R.id.details_top_banner_tv);
    }

    public void setData(List<BannerBean> lists) {
        if (lists != null && lists.size() > 0) {
            datas.clear();
            datas.addAll(lists);
            mAppBanner.setData(lists, this);
            mAppBanner.setScrollSpeed(mAppBanner);

            mTextView.setText(Html.fromHtml("<font color= '#ffffff'><big><big>" + "1" + "</big></big></font>" + "/" + lists.size()));
        }
    }

    @Override
    public void onPageClick(BannerBean info) {
        ToastUtils.showMessage(getContext(), "点击事件");
//        Intent intent = new Intent();
//        intent.setClass(getContext(), HomeActivity.class);
//        getContext().startActivity(intent);
    }

    @Override
    public void onPageSelected(int position) {
        mTextView.setText(Html.fromHtml("<font color= '#ffffff'><big><big>" + (position + 1) + "</big></big></font>" + "/" + datas.size()));
    }
}
