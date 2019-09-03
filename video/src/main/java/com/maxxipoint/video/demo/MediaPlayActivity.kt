package com.maxxipoint.video.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.maxxipoint.video.R
import com.maxxipoint.video.demo.adapter.VideoPlayListAdapter
import com.maxxipoint.video.demo.bean.VideoPlayInfo
import com.maxxipoint.video.demo.util.MediaHelper
import kotlinx.android.synthetic.main.activity_media_play.*
import java.util.ArrayList

/**
 * TextureView+MediaPlayer在线短视频播放
 */
class MediaPlayActivity : AppCompatActivity() {

    private var videoPlayInfoList: MutableList<VideoPlayInfo>? = null
    private var lm: LinearLayoutManager? = null
    private var adapter: VideoPlayListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_play)
        initData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        //初始化RecyclerView
        lm = LinearLayoutManager(this)
        rv.layoutManager = lm

        // 添加分割线
        // rv.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1, Color.BLACK));
        adapter = videoPlayInfoList?.let { VideoPlayListAdapter(this, it) }
        rv.adapter = adapter
        //设置滑动监听
        rv.addOnScrollListener(onScrollListener)
    }

    private fun initData() {
        //网络视频路径
        //        String url = "http://ips.ifeng.com/video19.ifeng.com/video09/2017/05/24/4664192-102-008-1012.mp4";
        val url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        //数据的初始化
        videoPlayInfoList = ArrayList()
        for (i in 0..19) {
            videoPlayInfoList?.add(VideoPlayInfo(i, url))
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        //进行滑动
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //获取屏幕上显示的第一个条目和最后一个条目的下标
            val firstVisibleItemPosition = lm?.findFirstVisibleItemPosition()
            val lastVisibleItemPosition = lm?.findLastVisibleItemPosition()
            //获取播放条目的下标
            val currentPosition = adapter?.currentPosition
            if (lastVisibleItemPosition != null) {
                if ((firstVisibleItemPosition!! > currentPosition!! || lastVisibleItemPosition < currentPosition) && currentPosition > -1) {
                    //让播放隐藏的条目停止
                    MediaHelper.release()
                    adapter?.currentPosition = -1
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }
}
