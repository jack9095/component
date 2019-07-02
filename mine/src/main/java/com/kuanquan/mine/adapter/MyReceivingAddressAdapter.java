package com.kuanquan.mine.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.library.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.kuanquan.mine.R;
import com.kuanquan.mine.bean.MyReceivingAddressBean;
import java.util.List;

public class MyReceivingAddressAdapter extends BaseQuickAdapter<MyReceivingAddressBean, BaseViewHolder> {
    private static final int ONE = 0;
    private static final int TWO = 1;
    public MyReceivingAddressAdapter(@Nullable List<MyReceivingAddressBean> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<MyReceivingAddressBean>() {
            @Override
            protected int getItemType(MyReceivingAddressBean entity) {
                //根据你的实体类来判断布局类型
                return entity.getItemType();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(ONE, R.layout.address_adapter_item_one_layout)
                .registerItemType(TWO, R.layout.addressadapter_item_two_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyReceivingAddressBean item) {
        switch (helper.getItemViewType()) {
            case ONE:
                LogUtil.e("tag", "FIRST_TYPE===============" + helper.getLayoutPosition());
                ImageView imageView = helper.getView(R.id.address_adapter_iv);
                helper.setText(R.id.address_adapter_tv,item.title);
                break;
            case TWO:
                LogUtil.e("tag", "SECOND_TYPE===============" + helper.getLayoutPosition());
                View view = helper.getView(R.id.address_adapter_view);
                TextView recentTv = helper.getView(R.id.address_adapter_recent); // 最近使用
                helper.setText(R.id.address_adapter_name,item.name);
                helper.setText(R.id.address_adapter_phone,item.phone);
                helper.setText(R.id.address_adapter_address,item.content);
                helper.setText(R.id.address_adapter_tag,item.type);
                break;
        }
    }
}
