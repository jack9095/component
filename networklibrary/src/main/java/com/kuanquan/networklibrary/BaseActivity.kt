package com.kuanquan.networklibrary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kuanquan.networklibrary.util.LogUtil
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {

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

    protected fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    private var progressDialog: LoadDialog? = null
    fun showProgressDialog() {
        progressDialog = LoadDialog(this).apply {
            //点击外部都不可取消
            setCanceledOnTouchOutside(false)
            //点击返回键可取消
            setCancelable(true)
        }
        progressDialog?.run {
            try {
                if (isShowing)
                    dismiss()
                else
                    show()
            } catch (e: Exception) {
                LogUtil.e("异常 = ", e)
            }
        }
    }

    fun dismissProgressDialog() {
        try {
            progressDialog?.run {
                // progressDialog.hide();会导致android.view.WindowLeaked
                if (isShowing) dismiss()
            }
        } catch (e: Exception) {
            LogUtil.e("进度弹框取消异常 = ", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBindEventBusHere()) EventBus.getDefault().unregister(this)
    }
}