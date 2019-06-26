package com.kuanquan.universalcomponents.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.base.library.utils.glide.invocation.ImageLoaderManager;
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
        helper.setText(R.id.user_name_tv,item.name);
        helper.setText(R.id.user_content_tv,item.content);

        ImageView viewVip = helper.getView(R.id.user_vip_iv);
        ImageLoaderManager.getInstance().displayImageNetUrl(mContext,item.vip,R.mipmap.ic_launcher,viewVip);

        ImageView viewHead = helper.getView(R.id.user_head_iv);
        ImageLoaderManager.getInstance().displayImageNetUrl(mContext,item.headImage,R.mipmap.ic_launcher,viewHead);

    }
}
