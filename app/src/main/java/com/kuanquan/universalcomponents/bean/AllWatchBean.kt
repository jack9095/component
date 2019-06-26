package com.kuanquan.universalcomponents.bean

import java.io.Serializable

class AllWatchBean : Serializable {

    lateinit var imageUrl: String // 图片链接
    lateinit var title: String
    lateinit var id: String
    lateinit var des: String // 买1组送50积分
    lateinit var price: String // 价格
    lateinit var price_integral: String // 返分价格
    lateinit var price_honorable: String // 尊享价格

}