package com.base.library.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.base.library.R;

/**
 * 简单封装recyclerView的刷新和加载
 */
public class SwipeRefreshLayoutRecycler extends FrameLayout {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    public SwipeRefreshLayoutRecycler(Context context) {
        super(context);
        initView();
    }

    public SwipeRefreshLayoutRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SwipeRefreshLayoutRecycler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwipeRefreshLayoutRecycler(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);  // 请求父控件不拦截子控件的触摸事件
//        return super.dispatchTouchEvent(ev);
//    }

    private void initView(){
        View root = LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_base_library_recycler, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.base_library_swipe_refresh);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.base_library_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnScrollListener(new RvScrollListener());
        mSwipeRefreshLayout.setColorSchemeResources(colors);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefreshListener();
                }
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1200);
            }
        });
    }

    private int lastVisibleItem;
    private boolean isRefresh;  // true 是刷新
    private boolean isLoad; // true 是加载到数据可以继续加载数据（网络请求）
    private class RvScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();  // 滑动到最后一个
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (mRefreshListener != null) {
                mRefreshListener.onLoadListener(newState,lastVisibleItem + 1);
            }
//            if (newState == RecyclerView.SCROLL_STATE_IDLE
//                    && lastVisibleItem + 1 == mHomeAdapter.getItemCount()) {
//                if (DataUtils.getFindData() != null && DataUtils.getFindData().size() >= 5) {
//                    if (isLoad) {
//                        mHomeAdapter.loadProgress.setVisibility(View.VISIBLE);
//                        mHomeAdapter.loadText.setVisibility(View.VISIBLE);
//                        isRefresh = false;
//                        // 网络请求
//                    }
//                }
//            }
        }
    }

    /**
     * 下拉刷新控件变化的四个颜色
     */
    int[] colors = new int[] {
            android.R.color.holo_green_light, android.R.color.holo_blue_light,
            android.R.color.holo_green_light, android.R.color.holo_blue_light
    };


    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout(){
        return mSwipeRefreshLayout;
    }

    RefreshListener mRefreshListener;
    public void setRefreshListener(RefreshListener mRefreshListener){
        this.mRefreshListener = mRefreshListener;
    }
    public interface RefreshListener{
        void onRefreshListener();
        void onLoadListener(int newState, int lastVisibleItem);
    }
}
