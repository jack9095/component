package com.kuanquan.universalcomponents.kotlinTest.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kuanquan.universalcomponents.R

/**
 * 对象声明。总是在 object 关键字后跟一个名称。 就像变量声明一样，对象声明不是一个表达式，不能用在赋值语句的右边
 * 对象声明的初始化过程是线程安全的
 */
class KotlinAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    var lists = ArrayList<String>()
private lateinit var lists: ArrayList<String>

    fun setData(lists: ArrayList<String>){
        this.lists = lists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_adapter, parent, false)
        return MyHolder(rootView)
    }

    override fun getItemCount(): Int {
        return if(lists != null) lists.size else {0}
//        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (holder is MyHolder) {
            // 设置值
            holder.textView?.text = lists[position]

            // 点击事件
            holder.itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    iKotlinItemClickListener?.onItemClickListener(position)
                }
            })
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // !! 断言
        var textView = itemView.findViewById<TextView>(R.id.text_view)
    }

    // 对外提供自定义接口的set方法
    fun setIKotlinItemClickListener(listener : IKotlinItemClickListener) {
        this.iKotlinItemClickListener = listener
    }

    // 加❓表示可以为空
    private var iKotlinItemClickListener : IKotlinItemClickListener? = null

    // 自定义一个接口
    interface IKotlinItemClickListener {
        fun onItemClickListener(position: Int)
    }
}