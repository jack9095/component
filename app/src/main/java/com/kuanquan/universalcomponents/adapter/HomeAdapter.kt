package com.kuanquan.universalcomponents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.bean.HomeAdapterBean
import com.kuanquan.universalcomponents.widget.LayoutWorkBannerHot

/**
 * https://www.runoob.com/kotlin/kotlin-class-object.html
 * 非空属性必须在定义的时候初始化,kotlin 提供了一种可以延迟初始化的方案,使用 lateinit 关键字描述属性：
 */
class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ONE = 0
    val TWO = 1

    var lists: ArrayList<HomeAdapterBean>? = null
    var context: Context? = null

    fun setData(lists: ArrayList<HomeAdapterBean>){
        this.lists = lists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        lateinit var layoutView: View
        when (getItemViewType(position)) {
            ONE -> {
                layoutView = inflater.inflate(R.layout.adapter_item_banner, parent, false)
                return BannerClass(layoutView)
            }

            TWO -> {
                layoutView = inflater.inflate(R.layout.adapter_item_common, parent, false)
                return CommonItemClass(layoutView)
            }

            else -> {  // 默认的
                layoutView = inflater.inflate(R.layout.adapter_item_common, parent, false)
                return CommonItemClass(layoutView)
            }
        }
    }

    override fun getItemCount(): Int {
        return lists?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return lists?.get(position)?.itemType ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        if (holder is BannerClass) {
            var bannerLists = lists?.get(p1)?.bannerBeans
            holder.bannerHot.setData(bannerLists)
                if (bannerLists != null && bannerLists.size > 0) {

                }
        }
    }

    class BannerClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bannerHot = itemView.findViewById<LayoutWorkBannerHot>(R.id.home_banner)
    }

    class CommonItemClass(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}