package com.maxxipoint.html.html;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author:yang
 * Time:2019-08-23 20:25
 * Description:Html标签,支持font和span
 */
@SuppressLint("AppCompatCustomView")
public class HtmlTextView extends TextView {


    public HtmlTextView(Context context) {
        this(context, null);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setHtmlColorSize(String source) {
        setText(HtmlHelper.getHtmlSpanned(source));
    }

    /**
     * @param source
     * @param defaultSource 为空的时候,默认展示的内容
     */
    public void setHtmlColorSize(String source, String defaultSource) {
        setText(HtmlHelper.getHtmlSpanned(source, defaultSource));
    }

}
