package com.maxxipoint.video.video

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maxxipoint.video.R
import com.maxxipoint.video.demo.bean.VideoPlayInfo
import kotlinx.android.synthetic.main.activity_media_video.*

class MediaVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_video)
        val url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"

        //传递给条目里面的MyVideoPlayer
        videoPlayer?.setPlayData(VideoPlayInfo(0, url))
        //设置为初始化状态
        videoPlayer?.initViewDisplay()
    }
}
