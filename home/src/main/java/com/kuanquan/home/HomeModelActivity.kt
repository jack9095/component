package com.kuanquan.home

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_home_model.*
import kotlinx.android.synthetic.main.my_keyborad_view.*

/**
 * kotlin 协程 使用
 */
class HomeModelActivity : AppCompatActivity(), NumberKeyBoxView.NumberKeyBoxViewClick {
    override fun click(value: String?) {
        Log.e("HomeModelActivity","点击回调的数字 -> $value")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_model)
        key_box.setNumberKeyBoxViewClick(this)

        btn_hide.setOnClickListener {
            rotateAnimationEnd()
        }
        btn_show.setOnClickListener {
            key_box.visibility = View.VISIBLE
            rotateAnimation()
        }

        initView()
    }

    private fun initView() {
        et.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                mykeyboard.setAttachToEditText(v as EditText, et_root, mykeyboard_root)
            }
            true
        }
    }

    @SuppressLint("WrongConstant")
    fun rotateAnimation() {
        val objectAnimator = ObjectAnimator.ofFloat(key_box,"translationY",600.0f,0.0f)
        objectAnimator.setDuration(300)  // 动画执行时长
        objectAnimator.setRepeatCount(0)  // 重复次数 ，无限次
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART) // 重复模式，一个方向
        objectAnimator.start()
    }

    @SuppressLint("WrongConstant")
    fun rotateAnimationEnd() {
        val objectAnimator = ObjectAnimator.ofFloat(key_box,"translationY",0.0f,600.0f)
        objectAnimator.setDuration(300)  // 动画执行时长
        objectAnimator.setRepeatCount(0)  // 重复次数 ，无限次
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART) // 重复模式，一个方向
        objectAnimator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                key_box.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        objectAnimator.start()
    }
}
