package com.maxxipoint.video.demo.bean

import java.io.Serializable

class VideoPlayInfo : Serializable{
    var id: Int = 0
    var url: String

    constructor(id: Int, url: String) {
        this.id = id
        this.url = url
    }
}