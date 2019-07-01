package com.kuanquan.universalcomponents.slide

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.adapter.SlideAdapter
import kotlinx.android.synthetic.main.slide_activity.*

class SlideActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide_activity)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = SlideAdapter()
    }

    fun start(context: Context) {
        val intent = Intent(context, SlideActivity::class.java)
        context.startActivity(intent)
    }
}