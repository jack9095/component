package com.maxxipoint.video.demo.util

import java.text.SimpleDateFormat
import java.util.*

object VideoUtil {

    //格式化时间 00：00
    fun formatDuration(duration: Int): String {
        val format = SimpleDateFormat("mm:ss")
        return format.format(Date(duration.toLong()))
    }
}