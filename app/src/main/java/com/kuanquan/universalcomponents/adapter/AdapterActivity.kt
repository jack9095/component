package com.kuanquan.universalcomponents.adapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.base.library.utils.LogUtil
import com.kuanquan.universalcomponents.R

class AdapterActivity : AppCompatActivity(), KotlinAdapter.IKotlinItemClickListener {
    // 创建一个数据集合
//   private var lists: ArrayList<String>? = ArrayList()
    private var lists = ArrayList<String>()
//    private var adapter: KotlinAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter)

        initData()
        initView()
    }

    private fun initView() {
        // 下面这两种写法都可以
         recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        // 设置布局
        recyclerView!!.layoutManager = linearLayoutManager
        // 设置动画
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        val adapter = KotlinAdapter()
        recyclerView!!.adapter = adapter
        adapter.setIKotlinItemClickListener(this)
        adapter.setData(lists)
    }

    private fun initData(){
        for (i in 1..20){
//            lists.add(i.toString())
            // 这种写法表示如果为空可以抛出空指针异常
            lists!!?.add("我是条目" + i.toString())
        }
    }

    override fun onItemClickListener(position: Int) {
        LogUtil.e("AdapterActivity","adapter的点击事件")
    }
}
