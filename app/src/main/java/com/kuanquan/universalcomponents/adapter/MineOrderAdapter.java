package com.kuanquan.universalcomponents.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.MineCardTicketBean;
import com.kuanquan.universalcomponents.bean.MineOrderBean;

import java.util.List;

/**
 * 我的页面 我的订单、我的服务
 */
public class MineOrderAdapter extends BaseQuickAdapter<MineOrderBean, BaseViewHolder> {

    public MineOrderAdapter(@Nullable List<MineOrderBean> data) {
        super(R.layout.mine_adater_order_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineOrderBean item) {
        helper.setText(R.id.card_ticket_tv,item.title);
    }
}
