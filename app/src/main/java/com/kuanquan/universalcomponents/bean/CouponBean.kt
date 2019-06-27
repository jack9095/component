package com.kuanquan.universalcomponents.bean

import java.io.Serializable

class CouponBean: Serializable {
    lateinit var id: String
    lateinit var title: String
    lateinit var tagText: String
    lateinit var type: String  // 1首单专项  2 赠分
}