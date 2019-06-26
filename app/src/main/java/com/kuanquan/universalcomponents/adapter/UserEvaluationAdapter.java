package com.kuanquan.universalcomponents.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.UserEvaluationBean;

import java.util.List;

/**
 * 用户评价
 */
public class UserEvaluationAdapter extends BaseQuickAdapter<UserEvaluationBean, BaseViewHolder> {

    public UserEvaluationAdapter(@Nullable List<UserEvaluationBean> data) {
        super(R.layout.user_evalu_adapter_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserEvaluationBean item) {

    }
}
