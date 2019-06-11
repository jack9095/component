package com.kuanquan.universalcomponents.kotlinTest

/**
 * Kotlin 实现单例
 */
//class InstenceKotlinClass private constructor() {
class InstenceKotlinClass public constructor() {

    var a: Int = 0

    fun get(): Int{
        return a
    }

    fun set(a: Int){
        this.a = a
    }

    // 饿汉式单例
    object InstenceKotlinClass


    // 懒汉式
    // companion object 类的伴生对象  https://www.kotlincn.net/docs/reference/extensions.html
    companion object {
        private var instance: InstenceKotlinClass? = null
            // field 标识符只能用在属性的访问器内  幕后字段
            get() {  // 上面这个变量元素的 get 方法
                if (field == null) {
                    field = InstenceKotlinClass
                }
                return field
            }

        fun get(): InstenceKotlinClass {
            //细心的小伙伴肯定发现了，这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字
            return instance!!
        }
    }

}