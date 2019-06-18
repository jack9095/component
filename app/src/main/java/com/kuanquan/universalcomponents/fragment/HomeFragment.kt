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
import com.kuanquan.universalcomponents.adapter.KotlinAdapter
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
    var refreshLayout: SmartRefreshLayout? = null
    var adapter: KotlinAdapter? = null
    var lists = ArrayList<String>()
    var linearLayoutManager: LinearLayoutManager? = null
    var overallXScroll: Int = 0
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
        titleBar = view.findViewById<FrameLayout>(R.id.title_bar)
        titleBar?.getBackground()?.setAlpha(0)

        titleRl = view.findViewById(R.id.rl_title)
        recyclerView = view.findViewById(R.id.common_recycler_view)
        recyclerView?.isFocusable = false
        recyclerView?.isNestedScrollingEnabled = false

        linearLayoutManager = LinearLayoutManager(getContext())
        linearLayoutManager?.orientation = LinearLayoutManager.VERTICAL
        // 设置布局
        recyclerView?.layoutManager = linearLayoutManager
        // 设置动画
        recyclerView!!.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        adapter = KotlinAdapter()
        recyclerView!!.adapter = adapter
        adapter?.setData(lists)

        initRefreshLayout()
    }

    // 初始化刷新布局
    private fun initRefreshLayout() {
        refreshLayout = view.findViewById(R.id.common_refresh_Layout)

        refreshLayout?.setOnRefreshListener(OnRefreshListener { refreshlayout ->

            lists.clear()
            for (i in 1..500) {
                // 这种写法表示如果为空可以抛出空指针异常
                lists.add("我是条目$i")
            }
            adapter?.setData(lists)
            refreshlayout.finishRefresh(1000/*,false*/) //传入false表示刷新失败

        })

        refreshLayout?.setOnLoadMoreListener(OnLoadMoreListener { refreshlayout ->

            refreshlayout.finishLoadMore(500/*,false*/)//传入false表示加载失败
            LogUtil.e(TAG, "lists  ->  ${lists.size}")
            for (i in 1..500) {
                // 这种写法表示如果为空可以抛出空指针异常
                lists.add("我是条目$i")
            }
            adapter?.setData(lists)
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        for (i in 1..500) {
            // 这种写法表示如果为空可以抛出空指针异常
            lists.add("我是条目$i")
        }

        //滑动监听事件
        recyclerView?.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            //dy:每一次竖直滑动增量 向下为正 向上为负
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 第三种方法
                overallXScroll += dy// 累加y值 解决滑动一半y值为0
                if (overallXScroll <= 0) {   //设置标题的背景颜色
                    titleBar?.background?.alpha = 0
                } else if (overallXScroll in 1..height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    val scale = overallXScroll.toFloat() / height
                    val alpha = 255 * scale
                    titleBar?.background?.alpha = alpha.toInt()
                } else {
                    titleBar?.background?.alpha = 255
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