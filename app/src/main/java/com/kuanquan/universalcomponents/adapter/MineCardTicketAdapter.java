package com.kuanquan.universalcomponents.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.MineCardTicketBean;

import java.util.List;

/**
 * 我的页面 分、卡券、收藏
 */
public class MineCardTicketAdapter extends BaseQuickAdapter<MineCardTicketBean, BaseViewHolder> {

    public MineCardTicketAdapter(@Nullable List<MineCardTicketBean> data) {
        super(R.layout.mine_adater_card_ticket_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineCardTicketBean item) {
        helper.setText(R.id.card_ticket_num_tv,item.num);
        helper.setText(R.id.card_ticket_tv,item.title);
    }
}
