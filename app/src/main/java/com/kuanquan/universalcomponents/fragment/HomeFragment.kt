package com.kuanquan.universalcomponents.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.base.library.base.BaseViewModelFragment
import com.base.library.utils.LogUtil
import com.base.library.widget.TopNavigationLayout
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

    val TAG: String = HomeFragment::class.java.simpleName
    var titlebar: FrameLayout? = null
    var titleRl: RelativeLayout? = null
    var recyclerView: RecyclerView? = null
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
        titlebar = view.findViewById<FrameLayout>(R.id.title_bar)
//        mTopNavigationLayout = view.findViewById<TopNavigationLayout>(R.id.top_navigation_a_f)
//        mTopNavigationLayout.setTvTitle("首页")
//        mTopNavigationLayout.setHintLeftTextView(true)
        setSystemBarAlpha(0)

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
//            initData(null)
            LogUtil.e(TAG, "lists  ->  ${lists.size}")
            for (i in 1..500) {
                // 这种写法表示如果为空可以抛出空指针异常
                lists.add("我是条目$i")
            }
            adapter?.setData(lists)
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
//        KotlinClassConstructor(9)
        for (i in 1..500) {
            // 这种写法表示如果为空可以抛出空指针异常
            lists.add("我是条目$i")
        }

        //滑动监听事件
        recyclerView?.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            //dy:每一次竖直滑动增量 向下为正 向上为负
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                mDistance += dy
//                var percent = mDistance * 1f / maxDistance //百分比
//                var alpha = (percent * 255).toInt()
////                var argb = Color.argb(alpha, 57, 174, 255)
//                setSystemBarAlpha(alpha)

                // 第二种方法
//                LogUtil.e("zhu getScollYDistance():" + getScollYDistance())
//                if (getScollYDistance() <= 0) {
//                    titlebar?.setBackgroundColor(Color.argb(0, 255,41,76))
////                    titlebar?.background?.alpha = 0
////                    tvTitle.setVisibility(View.GONE);
//                } else if(getScollYDistance() in 1..400){
//                    val scale =  getScollYDistance() / 400
//                    val alpha = (255 * scale)
////                    titlebar?.background?.alpha = alpha
//                    titlebar?.setBackgroundColor(Color.argb(alpha, 255,255,255))
//                } else {
////                    titlebar?.background?.alpha = 255
//                    titlebar?.setBackgroundColor(Color.argb(255, 255,255,255))
////                    tvTitle.setVisibility(View.VISIBLE);
//                }

                // 第三种方法
                overallXScroll += dy// 累加y值 解决滑动一半y值为0
                if (overallXScroll <= 0) {   //设置标题的背景颜色
                    titlebar?.background?.alpha = 0
//                    titlebar?.setBackgroundColor(Color.argb(0, 41, 193, 246))
                } else if (overallXScroll in 1..height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    val scale = overallXScroll.toFloat() / height
                    val alpha = 255 * scale
                    titlebar?.background?.alpha = alpha.toInt()
//                    titlebar?.setBackgroundColor(Color.argb(alpha.toInt(), 41, 193, 246))
                } else {
                    titlebar?.background?.alpha = 255
//                    titlebar?.setBackgroundColor(Color.argb(255, 41, 193, 246))
                }


                if (overallXScroll > 800) {
                    titleRl?.visibility = View.VISIBLE
                } else {
                    titleRl?.visibility = View.GONE
                }
            }
        })
    }

    /**
     * 用来整体滑动的距离
     * @return
     */
    fun getScollYDistance(): Int {
        val position: Int? = linearLayoutManager?.findFirstVisibleItemPosition()
        val firstVisiableChildView = linearLayoutManager?.findViewByPosition(position!!)
        val itemHeight: Int? = firstVisiableChildView?.height
        return position!! * itemHeight!! - firstVisiableChildView.top
    }


    var mDistance: Int = 0
//    var maxDistance: Int = 255 //当距离在[0,255]变化时，透明度在[0,255之间变化]
    var maxDistance: Int = 800 //当距离在[0,255]变化时，透明度在[0,255之间变化]

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun dataObserver() {

    }

//    var alpha: Int = 0
    /**
     * 设置标题栏背景透明度
     * @param alpha 透明度  0 全透明
     */
    fun setSystemBarAlpha(alpha: Int) {
        var alphaValue: Int = 0
        if (Math.abs(alpha) > 255) {
            alphaValue = 255
        } else if (Math.abs(alpha) > 0) {
            alphaValue = alpha
        } else {
//            alphaValue = 0
            alphaValue = alpha
        }

        //标题栏渐变。a:alpha透明度 r:红 g：绿 b蓝
//        titlebar.setBackgroundColor(Color.rgb(57, 174, 255));//没有透明效果
//        titlebar.setBackgroundColor(Color.argb(alpha, 57, 174, 255));//透明效果是由参数1决定的，透明范围[0,255]
        LogUtil.e("透明度值 alpha = ", alphaValue)
        titlebar?.getBackground()?.setAlpha(alphaValue)
//            titlebar?.background?.alpha = alphaValue
    }
}