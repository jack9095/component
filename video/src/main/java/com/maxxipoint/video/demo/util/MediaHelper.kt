package com.maxxipoint.video.demo.util

import android.media.MediaPlayer

/**
 * 多媒体的工具类
 */
object MediaHelper {

    private var mPlayer: MediaPlayer? = null

    //获取多媒体对象
    fun getInstance(): MediaPlayer? {
        if (mPlayer == null) {
            synchronized(MediaHelper::class.java) {
                if (mPlayer == null) {
                    mPlayer = MediaPlayer()
                }
            }
        }
        return mPlayer
    }

    //播放
    fun play() {
        mPlayer?.start()
    }

    //暂停
    fun pause() {
        mPlayer?.pause()
    }

    //释放
    fun release() {
        mPlayer?.release()
        mPlayer = null
    }
}