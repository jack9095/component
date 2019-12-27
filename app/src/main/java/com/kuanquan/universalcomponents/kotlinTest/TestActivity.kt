package com.kuanquan.universalcomponents.kotlinTest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.base.library.utils.LogUtil
import com.kuanquan.universalcomponents.R
import java.util.*
import kotlin.collections.HashMap

/**
 * 一些常用的 kotlin 的写法
 * 在 Kotlin 中，三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小
 * b?. 若为null 返回null b!!. 若为null 返回null exception
 */
class TestActivity : AppCompatActivity() {

    val TAG: String = TestActivity::class.java.simpleName

    // 延迟加载  只有用到时才会对控件初始化
    val str: String by lazy{
        println("computed!")
        LogUtil.e("延迟加载的数据 = ","computed")
        "890"
    }

    // 只读 list  只能读取，不能添加,删除
    val listsf = listOf<String>("a","b","c")

    // 只读 map
    val map = mapOf("a" to 1,"b" to 2,"c" to 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        LogUtil.e("延迟加载的数据 = ",str)

        val stringExtra = intent.getStringExtra("aa")
        Log.e("CoroutineTestActivity", "onCreate$stringExtra")

        // 创建一个map
        val hashMap = HashMap<String,String>()
        hashMap["aa"] = "99"

        Log.e(TAG,"hashMap and " + hashMap["aa"])

        // 创建一个 ArrayList
        var lists =  ArrayList<String>()

        lists.add("3")
        lists.add("4")


        // 类型转换
        test(lists)

        // 循环
        for (str in lists){
            Log.e("CoroutineTestActivity","str 的值是 $str")

            // 过滤 lists 集合
            val filter = lists.filter { str == "3" }
            LogUtil.e(TAG, "filter = $filter")

            if (lists.contains("3")) {
                LogUtil.e(TAG, "包含3")
            }
        }

        // 循环出下标
        for (index in lists.indices){
            Log.e("CoroutineTestActivity","index 的值是 $index  and  ${lists[index]}")
        }

        // if 判断语句
        var max = if(3 > 4) 3 else 4
        Log.e("CoroutineTestActivity","max 的值是 $max")

        // swich 语句
        when (7){
            3 -> {print(3)}
            2 -> print(32)
            else -> {
                Log.e("CoroutineTestActivity","x 不是 1 ，也不是 2")
            }
        }

        // 区间语句
        var x = 5
        var y = 10
        if (x in 1..8) {
            Log.e("CoroutineTestActivity","x在这个区间内")
        }

        Log.e("HtmlActivity", "首页" + sun(3,6))
        Log.e("HtmlActivity", "首页" + sum(3,6))
    }


    // 求和函数带返回值
    fun sun(a: Int, b: Int): Int {
        return a + b
    }

    // 上面的函数也可以写成下面的表达式
    fun sum(a: Int,b: Int) = a+b

    /*
        当一个引用可能为 null 值时, 对应的类型声明必须明确地标记为可为 null。
        当 str 中的字符串内容不是一个整数时, 返回 null:
     */
    fun StringToInt(str: String): Int? {
        // String 转 Int
        return str.toInt()
    }

    fun test(args: ArrayList<String>) {

        if (args.size < 2) {
            return
        }

        val one = StringToInt(args[0])
        val two = StringToInt(args[1])

        if (one != null && two != null) {
            print(one * two)
            Log.e("CoroutineTestActivity",(one * two).toString())
        }
    }

    // is 运算符检测一个表达式是否某类型的一个实例
    fun stringLength(obj: Any): Int?{
        if (obj is String) {  // 这个语句同时会把 obj 自动转换成 String
            return obj.length
        }
        return null
    }

    // 给函数设置默认参数
    fun functionDefaultValue(a: Int = 0,b: String = "8"){

    }


































































    //    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BREAK) {
//            // 把当前 activity 运行至后台
//            moveTaskToBack(true)
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("CoroutineTestActivity", "onDestroy")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // 把当前 activity 运行至后台
//        moveTaskToBack(true)
    }

}
