package com.kuanquan.universalcomponents.viewmodel

import com.kuanquan.universalcomponents.bean.CouponBean

class CouponViewModel: MainBaseViewModel() {

    fun getData(): List<CouponBean>{
        val lists = ArrayList<CouponBean>()
        for (i in 0..3) {
            val bean = CouponBean()
            bean.title = "新人首单专享，已降¥14.1"
            bean.tagText = "首单专享"
            lists.add(bean)
        }
        return lists
    }

}