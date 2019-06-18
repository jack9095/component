package com.kuanquan.universalcomponents.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.base.library.base.BaseViewModelFragment
import com.base.library.utils.LogUtil
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.adapter.HomeAdapter
import com.kuanquan.universalcomponents.bean.BannerBean
import com.kuanquan.universalcomponents.bean.HomeAdapterBean
import com.kuanquan.universalcomponents.kotlinTest.adapter.KotlinAdapter
import com.kuanquan.universalcomponents.viewmodel.HomeViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 *
 * 对象声明。总是在 object 关键字后跟一个名称。 就像变量声明一样，对象声明不是一个表达式，不能用在赋值语句的右边
 * 对象声明的初始化过程是线程安全的
 *
 * 伴生对象（内部类）
 * https://www.kotlincn.net/docs/reference/object-declarations.html#%E4%BC%B4%E7%94%9F%E5%AF%B9%E8%B1%A1
 */
class HomeFragment : BaseViewModelFragment<HomeViewModel>() {

    private val TAG: String = HomeFragment::class.java.simpleName
    var titleBar: FrameLayout? = null
    var titleRl: RelativeLayout? = null
    private var recyclerView: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null
    private var adapter: HomeAdapter? = null
    private var lists = ArrayList<HomeAdapterBean>()
    private var linearLayoutManager: LinearLayoutManager? = null
    var overallXScroll = 0
    private val height = 640 // 滑动开始变色的高,真实项目中此高度是由广告轮播或其他首页view高度决定

    override fun onClick(v: View?) {

    }

    override fun createViewModel(): HomeViewModel {
        return createViewModel(this, HomeViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        titleBar = view.findViewById(R.id.title_bar)
        titleBar?.background?.alpha = 0

        titleRl = view.findViewById(R.id.rl_title)
        recyclerView = view.findViewById(R.id.common_recycler_view)
        recyclerView?.isFocusable = false
        recyclerView?.isNestedScrollingEnabled = false

        linearLayoutManager = LinearLayoutManager(getContext())
        linearLayoutManager?.orientation = LinearLayoutManager.VERTICAL
        // 设置布局
        recyclerView?.layoutManager = linearLayoutManager
        // 设置动画
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        adapter = HomeAdapter()
        recyclerView!!.adapter = adapter

        initRefreshLayout()
    }

    // 初始化刷新布局
    private fun initRefreshLayout() {
        refreshLayout = view.findViewById(R.id.common_refresh_Layout)

        refreshLayout?.setOnRefreshListener(OnRefreshListener { refreshlayout ->

//            lists.clear()

//            adapter?.setData(lists)
            refreshlayout.finishRefresh(1000/*,false*/) //传入false表示刷新失败

        })

        refreshLayout?.setOnLoadMoreListener(OnLoadMoreListener { refreshlayout ->

            refreshlayout.finishLoadMore(500/*,false*/)//传入false表示加载失败
//            LogUtil.e(TAG, "lists  ->  ${lists.size}")

//            adapter?.setData(lists)
        })
    }

    override fun initData(savedInstanceState: Bundle?) {

        var homeAdapterBean = HomeAdapterBean()

        var bannerBean = BannerBean()
        bannerBean.url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560861148821&di=c8ecc814e3bdf21b08c7970d141b316f&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201312%2F06%2F211346rdqzode7loq7oo5o.jpg"
        homeAdapterBean.bannerBeans.add(bannerBean)
        homeAdapterBean.itemType = 0

        var bannerBean1 = BannerBean()
        bannerBean1.url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560861148820&di=f9a1c7e3d9d460a2c6beaf38153831ad&imgtype=0&src=http%3A%2F%2Ft1.mmonly.cc%2Fuploads%2Ftu%2F201612%2F47%2F109409840.jpg"
        homeAdapterBean.itemType = 0
        homeAdapterBean.bannerBeans.add(bannerBean1)

        lists.add(homeAdapterBean)

        for (i in 1..500) {
            // 这种写法表示如果为空可以抛出空指针异常
            var homeBean = HomeAdapterBean()
            homeBean.itemType = 1
            lists.add(homeBean)
        }

        adapter?.setData(lists)

        //滑动监听事件
        recyclerView?.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            //dy:每一次竖直滑动增量 向下为正 向上为负
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 第三种方法
                overallXScroll += dy// 累加y值 解决滑动一半y值为0
                when {
                    overallXScroll <= 0 -> //设置标题的背景颜色
                        titleBar?.background?.alpha = 0
                    overallXScroll in 1..height -> { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                        val scale = overallXScroll.toFloat() / height
                        val alpha = 255 * scale
                        titleBar?.background?.alpha = alpha.toInt()
                    }
                    else -> titleBar?.background?.alpha = 255
                }

                if (overallXScroll > 800) {
                    titleRl?.visibility = View.VISIBLE
                } else {
                    titleRl?.visibility = View.GONE
                }
            }
        })
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {

    }
}