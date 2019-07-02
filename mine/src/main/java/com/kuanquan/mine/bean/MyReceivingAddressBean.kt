package com.kuanquan.mine.bean

import java.io.Serializable

class MyReceivingAddressBean: Serializable {
    var itemType = 0
    lateinit var id: String
    lateinit var name: String
    lateinit var phone: String
    lateinit var type: String  // 自提、公司、家
    lateinit var content: String
    var isEdit = false


    lateinit var imageUrl: String
    lateinit var title: String
}