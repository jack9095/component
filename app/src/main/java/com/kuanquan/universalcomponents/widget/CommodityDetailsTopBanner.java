package com.kuanquan.universalcomponents.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import com.base.library.utils.CollectionsUtil;
import com.base.library.utils.LogUtil;
import com.base.library.utils.glide.invocation.ImageLoaderManager;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.BannerBean;

import java.util.List;

/**
 * 商品详情顶部 banner
 */
public class CommodityDetailsTopBanner extends ViewPager {

    private MyAdapter mAdapter;
    private Handler mHandler;
    private Runnable mRunnable;
    private int time = 10000;
    private Rect mRect;
    private boolean isFmVisiable = true;
    private boolean isBanner = true;  // true 默认banner 否则就是引导页


    public CommodityDetailsTopBanner(Context context) {
        super(context);
        init();
    }

    public CommodityDetailsTopBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void isBanner(boolean blean){
        isBanner = blean;
    }

    public void setData(List<BannerBean> bannerList, OnPageClickListener clickListener) {
        if (mAdapter == null || isDataChanged(mAdapter.getData(), bannerList)) {
            mAdapter = new MyAdapter(getContext(), bannerList);
            onPageClickListener = clickListener;
            setAdapter(mAdapter);
            if (isBanner) {
                setCurrentItem(bannerList.size() * 10000);
            }
        }
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(mRunnable, time);
    }


    public List getData() {
        if (mAdapter != null) {
            return mAdapter.getData();
        }
        return null;
    }

