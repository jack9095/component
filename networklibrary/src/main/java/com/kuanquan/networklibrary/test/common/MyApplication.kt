package com.kuanquan.networklibrary.test.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.kuanquan.networklibrary.BuildConfig
import com.kuanquan.networklibrary.http.HttpHelper
import com.kuanquan.networklibrary.http.UrlConfig
import com.kuanquan.networklibrary.util.LogUtil
import com.kuanquan.networklibrary.util.SharedPreferencesUtils

/**
 * 使用 kotlin 编译警告 http://www.aoaoyi.com/archives/1389.html
 */
class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initLog()
        HttpHelper.getInstance()?.init(UrlConfig.BASE_URL) // 初始化网络请求
        SharedPreferencesUtils.getInstance("demo_fl", this.applicationContext)
        LogUtil.e("MyApplication 初始化次数")
    }

    fun initARouter() {
//        if (BuildConfig.LOG_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    fun initLog() {
        val fLog = LogUtil.Builder(this)
            .isLog(true) //是否开启打印
            .isLogBorder(true) //是否开启边框
            .setLogType(LogUtil.TYPE.E) //设置默认打印级别
            .setTag("dx") //设置默认打印Tag
        LogUtil.init(fLog)
    }
}