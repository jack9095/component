package com.kuanquan.universalcomponents.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.CouponBean;
import java.util.List;

public class CouponAdapter extends BaseQuickAdapter<CouponBean, BaseViewHolder> {

    public CouponAdapter(@Nullable List<CouponBean> data) {
        super(R.layout.coupon_adater_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean item) {
        helper.setText(R.id.self_support_tv,item.tagText);
        helper.setText(R.id.title_adapter_coupon,item.title);
    }
}
