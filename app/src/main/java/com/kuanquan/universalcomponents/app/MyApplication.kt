package com.kuanquan.universalcomponents.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
//import com.base.library.base.network.api.URLConfig
//import com.base.library.base.network.http.HttpHelper
import com.base.library.utils.LogUtil
import com.base.library.utils.SharedPreferencesUtils
import com.kuanquan.universalcomponents.BuildConfig
import com.kuanquan.universalcomponents.javaTest.hook.packageHook.PackageManagerHook
import com.tencent.bugly.Bugly

/**
 * 使用 kotlin 编译警告 http://www.aoaoyi.com/archives/1389.html
 */
class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        PackageManagerHook.hook(base)
    }

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initLog()
//        HttpHelper.getInstance().init(URLConfig.BASE_URL)
//        HttpHelper.getInstance()?.init(UrlConfig.BASE_URL) // 初始化网络请求
        SharedPreferencesUtils.getInstance("demo_fl", this.applicationContext)
        LogUtil.e("TestApplication 初始化次数")
        Bugly.init(applicationContext, "897534689", false)
    }

    fun initARouter() {
        if (BuildConfig.LOG_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    fun initLog() {
        val fLog = LogUtil.Builder(this)
            .isLog(BuildConfig.LOG_DEBUG) //是否开启打印
            .isLogBorder(true) //是否开启边框
            .setLogType(LogUtil.TYPE.E) //设置默认打印级别
            .setTag("dx") //设置默认打印Tag
        LogUtil.init(fLog)
    }
}