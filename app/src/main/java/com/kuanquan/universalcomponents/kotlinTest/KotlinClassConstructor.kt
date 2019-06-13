package com.kuanquan.universalcomponents.kotlinTest

import com.base.library.utils.LogUtil

/**
 * 构造函数的讲解
 * https://www.kotlincn.net/docs/reference/classes.html
 * 跟在类后面的是主构造函数
 */
//class KotlinClassConstructor constructor(name: String){
//class KotlinClassConstructor(val name: String){
class KotlinClassConstructor{

    // 主构造的参数可以在类体内声明的属性初始化器中使用
//    val customerKey = name.toUpperCase()

    val TAG: String = KotlinClassConstructor::class.java.simpleName

    // 初始化块 主构造的参数可以在初始化块中使用
    // 请注意，初始化块中的代码实际上会成为主构造函数的一部分。委托给主构造函数会作为次构造函数的第一条语句，
    // TODO 因此所有初始化块中的代码都会在次构造函数体之前执行。
    // 即使该类没有主构造函数，这种委托仍会隐式发生，并且仍会执行初始化块：
    init{
        LogUtil.e(TAG,"name = ")
//        LogUtil.e(TAG,"name = $name")
    }

    //  没有主构造函数时 次构造函数的写法
    constructor(int: Int){
         LogUtil.e(TAG,"name -> $int")
    }

    //  有主构造函数时 次构造函数的写法
    // 如果类有一个主构造函数，每个次构造函数需要委托给主构造函数，
    // 可以直接委托或者通过别的次构造函数间接委托。委托到同一个类的另一个构造函数用 this 关键字即可
//    constructor(name: String, parent: KotlinClassConstructor) : this(name) {
//        LogUtil.e(TAG,"name = $name")
//    }
}