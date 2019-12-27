package com.maxxipoint.html.html;


import android.support.annotation.ColorInt;

import java.util.List;

/**
 * Author:yang
 * Time:2019-08-26 12:15
 * Description:每个Html标签对应的Bean
 */
public class HtmlLabelBean {
    public String tag;//当前Tag
    public int startIndex;//tag开始角标
    public int endIndex;//tag结束的角标
    public int size;//字体大小
    @ColorInt
    public int color;//字体颜色

    public String fontWeight;//字体样式,目前只是判断了是否加粗


    public List<HtmlLabelRangeBean> ranges;

    /**
     * 是否加粗
     */
    public boolean isBold() {
        return "bold".equalsIgnoreCase(fontWeight);
    }


    @Override
    public String toString() {
        return "HtmlLabelBean{" +
                "tag='" + tag + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", size=" + size +
                ", color=" + color +
                ", ranges=" + ranges +
                '}';
    }
}
