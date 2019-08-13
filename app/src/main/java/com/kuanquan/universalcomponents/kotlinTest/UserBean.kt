package com.kuanquan.universalcomponents.kotlinTest

import java.io.Serializable
import java.util.*

open class UserBean: Serializable {

    var name: String? = ""
    open var sex: String? = ""
    open var gender: Int = 0
    open var code: String? = null

    // 伴生对象  静态方法
    companion object{
        fun staticFun(){
            val maps = WeakHashMap<String,Int>()
        }
    }

}