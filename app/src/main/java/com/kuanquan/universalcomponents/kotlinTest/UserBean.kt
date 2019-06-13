package com.kuanquan.universalcomponents.kotlinTest

import java.io.Serializable

open class UserBean: Serializable {

    var name: String? = ""
    open var sex: String? = ""
    open var gender: Int = 0
    open var code: String? = null
}