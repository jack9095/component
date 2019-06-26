package com.kuanquan.universalcomponents.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.base.library.utils.LogUtil;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.adapter.AllWatchAdapter;
import com.kuanquan.universalcomponents.bean.BannerBean;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * 大家都喜欢
 */
public class ViewPagerIndicator extends ViewPager {

    private MyAdapter mAdapter;

    public ViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setData(List<BannerBean> bannerList, OnPageClickListener clickListener) {
        if (mAdapter == null || isDataChanged(mAdapter.getData(), bannerList)) {
            mAdapter = new MyAdapter(getContext(), bannerList);
            onPageClickListener = clickListener;
            setAdapter(mAdapter);
        }
    }


    public List getData() {
        if (mAdapter != null) {
            return mAdapter.getData();
        }
        return null;
    }

    /**
     * 判断banner页数据是否有变化
     */
    private boolean isDataChanged(List<BannerBean> old, List<BannerBean> newData) {
        try {
            if (newData == null || newData.isEmpty()) {
                return false;
            } else if (old == null || old.isEmpty()) {
                return true;
            } else if (old.size() != newData.size()) {
                return true;
            } else {
                for (int i = 0; i < old.size(); i++) {
                    BannerBean oldInfo = old.get(i);
                    newData.size();
                    BannerBean newInfo = newData.get(i);
                    if (!oldInfo.equals(newInfo)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            LogUtil.e("crash", "isDataChanged method");
            return true;
        }
    }

    private void init() {
//        setOffscreenPageLimit(3);
//        setPageMargin(-75);
//        setPageTransformer(true, new TranslatePageTransformer());

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if (onPageClickListener != null) {
                    onPageClickListener.onPageSelected(position);
                }
//                BannerBean bannersBean = mAdapter.getItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, true);
    }

    public class MyAdapter extends PagerAdapter implements OnClickListener {
        private Context mContext;
        private List<BannerBean> mList;

        MyAdapter(Context context, List<BannerBean> list) {
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            if (mList == null || mList.isEmpty()) {
                return 0;
            } else {
                return mList.size();
            }
        }

        BannerBean getItem(int position) {
            int pos = position % mList.size();
            if (pos >= 0 && pos < mList.size()) {
                return mList.get(pos);
            }
            return null;
        }

        @Override
        public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
            return view == object;
        }

        @NotNull @Override
        public Object instantiateItem(@NotNull ViewGroup container, int position) {
            int pos = position % mList.size();
            if (pos >= 0 && pos < mList.size()) {
                BannerBean info = mList.get(pos);
                View view = LayoutInflater.from(mContext).inflate(R.layout.good_item_view_pager, container, false);
                view.setTag(info);
                view.setOnClickListener(this);

                RecyclerView recyclerView = view.findViewById(R.id.view_pager_recycler);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));

                // TODO 真实数据从外面传进来的
                AllWatchAdapter mAllWatchAdapter = new AllWatchAdapter(R.layout.recycler_pager_item_adapter,info.getDatas());
                recyclerView.setAdapter(mAllWatchAdapter);

                container.addView(view);
                return view;
            } else {
                return null;
            }
        }

        @Override
        public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
            container.removeView((View) object);
        }

        public List<BannerBean> getData() {
            return mList;
        }

        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof BannerBean) {
                BannerBean info = (BannerBean) v.getTag();
                if (onPageClickListener != null) {
                    onPageClickListener.onPageClick(info);
                }
            }
        }
    }

    public OnPageClickListener onPageClickListener;
    public interface OnPageClickListener {
        void onPageClick(BannerBean info);
        void onPageSelected(int position);
    }
}