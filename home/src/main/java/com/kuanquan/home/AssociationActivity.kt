package com.kuanquan.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.base.library.utils.LogUtil
import com.kuanquan.home.global.GlobalScopeTest

/**
 * 协程案例  https://www.jianshu.com/p/84cc26da7c6d
 */
class AssociationActivity : AppCompatActivity() {

    var globalScope: GlobalScopeTest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_association)

        globalScope = GlobalScopeTest()

//        val suma = fun Int.(other: Int): Int = this + other
//        LogUtil.e("求和1 -> $suma")
//
//        val sumb: Int.(Int) -> Int = { other -> plus(other) }
//        LogUtil.e("求和2 -> $sumb")

//        globalScope?.start()
//        globalScope?.main()
//        globalScope?.test()
//        globalScope?.joinTo()
//        globalScope?.mainTest()
//        globalScope?.scopeTest()
//        globalScope?.restructure()
//        globalScope?.daemonThread()
//        globalScope?.bolock()
//        globalScope?.cancel()
//        globalScope?.channelBasics()
//        globalScope?.channelClose()
//        globalScope?.channelProduce()
//        globalScope?.conduit()  // 管道使用
//        globalScope?.fanOut()  // 扇出使用
//        globalScope?.channelCache()  // 带缓存的管道
//        globalScope?.sumInt()  // 使用 async
        globalScope?.structuralization()  // 使用结构化 async
    }
}
