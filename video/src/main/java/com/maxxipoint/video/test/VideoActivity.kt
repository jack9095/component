package com.maxxipoint.video.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maxxipoint.video.R
import kotlinx.android.synthetic.main.activity_video.*

/**
 * https://github.com/jiajunhui/PlayerBase
 */
class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        video_play_view.createMedia()
        video_play_view.setActivity(this)
        val url =
            "https://vd4.bdstatic.com/mda-jhefq5cuh085w5q8/sc/mda-jhefq5cuh085w5q8.mp4?auth_key=1567407475-0-0-28a0e2835ef5b7c7e8909ebaf10370dd&bcevod_channel=searchbox_feed&pd=bjh&abtest=all"
       val url1 = "https://vd4.bdstatic.com/mda-jfvfz7g3i21mtzwn/sc/mda-jfvfz7g3i21mtzwn.mp4?auth_key=1567409904-0-0-f12f042d73d61295ddf353e4d7873fd2&bcevod_channel=searchbox_feed&pd=bjh&abtest=all"
       val url2 = "http://player.youku.com/embed/XMjk4NzM1NTU2NA=="
        video_play_view.initVideo(url1)
        video_play_view.play()
    }
}
