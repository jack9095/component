package com.kuanquan.networklibrary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this)
        }
        initView()
        initData()
    }

    abstract fun isBindEventBusHere(): Boolean
    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun dataObserver()

    protected fun showToast(msg:String){
        Toast.makeText(applicationContext,msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this)
        }
    }
}