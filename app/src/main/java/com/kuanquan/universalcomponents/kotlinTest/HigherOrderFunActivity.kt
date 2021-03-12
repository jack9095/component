package com.kuanquan.universalcomponents.kotlinTest

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.base.library.utils.LogUtil
import com.kuanquan.mine.MineActivity
import com.kuanquan.mine.MyReceivingAddressActivity
import com.kuanquan.universalcomponents.R
import com.kuanquan.universalcomponents.dialog.BottomDialog
import com.kuanquan.universalcomponents.kotlinTest.adapter.AdapterActivity
import com.kuanquan.universalcomponents.main.HomeActivity
import com.kuanquan.universalcomponents.main.SearchActivity
import com.kuanquan.universalcomponents.rx.RxActivity
import com.kuanquan.universalcomponents.slide.GuideActivity
import kotlinx.android.synthetic.main.activity_higher_order_fun.*

/**
 * Kotlin标准库中的高阶函数
 * https://www.jianshu.com/p/b6befb149b5d
 * 高阶函数：将函数作为参数或返回一个函数
 * https://www.imooc.com/article/22500
 */
class HigherOrderFunActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_higher_order_fun)
        button.setOnClickListener(this)
        dialog.setOnClickListener(this)
        slide.setOnClickListener(this)
        address.setOnClickListener(this)
        mine.setOnClickListener(this)
        height_fun.setOnClickListener(this)
    }

    // with 扩展函数
    // 此函数实际干的事情就是，接受了一个参数，然后此对象调用 lamaba 函数，统一进行处理。
    // 实际使用：一个对象连续调用一系列的方法，使用此函数即可
    // PS: 将对象作为函数的参数，在函数内可以通过 this指代该对象。返回值为函数的最后一行或return表达式
    fun withDemo(){

        // 例如
        val paint = Paint()
       val paintF = with(paint){
            color = Color.BLACK
            strokeWidth = 1.0f
            isAntiAlias = true
            textSize = 18.0f
            paint
        }

        val list= mutableListOf<String>()
        with(list){
            add("1")
            add("2")
            add("3")
        }
        // 或者
        val lists = with(mutableListOf<String>()){
            add("11")
            add("22")
            add("33")
        }
    }

    // let 扩展函数
    // 调用对象（T）的let函数，则该对象为函数的参数。在函数内可以通过 it 指代该对象。
    // 返回值为函数的最后一行或指定return表达式
    fun letDemo(){
        val datas = ArrayList<String>()
       val intSize = datas?.let {
            // 假如data不为null，代码会执行到此处
           it.size
//            datas.size
        }
        LogUtil.e("let函数返回值 = ",intSize)
    }

    // run扩展函数
    // 调用 run 函数返回值为函数体最后一行，或return表达式
    // run 函数可以说是 let 和 with 的结合体
    // 适用于let ,with 函数任何场景，因为：
    // -> 弥补了let函数在函数体内必须使用it参数替代对象，在run函数中可以和with函数一样省略，直接访问实体的公有属性和方法
    // -> 弥补了with函数的传入的对象判空问题，在run函数中可以向let函数一样判空处理
    fun runDemo() {
        val str: String? = null
        // 弥补了with函数的传入的对象判空问题，在run函数中可以向let函数一样判空处理
        str?.run {
//            val str = "java"  // 和上面的变量不会冲突
            LogUtil.e("高阶函数 run = ",str)
        }

        LogUtil.e("高阶函数外层 run = ",str)

        for (i in 0..4){

            kotlin.run {
                if (i == 2) {
                    LogUtil.e("高阶函数 run 遍历数据= $i")
                }
                return@run LogUtil.e("高阶函数 run 遍历数据= $i -> return")

                // TODO 返回return表达式，return后面的代码不再执行（注意写法@run）
                // 下面这一局是不会打印的
                LogUtil.e("高阶函数 run 遍历数据= $i ->&*************************8")
            }
//            if (i == 2) {
//                // 在需要break的地方调用
//                break
//            }
//            LogUtil.e("高阶函数 run 遍历数据= $i")
        }

        LogUtil.e("高阶函数 run 最后语句")
    }

    // apply 扩展函数
    // 调用对象的apply函数，在函数范围内，可以任意调用该对象的任意方法，并返回该对象。
    // 注意他和run函数的区别，run返回的是最后一行，apply返回的是对象本身，
    // 由apply函数的定义我们可以看出apply适用于那些对象初始化需要给其属性赋值的情况
    //
    // 此外由于apply函数返回的是其对象本身，那么可以配合？.完成多级的非空判断操作，或者用于建造者模式的Builder中
    fun applyDemo(){

      val lists = ArrayList<String>().apply(){
            add("apply1")
            add("apply2")
            add("apply3")
        }

        var paint = Paint()?.apply {
            textSize = 14.0f
            color = Color.WHITE
            isAntiAlias = false
        }
    }

    // also  扩展函数
    // 调用对象的also函数，在函数块内可以通过 it指代该对象,返回值为该对象本身
    // 注意其和let函数的区别，let返回的是最后一行，also返回的是对象本身
    fun alsoDemo(){
        val datas = ArrayList<String>()
        val intSize = datas?.also {
            // 假如data不为null，代码会执行到此处
            datas.size
        }
        LogUtil.e("also 函数返回值 = ",intSize)
    }



    // 这里的 Unit 可以省略不写
    override fun onClick(v: View?): Unit {
        when (v?.id) {
            R.id.button -> {
                withDemo()
            }
            R.id.dialog -> {
                letDemo()
//                BottomDialog(this).builder().show()
            }
            R.id.slide -> {
                runDemo()
            }
            R.id.address -> {
                applyDemo()
            }
            R.id.mine -> {
                alsoDemo()
            }
            R.id.height_fun -> {

            }
        }
    }
}
