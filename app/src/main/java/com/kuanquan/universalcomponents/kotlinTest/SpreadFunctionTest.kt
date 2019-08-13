package com.kuanquan.universalcomponents.kotlinTest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.base.library.utils.GsonUtils
import com.base.library.utils.LogUtil

/**
 * 扩展函数测试案例
 */
class SpreadFunctionTest : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test()
    }

    // 声明一个扩展函数，我们需要用一个 接收者类型 也就是被扩展的类型来作为他的前缀
    // 下面代码为 MutableList<Int> 添加一个swap 函数
    fun MutableList<Int>.swap(index1: Int,index2: Int){
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp

        // 这个 this 关键字在扩展函数内部对应到接收者对象（传过来的在点符号前的对象）
    }

    fun test(){
        val lists = mutableListOf(1,5,6,2)
        lists.swap(0,1)
        LogUtil.e("集合交换数据 = ",GsonUtils.toJson(lists))
    }

    // 上面的扩展函数当然可以泛型化
    fun <T> MutableList<T>.swapT(index1: Int, index2: Int) {
        val tmp = this[index1] // “this”对应该列表
        this[index1] = this[index2]
        this[index2] = tmp
    }
}