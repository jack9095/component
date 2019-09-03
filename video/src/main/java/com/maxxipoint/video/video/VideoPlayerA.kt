package com.maxxipoint.video.video

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.RelativeLayout
import com.maxxipoint.video.R
import com.maxxipoint.video.demo.bean.VideoPlayInfo
import com.maxxipoint.video.demo.util.MediaHelper
import kotlinx.android.synthetic.main.video_play_single.view.*

/**
 * 对于视频播放界面的一个封装类
 */
class VideoPlayerA: RelativeLayout {

    var mPlayer: MediaPlayer? = null
    private var mSurface: Surface? = null

    var hasPlay: Boolean = false // 是否播放了
    constructor(context: Context): super(context){ initView() }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs){ initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) { initView() }

    //初始化布局
    private fun initView() {
        View.inflate(context, R.layout.video_play_single, this)

        initViewDisplay()
        //把VideoPlayer对象传递给VideoMediaController
        mediaController?.setVideoPlayer(this)

        //进行TextureView控件创建的监听
        video_view?.surfaceTextureListener = surfaceTextureListener
    }

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        //创建完成  TextureView才可以进行视频画面的显示
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            mSurface = Surface(surface)//连接对象（MediaPlayer和TextureView）
            info?.url?.let { play(it) }
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return true
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }

    //视频播放（视频的初始化）
    private fun play(url: String) {
        try {
            mPlayer = MediaHelper.getInstance()
            mPlayer?.reset()
            mPlayer?.setDataSource(url)
            //让MediaPlayer和TextureView进行视频画面的结合
            mPlayer?.setSurface(mSurface)
            //设置监听
            mPlayer?.setOnBufferingUpdateListener(onBufferingUpdateListener)
            mPlayer?.setOnCompletionListener(onCompletionListener)
            mPlayer?.setOnErrorListener(onErrorListener)
            mPlayer?.setOnPreparedListener(onPreparedListener)
            mPlayer?.setScreenOnWhilePlaying(true)//在视频播放的时候保持屏幕的高亮
            //异步准备
            mPlayer?.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //准备完成监听
    private val onPreparedListener = MediaPlayer.OnPreparedListener {
        //隐藏视频加载进度条
        mediaController?.setPbLoadingVisible(View.GONE)
        //进行视频的播放
        MediaHelper.play()
        hasPlay = true
        //隐藏标题
        mediaController?.delayHideTitle()
        //设置视频的总时长
        mPlayer?.duration?.let { it1 -> mediaController?.setDuration(it1) }
        //更新播放的时间和进度
        mediaController?.updatePlayTimeAndProgress()
    }

    //错误监听
    private val onErrorListener = MediaPlayer.OnErrorListener { mp, what, extra -> true }

    //完成监听
    private val onCompletionListener = MediaPlayer.OnCompletionListener {
        //视频播放完成
        mediaController?.showPlayFinishView()
    }

    //缓冲的监听
    private val onBufferingUpdateListener = MediaPlayer.OnBufferingUpdateListener { mp, percent ->
        mediaController?.updateSeekBarSecondProgress(percent)
    }

    // 初始化控件的显示状态
    fun initViewDisplay() {
        video_view?.visibility = View.GONE
        mediaController?.initViewDisplay()
    }

    // 设置视频播放界面的显示
    fun setVideoViewVisible(visible: Int) {
        video_view?.visibility = View.VISIBLE
    }

    private var info: VideoPlayInfo? = null
    fun setPlayData(info: VideoPlayInfo) {
        this.info = info
    }
}