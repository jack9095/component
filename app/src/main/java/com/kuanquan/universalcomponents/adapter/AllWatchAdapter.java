package com.kuanquan.universalcomponents.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.base.library.utils.glide.invocation.ImageLoaderManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.AllWatchBean;
import java.util.List;

public class AllWatchAdapter extends BaseQuickAdapter<AllWatchBean, BaseViewHolder> {

    public AllWatchAdapter(int layoutResId, @Nullable List<AllWatchBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllWatchBean item) {
        ImageView imageView = helper.getView(R.id.right_recycler_iv);
        ImageLoaderManager.getInstance().displayImageNetUrl(mContext,item.imageUrl,R.mipmap.ic_launcher,imageView);

        helper.setText(R.id.right_title, item.title);
        helper.setText(R.id.right_send_integrals, item.des);
        helper.setText(R.id.right_one_price, "￥" + item.getPrice());
        helper.setText(R.id.right_two_price, "￥" + item.getPrice_integral());

    }
}
