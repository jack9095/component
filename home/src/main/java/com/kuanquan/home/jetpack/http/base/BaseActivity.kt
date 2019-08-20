package com.kuanquan.home.jetpack.http.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_scrolling.*

/**
 * create By 2019/5/27 actor 晴天
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object{
        const val PERMISSION_CODE = 0X01
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        setSupportActionBar(toolbar)
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()

    protected fun startActivity(z : Class<*>){
        startActivity(Intent(applicationContext,z))
    }


    protected fun showToast(msg:String){
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
    }

    /**
     * 权限申请成功执行方法
     */
    protected open fun onPermissionSuccess(){

    }
    /**
     * 权限申请失败
     */
    protected open fun onPermissionFail(){

    }

}