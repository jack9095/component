package com.kuanquan.universalcomponents.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuanquan.universalcomponents.R
import android.widget.Button
import android.widget.LinearLayout
import com.kuanquan.universalcomponents.widget.SwipeMenuView



class SlideAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
       val root = LayoutInflater.from(p0.context).inflate(R.layout.item_course_learn_record,p0,false)
        return MyAdapter(root)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        if (holder is MyAdapter) {
            (holder.itemView as SwipeMenuView).setIos(false).isLeftSwipe = true
            holder.btnDelete.setOnClickListener(View.OnClickListener {

            })

            // item  点击事件
            holder.swipe_content.setOnClickListener(View.OnClickListener {

            })
        }
    }

    class MyAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipe_content = itemView.findViewById<LinearLayout>(R.id.swipe_content)
        val btnDelete = itemView.findViewById<Button>(R.id.btnDelete)
    }
}