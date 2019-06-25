package com.kuanquan.universalcomponents.bean

import java.io.Serializable

class SearchBean: Serializable {
    var itemType = 0
    lateinit var title: String
    var isDisplay = false
    lateinit var id: String
    var dataSource: List<TagBean> = ArrayList()  // TagLayout 中的数据
}