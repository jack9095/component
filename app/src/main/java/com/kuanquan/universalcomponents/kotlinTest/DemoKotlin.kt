package com.kuanquan.universalcomponents.kotlinTest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.base.library.utils.LogUtil
import com.kuanquan.universalcomponents.R

/**
 * 一些常用的 kotlin 的写法
 * 在 Kotlin 中，三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小
 */
class DemoKotlin : AppCompatActivity() {

    val TAG: String = DemoKotlin::class.java.simpleName
    var list: ArrayList<String>? = null

    // 创建类的实例
    var demoClass = InstenceKotlinClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // 创建一个集合
        list = ArrayList<String>()
        list!!.add("1")
        list?.add("2")

        // If not null 缩写
        LogUtil.e(TAG,"If not null 缩写  " + list?.size)

        // If not null and else 缩写
        LogUtil.e(TAG,"If not null and else 缩写   " + (list?.size ?: "empty"))

        // if null 执行一个语句
        LogUtil.e(TAG,"if null 执行一个语句   " + (list ?: 88))

        // 在可能会为空的集合中获取第一元素
        list?.firstOrNull() ?: ""

        // if not null 执行代码
        list?.let {
            LogUtil.e(TAG,"if not null 执行代码   list不为null")
        }

        // 交换两个变量的值
        var a = 1
        var b = 2
        a = b.also { b = a}


        // 你可以通过 trimMargin() 函数去除前导空格：

        val text = "  " + "123" + "  ".trimMargin()

        // 字符串模板  它不支持反斜杠转义
        val price = """ ${'$'}9.99 """
    }
}