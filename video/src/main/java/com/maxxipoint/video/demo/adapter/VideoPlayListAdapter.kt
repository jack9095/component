package com.maxxipoint.video.demo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.library.utils.LogUtil
import com.maxxipoint.video.R
import com.maxxipoint.video.demo.bean.VideoPlayInfo
import kotlinx.android.synthetic.main.item_video_play.view.*
import kotlinx.android.synthetic.main.video_play.view.*

class VideoPlayListAdapter(private val context: Context, videoPlayerItemInfoList: List<VideoPlayInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val videoPlayerItemInfoList: List<VideoPlayInfo>? = videoPlayerItemInfoList

    //记录之前播放的条目下标
    var currentPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video_play, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ListViewHolder
        //获取到条目对应的数据
        val info = videoPlayerItemInfoList?.get(position)
        //传递给条目里面的MyVideoPlayer
        info?.let { viewHolder.itemView.videoPlayer?.setPlayData(it) }
        //把条目的下标传递给MyVideoMediaController对象
        viewHolder.itemView.videoPlayer?.mediaController?.setPosition(position)
        //把Adapter对象传递给MyVideoMediaController对象
        viewHolder.itemView.videoPlayer?.mediaController?.setAdapter(this)
        if (position != currentPosition) {
            LogUtil.e("adapter 控制显示")
            //设置为初始化状态
            viewHolder.itemView.videoPlayer?.initViewDisplay()
        } else {
            LogUtil.e("adapter 控制显示 ***********&&&&&&&&&&&&&&&&&&7")
        }
    }

    override fun getItemCount(): Int {
        return videoPlayerItemInfoList?.size ?: 0
    }

    fun setPlayPosition(position: Int) {
        currentPosition = position
    }

    internal class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}