package com.maxxipoint.html.html;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;


public class HtmlHelper {

    /**
     * font:size;color
     * span:font-size;color,font-weight
     * 为空的时候,默认的是""
     *
     * @param source
     */
    public static CharSequence getHtmlSpanned(String source) {
        return getHtmlSpanned(source, "");
    }


    /**
     * font:size;color
     * span:font-size;color,font-weight
     *
     * @param source        html内容
     * @param defaultSource source为空的时候,默认的值,这个值不要为null
     */
    public static CharSequence getHtmlSpanned(String source, String defaultSource) {
        Log.d(HtmlCustomTagHandler.TAG, "原:" + source);
        if (TextUtils.isEmpty(source)) {
            Log.d(HtmlCustomTagHandler.TAG, "默认:" + defaultSource);
            return defaultSource;
        }
        boolean isTransform = false;//是否转换了自定义的标签
        if (source.contains("<" + HtmlCustomTagHandler.HTML_FONT)) {
            isTransform = true;
            //转化font标签
            source = source.replaceAll("<" + HtmlCustomTagHandler.HTML_FONT, "<" + HtmlCustomTagHandler.NEW_FONT);
            source = source.replaceAll("/" + HtmlCustomTagHandler.HTML_FONT + ">", "/" + HtmlCustomTagHandler.NEW_FONT + ">");
            Log.d(HtmlCustomTagHandler.TAG, "font->myfont");
        }
        if (source.contains("<" + HtmlCustomTagHandler.HTML_SPAN)) {
            isTransform = true;
            //转化span标签
            source = source.replaceAll("<" + HtmlCustomTagHandler.HTML_SPAN, "<" + HtmlCustomTagHandler.NEW_SPAN);
            source = source.replaceAll("/" + HtmlCustomTagHandler.HTML_SPAN + ">", "/" + HtmlCustomTagHandler.NEW_SPAN + ">");
            Log.d(HtmlCustomTagHandler.TAG, "span->myspan");
        }
        //转化为了自定义的标签,用HtmlColorSizeHandler解析
        if (isTransform) {
            //如果使用HtmlColorSizeHandler,转化后最外层增加一个<span></span>,否则有些属性不起作用,会改变标签的顺序
            source = "<span>" + source + "</span>";
            Log.d(HtmlCustomTagHandler.TAG, "转化后:" + source);
            Spanned spanned = Html.fromHtml(source, null, new HtmlCustomTagHandler());
            return spanned;
        } else {
            //1.如果未转化为自定义的标签,表示不包含myfont和myspan,就不需要使用HtmlColorSizeHandler转化
            if (source.contains("</")) {
                //2.如果source包含闭标签(</),那么就需要转化为系统支持的html
                Log.d(HtmlCustomTagHandler.TAG, "系统支持的Html:" + source);
                return Html.fromHtml(source);
            } else {
                //3.如果source不包含闭标签(</),那么就不要转化为html
                Log.d(HtmlCustomTagHandler.TAG, "未转化为Html:" + source);
                return source;
            }
        }
    }


    /**
     * 创建Html
     *
     * @param content 内容
     * @param color   颜色
     * @param size    大小 sp
     * @param isBlod  是否加粗
     * @return
     */
    public static String createSpanHtmlStr(String content, String color, int size, boolean isBlod) {
        return "<span style='color:" + color + ";font-size:" + size + "px;font-weight:" + (isBlod ? "bold" : "normal") + "'>" + content + "</span>";
    }


}
