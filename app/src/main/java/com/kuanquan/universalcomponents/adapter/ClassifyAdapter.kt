package com.kuanquan.universalcomponents.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.base.library.utils.glide.invocation.ImageLoaderManager
import com.kuanquan.universalcomponents.main.CommodityDetailsActivity
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.kotlinTest.UserBean

class ClassifyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lists: List<UserBean>? = null
    var context: Context? = null

    fun setData(lists: List<UserBean>){
        this.lists = lists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        context = parent.context
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.classify_item_layout, parent, false)
        return ClassifyitemAdapter(viewItem)
    }

    override fun getItemCount(): Int {
        return lists?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = lists?.get(position)
        if (bean == null) {

        }
        if (holder is ClassifyitemAdapter) {
//            CollectionsUtil.setTextView(holder.tv_classify_item,bean?.name)
            ImageLoaderManager.getInstance().displayImageNetUrl(context,bean?.sex,R.mipmap.ic_launcher,holder.im_classify_item)

            holder.itemView.setOnClickListener(object : View.OnClickListener{

                override fun onClick(v: View?) {
                    context?.startActivity(Intent(context, CommodityDetailsActivity::class.java))
                }
            })
        }
    }

    class ClassifyitemAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var im_classify_item = itemView.findViewById<ImageView>(R.id.im_classify_item)
        var tv_classify_item = itemView.findViewById<TextView>(R.id.tv_classify_item)
    }
}