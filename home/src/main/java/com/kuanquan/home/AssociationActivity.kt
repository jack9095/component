package com.kuanquan.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.base.library.utils.LogUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 协程案例  https://www.jianshu.com/p/84cc26da7c6d
 */
class AssociationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_association)

        // 通常启动协程有 launch 和 async 方法
        GlobalScope.launch {
            LogUtil.e("挂起之前")
            delay(10000)  // 非阻塞 delay函数就是一个挂起函数，它用suspend关键字修饰，挂起函数只能从一个协程代码内部调用，普通代码不能调用
            LogUtil.e("挂起之后")
//            invoke()
        }

        // Lambda 表达式或者匿名函数（以及局部函数和对象表达式） 可以访问其 闭包 ，
        // 即在外部作用域中声明的变量。 与 Java 不同的是可以修改闭包中捕获的变量
        var ints = intArrayOf(1,2,3)
        var sum = 0
        ints.filter { it > 0 }.forEach {
            sum += it
        }
        LogUtil.e("和 = $sum")

        val suma = fun Int.(other: Int): Int = this + other

        val sumb: Int.(Int) -> Int = { other -> plus(other) }
    }
}
