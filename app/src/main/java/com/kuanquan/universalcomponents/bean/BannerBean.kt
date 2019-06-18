package com.kuanquan.universalcomponents.bean

import java.io.Serializable

/**
 * 非空属性必须在定义的时候初始化,kotlin提供了一种可以延迟初始化的方案,使用 lateinit 关键字描述属性：
 */
open class BannerBean : Serializable {
    lateinit var title: String
    lateinit var url: String
//    var url: String = ""
    var id: String = ""  // 默认实现 get 和 set 方法
}