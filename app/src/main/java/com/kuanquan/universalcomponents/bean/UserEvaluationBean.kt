package com.kuanquan.universalcomponents.bean

import java.io.Serializable

/**
 * 用户评论
 */
class UserEvaluationBean : Serializable {

    lateinit var headImage: String
    lateinit var name: String
    lateinit var id: String
    lateinit var content:String
    lateinit var vip: String
}