    /**
     * 判断banner页数据是否有变化
     *
     * @param old
     * @param newData
     * @return
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
                    if (i < newData.size()) {
                        BannerBean newInfo = newData.get(i);
                        if (!oldInfo.equals(newInfo)) {
                            return true;
                        }
                    } else {
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
        mHandler = new Handler(Looper.getMainLooper());
//        setOffscreenPageLimit(3);
//        setPageMargin(-75);
//        setPageTransformer(true, new TranslatePageTransformer());

        mRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = getCurrentItem();//返回了当前界面的索引
                //跳转到下一页
                if (currentItem == mAdapter.getCount() - 1) {
                    setCurrentItem(0);
                } else {
                    setCurrentItem(currentItem + 1);
                }
                if (mHandler != null) {
                    mHandler.postDelayed(mRunnable, time);
                }
            }
        };

        mRect = new Rect();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if (onPageClickListener != null) {

                    onPageClickListener.onPageSelected(position);
                }
                BannerBean bannersBean = mAdapter.getItem(position);
//                if (bannersBean != null && !TextUtils.isEmpty(bannersBean.h5Url) && isFmVisiable) {
//                    getLocalVisibleRect(mRect);
//                    if (mRect.top >= 0) {
//                        BannerUtil.getInstance().saveShowCount(bannersBean.videoName,bannersBean.h5Url);
//                    }
//                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN://手指按下
//                        //暂停轮播
//                        mHandler.removeCallbacksAndMessages(null);
//                        break;
//                    case MotionEvent.ACTION_UP://手指抬起
//                        //继续轮播
//                        mHandler.postDelayed(mRunnable, 5000);
//                        break;
//                }
//                return false;
//            }
//        });
    }

    public void setFmVisiable(boolean isVisiable) {
        this.isFmVisiable = isVisiable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP://手指抬起
                //继续轮播
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(mRunnable, time);
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://手指按下
                //暂停轮播
                mHandler.removeCallbacksAndMessages(null);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    //暂停轮播
    public void pauseBanner(){
        mHandler.removeCallbacksAndMessages(null);
    }

    //继续轮播
    public void resumeBanner(){
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(mRunnable, time);
    }

    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, true);
    }

    public class MyAdapter extends PagerAdapter implements OnClickListener {
        private Context mContext;
        private List<BannerBean> mList;

        public MyAdapter(Context context, List<BannerBean> list) {
            mList = list;
            mContext = context;
        }


        @Override
        public int getCount() {
            if (mList == null || mList.isEmpty()) {
                return 0;
            } else {
                if (isBanner) {
                    return Integer.MAX_VALUE;
                } else {
                    return mList.size();
                }
            }
        }

        public BannerBean getItem(int position) {
            int pos = position % mList.size();
            if (pos >= 0 && pos < mList.size()) {
                BannerBean info = mList.get(pos);
                return info;
            }
            return null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % mList.size();
            if (pos >= 0 && pos < mList.size()) {
                BannerBean info = mList.get(pos);
                View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item_details_top_layout, container, false);
                view.setTag(info);
                view.setOnClickListener(this);
                ImageView banner_image = view.findViewById(R.id.banner_image);
                ImageLoaderManager.getInstance().displayImageNetUrl(getContext(),info.url,R.mipmap.ic_launcher,banner_image);
//                GlideUtil.setImageUrl(getContext(),info.imageUrl,banner_image);
                TextView banner_title = view.findViewById(R.id.text_title);
                banner_title.setFocusable(false);
                banner_title.setFocusableInTouchMode(false);
                banner_title.setEllipsize(TextUtils.TruncateAt.END);

//                banner_title.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        banner_title.setFocusable(true);
//                        banner_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//                        banner_title.setSingleLine(true);
//                        banner_title.setFocusableInTouchMode(true);
//                    }
//                },1000);
                CollectionsUtil.setTextView(banner_title,info.title);
                container.addView(view);
                return view;
            } else {
                return null;
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public List<BannerBean> getData() {
            return mList;
        }

        @Override
        public void onClick(View v) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(mRunnable, time);

            if (v.getTag() instanceof BannerBean) {
                BannerBean info = (BannerBean) v.getTag();
                if (onPageClickListener != null) {
                    onPageClickListener.onPageClick(info);
//                    if (info != null && !TextUtils.isEmpty(info.h5Url)) {
//                        BannerUtil.getInstance().saveClickCount(info.videoName,info.h5Url);
//                    }
                }
            }
        }
    }


//    /**
//     * viewPager实现中间放大,两边缩小
//     */
//
//    public class ScalePageTransformer implements PageTransformer {
//        /**
//         * 最大的item
//         */
//        public static final float MAX_SCALE = 1f;
//        /**
//         * 最小的item
//         */
//        public static final float MIN_SCALE = 0.9f;
//
//        /**
//         * 核心就是实现transformPage(View page, float position)这个方法
//         **/
//        @Override
//        public void transformPage(View page, float position) {
//
//            if (position < -1) {
//                position = -1;
//            } else if (position > 1) {
//                position = 1;
//            }
//
//            float tempScale = position < 0 ? 1 + position : 1 - position;
//
//            float slope = (MAX_SCALE - MIN_SCALE) / 1;
//            //一个公式
//            float scaleValue = MIN_SCALE + tempScale * slope;
//            page.setScaleX(scaleValue);
//            page.setScaleY(scaleValue);
//
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//                page.getParent().requestLayout();
//            }
//        }
//    }

    public OnPageClickListener onPageClickListener;
    public interface OnPageClickListener {
        void onPageClick(BannerBean info);
        void onPageSelected(int position);
    }


    /**
     * Viewpager 加入淡出淡入动画
     */
    public class TranslatePageTransformer implements PageTransformer {

        /**
         * 核心就是实现transformPage(View page, float position)这个方法
         **/
        @Override
        public void transformPage(View page, float position) {

            if (position < -1) {
                position = -1;
            } else if (position > 1) {
                position = 1;
            }

            float tempScale = position < 0 ? 1 + position : 1 - position;

            final float normalizedposition = Math.abs(Math.abs(position) - 1);
            page.setAlpha(normalizedposition);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                page.getParent().requestLayout();
            }
        }
    }

    public void setScrollSpeed(ViewPager mViewPager){
//        try {
//            Class clazz=Class.forName("android.support.v4.view.ViewPager");
//            Field f=clazz.getDeclaredField("mScroller");
//            FixedSpeedScroller fixedSpeedScroller=new FixedSpeedScroller(getContext(),new LinearOutSlowInInterpolator());
//            fixedSpeedScroller.setmDuration(500);
//            f.setAccessible(true);
//            f.set(mViewPager,fixedSpeedScroller);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 轮播滚动的时候实现平滑效果
     */
    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 500;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }

}