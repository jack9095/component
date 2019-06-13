package com.kuanquan.universalcomponents.adapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.base.library.utils.LogUtil
import com.kuanquan.universalcomponents.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

class AdapterActivity : AppCompatActivity(), KotlinAdapter.IKotlinItemClickListener {

    val TAG: String = AdapterActivity::class.java.simpleName

    // 创建一个数据集合
//   private var lists: ArrayList<String>? = ArrayList()
    private var lists = ArrayList<String>()
//    private var adapter: KotlinAdapter? = null
    private var recyclerView: RecyclerView? = null

    var refreshLayout: SmartRefreshLayout? = null

    var adapter: KotlinAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter)

        initData()
        initView()
    }

    private fun initView() {
        // 下面这两种写法都可以
         recyclerView = findViewById<RecyclerView>(R.id.common_recycler_view)
//        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        recyclerView?.isFocusable = false
        recyclerView?.isNestedScrollingEnabled = false

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        // 设置布局
        recyclerView?.layoutManager = linearLayoutManager
        // 设置动画
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        adapter = KotlinAdapter()
        recyclerView!!.adapter = adapter
        adapter?.setIKotlinItemClickListener(this)
        adapter?.setData(lists)

        initRefreshLayout()
    }

    // 初始化刷新布局
    private fun initRefreshLayout() {
        refreshLayout = findViewById(R.id.common_refresh_Layout)

        refreshLayout?.setOnRefreshListener(OnRefreshListener {
            refreshlayout ->

            lists.clear()
            initData()
            adapter?.setData(lists)
            refreshlayout.finishRefresh(1000/*,false*/) //传入false表示刷新失败

        })

        refreshLayout?.setOnLoadMoreListener(OnLoadMoreListener { refreshlayout ->

            refreshlayout.finishLoadMore(500/*,false*/)//传入false表示加载失败
            initData()
            LogUtil.e(TAG,"lists  ->  ${lists.size}")
            adapter?.setData(lists)
        })
    }

    private fun initData(){
        for (i in 1..100){
//            lists.add(i.toString())
            // 这种写法表示如果为空可以抛出空指针异常
            lists?.add("我是条目" + i.toString())
        }
    }

    override fun onItemClickListener(position: Int) {
        LogUtil.e("AdapterActivity","adapter的点击事件")
    }
}
