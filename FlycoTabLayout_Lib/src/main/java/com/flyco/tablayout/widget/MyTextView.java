package com.flyco.tablayout.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.*;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextA(String str) {
//        SpannableStringBuilder builder = new SpannableStringBuilder();
        //设置文字颜色Span
//        ForegroundColorSpan Span1 = new ForegroundColorSpan(Color.RED);
        //设置背景颜色Span
//        BackgroundColorSpan Span2 = new BackgroundColorSpan(Color.YELLOW);
        //设置超链接Span
//        URLSpan Span3 = new URLSpan("wwww.baidu.com");
        //设置文本样式之字体Span
//        AbsoluteSizeSpan Span4 = new AbsoluteSizeSpan(32);
        //设置删除线Span
//        StrikethroughSpan Span5 = new StrikethroughSpan();
        //设置下划线Span
//        UnderlineSpan Span6 = new UnderlineSpan();
//        builder.setSpan(Span1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(Span2, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(Span3, 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(Span4, 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(Span5, 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(Span6, 5, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (str.contains("#")) {
            String[] split = str.split("#");
//            builder.setSpan(Span4, 0, split[0].length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//            String s = builder.toString();
            Spanned spanned = Html.fromHtml("<font><big>" + split[0] + "</big></font>" + "<br>" + split[1]);
//            String s = spanned.toString();
//            String aa = s + "\n" + split[1];
            setText(spanned);
//            setText(builder);
        } else {
//            builder.setSpan(Span4, 0, str.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(Html.fromHtml("<font><big>" + str + "</big></font>"));
        }
    }

}
