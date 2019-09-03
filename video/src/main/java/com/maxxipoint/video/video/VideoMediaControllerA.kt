package com.maxxipoint.video.video

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.base.library.utils.LogUtil
import com.maxxipoint.video.R
import com.maxxipoint.video.demo.util.MediaHelper
import com.maxxipoint.video.demo.util.VideoUtil
import kotlinx.android.synthetic.main.video_controller_a.view.*

/**
 * 对应视频播放控制界面的封装
 */
class VideoMediaControllerA: RelativeLayout,View.OnClickListener {
    private val TAG = this.javaClass.simpleName

    private var hasPause: Boolean = false //是否暂停

    //消息处理器
    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_HIDE_TITLE -> tv_title.visibility = View.GONE
                MSG_UPDATE_TIME_PROGRESS -> updatePlayTimeAndProgress()
                MSG_HIDE_CONTROLLER -> showOrHideVideoController()
            }
        }
    }

    fun delayHideTitle() {
        //移除消息
        mHandler.removeMessages(MSG_HIDE_TITLE)
        //发送一个空的延时2秒消息
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_TITLE, 5000)
    }

    private val MSG_HIDE_TITLE = 0
    private val MSG_UPDATE_TIME_PROGRESS = 1
    private val MSG_HIDE_CONTROLLER = 2

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) { initView() }

    //初始化控件
    private fun initView() {
        View.inflate(context, R.layout.video_controller_a, this)
        iv_replay.setOnClickListener(this)
        iv_share.setOnClickListener(this)
        iv_play.setOnClickListener(this)
        iv_fullscreen.setOnClickListener(this)
        initViewDisplay()
        //设置视频播放时的点击界面
        setOnTouchListener(onTouchListener)
        //设置SeekBar的拖动监听
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
        //播放完成的界面要销毁触摸事件
        rl_play_finish.setOnTouchListener { v, event -> true }
    }

    private val onSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        //拖动的过程中调用
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

        }

        //开始拖动的时候调用
        override fun onStartTrackingTouch(seekBar: SeekBar) {
            //暂停视频的播放、停止时间和进度条的更新
            MediaHelper.pause()
            mHandler.removeMessages(MSG_UPDATE_TIME_PROGRESS)
        }

        //停止拖动时调用
        override fun onStopTrackingTouch(seekBar: SeekBar) {
            //把视频跳转到对应的位置
            val progress = seekBar.progress
            val duration = myVideoPlayer?.mPlayer?.duration
            val position = duration?.let { it * progress / 100 }
            position?.let { myVideoPlayer?.mPlayer?.seekTo(it) }
            //开始播放、开始时间和进度条的更新
            MediaHelper.play()
            updatePlayTimeAndProgress()
        }
    }

    private val onTouchListener = OnTouchListener { v, event ->
        //按下+已经播放了
        if (event.action == MotionEvent.ACTION_DOWN && myVideoPlayer!!.hasPlay) {
            //显示或者隐藏视频控制界面
            showOrHideVideoController()
        }
        true//去处理事件
    }

    //显示或者隐藏视频控制界面
    private fun showOrHideVideoController() {
        if (ll_play_control.visibility == View.GONE) {
            mHandler.removeMessages(MSG_HIDE_CONTROLLER)
            //显示（标题、播放按钮、视频进度控制）
            tv_title.visibility = View.VISIBLE
            iv_play.visibility = View.VISIBLE
            //加载动画
            val animation = AnimationUtils.loadAnimation(context, R.anim.bottom_enter)
            animation.setAnimationListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animation) {
                    super.onAnimationEnd(animation)
                    ll_play_control.visibility = View.VISIBLE
                    //过5秒后自动隐藏
                    mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLLER, 5000)
                }
            })
            //执行动画
            ll_play_control.startAnimation(animation)
        } else {
            //隐藏（标题、播放按钮、视频进度控制）
            tv_title.visibility = View.GONE
            iv_play.visibility = View.GONE
            //加载动画
            val animation = AnimationUtils.loadAnimation(context, R.anim.bottom_exit)
            animation.setAnimationListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animation) {
                    super.onAnimationEnd(animation)
                    ll_play_control.visibility = View.GONE
                }
            })
            //执行动画
            ll_play_control.startAnimation(animation)
        }
    }

    //更新进度条的第二进度（缓存）
    fun updateSeekBarSecondProgress(percent: Int) {
        seekBar.secondaryProgress = percent
    }

    //设置播放视频的总时长
    fun setDuration(duration: Int) {
        val time = VideoUtil.formatDuration(duration)
        tv_time.text = time
        tv_use_time.text = "00:00"
    }

    //更新播放的时间和进度
    fun updatePlayTimeAndProgress() {
        //获取目前播放的进度
        val currentPosition = MediaHelper.getInstance()?.currentPosition
        //格式化
        val useTime = currentPosition?.let { VideoUtil.formatDuration(it) }
        tv_use_time.text = useTime
        //更新进度
        val duration = MediaHelper.getInstance()?.duration
        if (duration == null || duration == 0) {
            return
        }
        val progress = currentPosition?.let { 100 * it / duration }
        if (progress != null) {
            seekBar.progress = progress
        }
        //发送一个更新的延时消息
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME_PROGRESS, 500)
    }

    //移除所有的消息
    fun removeAllMessage() {
        mHandler.removeCallbacksAndMessages(null)
    }

    //显示视频播放完成的界面
    fun showPlayFinishView() {
        tv_title.visibility = View.VISIBLE
        rl_play_finish.visibility = View.VISIBLE
        tv_all_time.visibility = View.VISIBLE
    }

    //简单的动画监听器（不需要其他的监听器去实现多余的方法）
    private open inner class SimpleAnimationListener : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation) {

        }

        override fun onAnimationEnd(animation: Animation) {

        }

        override fun onAnimationRepeat(animation: Animation) {

        }
    }

    // 初始化控件的显示状态
    fun initViewDisplay() {
        LogUtil.e("初始化控制播放页面")
        tv_title.visibility = View.VISIBLE
        iv_play.visibility = View.VISIBLE
        iv_play.setImageResource(R.drawable.new_play_video)
        tv_all_time.visibility = View.VISIBLE
        pb_loading.visibility = View.GONE
        ll_play_control.visibility = View.GONE
        rl_play_finish.visibility = View.GONE
        tv_use_time.text = "00:00"
        seekBar.progress = 0
        seekBar.secondaryProgress = 0
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_replay -> {
                //隐藏播放完成界面
                rl_play_finish.visibility = View.GONE
                //隐藏时间
                tv_all_time.visibility = View.GONE
                tv_use_time.text = "00:00"
                //进度条
                seekBar.progress = 0
                //把媒体播放器的位置移动到开始的位置
                MediaHelper.getInstance()?.seekTo(0)
                //开始播放
                MediaHelper.play()
                //延时隐藏标题
                delayHideTitle()
            }
            R.id.iv_share -> {
            }
            R.id.iv_play -> {

                if (MediaHelper.getInstance()?.isPlaying!!) {
                    //暂停
                    MediaHelper.pause()
                    //移除隐藏Controller布局的消息
                    mHandler.removeMessages(MSG_HIDE_CONTROLLER)
                    //移除更新播放时间和进度的消息
                    mHandler.removeMessages(MSG_UPDATE_TIME_PROGRESS)
                    iv_play.setImageResource(R.drawable.new_play_video)
                    hasPause = true
                } else {
                    if (hasPause) {
                        //继续播放
                        MediaHelper.play()
                        mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLLER, 2000)
                        updatePlayTimeAndProgress()
                        hasPause = false
                    } else {
                        //播放
                        iv_play.visibility = View.GONE
                        tv_all_time.visibility = View.GONE
                        pb_loading.visibility = View.VISIBLE
                        //视频播放界面也需要显示
                        myVideoPlayer?.setVideoViewVisible(View.VISIBLE)
                    }
                    iv_play.setImageResource(R.drawable.new_pause_video)
                }
            }
            R.id.iv_fullscreen -> {
            }
        }
    }

    private var myVideoPlayer: VideoPlayerA? = null
    fun setVideoPlayer(myVideoPlayer: VideoPlayerA) {
        this.myVideoPlayer = myVideoPlayer
    }

    //设置视频加载进度条的显示状态
    fun setPbLoadingVisible(visible: Int) {
        pb_loading.visibility = visible
    }
}