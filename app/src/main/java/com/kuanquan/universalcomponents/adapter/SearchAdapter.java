package com.kuanquan.universalcomponents.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.base.library.utils.LogUtil;
import com.base.library.widget.OnTagClickListener;
import com.base.library.widget.TagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.SearchBean;
import com.kuanquan.universalcomponents.bean.TagBean;

import java.util.List;

/**
 * 搜索列表适配器  (多布局)
 */
public class SearchAdapter extends BaseQuickAdapter<SearchBean, BaseViewHolder> {
    private static final int ONE = 0;
    private static final int TWO = 1;

    public SearchAdapter(List<SearchBean> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<SearchBean>() {
            @Override
            protected int getItemType(SearchBean entity) {
                //根据你的实体类来判断布局类型
                return entity.getItemType();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(ONE, R.layout.search_adapter_item_one_layout)
                .registerItemType(TWO, R.layout.search_adapter_item_two_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, SearchBean item) {
        switch (helper.getItemViewType()) {
            case ONE:
                LogUtil.e("tag", "FIRST_TYPE===============" + helper.getLayoutPosition());
                helper.setText(R.id.search_tv,item.title);
                TextView view = helper.getView(R.id.empty_historical_records);
                if (item.isDisplay()) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                break;
            case TWO:
                LogUtil.e("tag", "SECOND_TYPE===============" + helper.getLayoutPosition());
                TagLayout mTagLayout = helper.getView(R.id.flow_layout_finance);
                TagAdapter<TagBean>  mFinanceTagAdapter  = new TagAdapter<>(mContext);
                mTagLayout.setAdapter(mFinanceTagAdapter);

                mFinanceTagAdapter.onlyAddAll(item.getDataSource());

                mTagLayout.setOnTagClickListener(new OnTagClickListener() {
                    @Override
                    public void onItemClick(TagLayout parent, View view, int position) {
                        TagBean item = (TagBean) parent.getAdapter().getItem(position);
//                        Intent intent = new Intent(mContext, GoodListActivity.class);
//                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }
}
