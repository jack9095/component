package com.base.library.bean;

import java.io.Serializable;

public class H5ShareBeanQuest implements Serializable {
    public String url; // 分享出去点击的链接
    public String content; // 分享出去内容
    public String title; // 标题
    public String picUrl; // 图片
    public String shareWord; // 分享说辞

    public H5ShareBeanQuest(String url, String content, String title, String picUrl, String shareWord) {
        this.url = url;
        this.content = content;
        this.title = title;
        this.picUrl = picUrl;
        this.shareWord = shareWord;
    }
}
