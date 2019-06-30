package com.kuanquan.universalcomponents.viewmodel

import com.kuanquan.universalcomponents.bean.MineCardTicketBean
import com.kuanquan.universalcomponents.bean.MineOrderBean

class MineViewModel : MainBaseViewModel() {

    fun oneData(): List<MineCardTicketBean>{
        val lists = ArrayList<MineCardTicketBean>()
        var bean: MineCardTicketBean
        for (i in 0..3){
            bean = MineCardTicketBean()
            bean.num = "$i"
            bean.title = "东西南北"
            lists.add(bean)
        }
        return lists
    }
    fun twoData(): List<MineOrderBean>{
        val lists = ArrayList<MineOrderBean>()
        var bean: MineOrderBean
        for (i in 0..3){
            bean = MineOrderBean()
            bean.url = "$i"
            bean.title = "待付款"
            lists.add(bean)
        }
        return lists
    }

    fun threeData(): List<MineOrderBean>{
        val lists = ArrayList<MineOrderBean>()
        var bean: MineOrderBean
        for (i in 0..5){
            bean = MineOrderBean()
            bean.url = "$i"
            bean.title = "待付款"
            lists.add(bean)
        }
        return lists
    }
}