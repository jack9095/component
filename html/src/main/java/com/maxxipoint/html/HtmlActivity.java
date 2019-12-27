package com.maxxipoint.html;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.maxxipoint.html.html.HtmlHelper;
import com.maxxipoint.html.html.HtmlTextView;
import com.maxxipoint.html.radius.RadiusBgUtils;
import com.maxxipoint.html.utils.ScreenUtils;

public class HtmlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        ScreenUtils.getContext(this);
        optHtml();
    }

    private void optHtml() {
        TextView htmlTv1 = findViewById(R.id.html_tv1);
        String htmlStr1 = HtmlHelper.createSpanHtmlStr("红色内容,20px,加粗", "#FF0000", 30, true);
        htmlTv1.setText(HtmlHelper.getHtmlSpanned(htmlStr1));

        String htmlStr2 =
                "<span style='color:#EE30A7;font-size:20px'>Html" +
                        "<font color='#EE2C2C' size='40px'>字体变大,色值变化</font>"
                        +
                        "<font color='#CD8500' size='60px'>字体变大,色值变化1</font>" +
                        "</span>";
        HtmlTextView htmlTv2 = findViewById(R.id.html_tv2);
        htmlTv2.setHtmlColorSize(htmlStr2);


        String htmlStr3 =
                "<font color='#4F94CD' size='40px'>我已经完成</font>" +
                        "<font color='#FF0000' size='80px'>80%</font>" +
                        "<font color='#4F94CD' size='40px'>的暑假作业</font>";
        HtmlTextView htmlTv3 = findViewById(R.id.html_tv3);
        htmlTv3.setHtmlColorSize(htmlStr3);

        String htmlStr4 =
                "<span style='color:#333333;font-size:20px'>我喜欢骑行,每天坚持骑行,可以" +
                        "<font color='#436EEE' size='25px'>强化身体素质</font>" +
                        ",已经累计骑行了" +
                        "<span style='color:#FF0000;font-size:40px;font-weight:bold'>20000</span>" +
                        "公里," +
                        "<font color='#0000EE' size='25px'>再接再厉</font>" +
                        ",为自己加油!</span>";
        HtmlTextView htmlTv4 = findViewById(R.id.html_tv4);
        htmlTv4.setHtmlColorSize(htmlStr4);
    }


    /**
     * 操作圆角
     */
    private void optRadiusView() {
        //================
        TextView radius_tv1 = findViewById(R.id.radius_tv1);
        float radius = getResources().getDimension(R.dimen.bg_radius);
        //设置背景
        GradientDrawable radiusBg1
                = RadiusBgUtils.createRectangleDrawable(
                Color.parseColor("#E066FF"),
                5,
                Color.BLACK,
                radius);
        radius_tv1.setBackground(radiusBg1);

        //===================
        TextView radius_tv2 = findViewById(R.id.radius_tv2);
        GradientDrawable radiusBg2
                = RadiusBgUtils.createRectangleDrawable(
                Color.parseColor("#EE3B3B"),
                5,
                Color.BLACK,
                new float[]{radius, 0, radius, 0});
        radius_tv2.setBackground(radiusBg2);


        //===========
        TextView radius_tv3 = findViewById(R.id.radius_tv3);
        GradientDrawable radiusBg3
                = RadiusBgUtils.createRectangleDrawable(
                Color.parseColor("#EE7600"),
                5,
                Color.BLACK,
                new float[]{0, 0, radius, radius});
        radius_tv3.setBackground(radiusBg3);

        //========
        TextView radius_tv4 = findViewById(R.id.radius_tv4);
        //前提是xml布局中设置的是背景类型是RECTANGLE, OVAL, LINE, RING其中之一
        GradientDrawable bg4 = (GradientDrawable) radius_tv4.getBackground();
        //修改填充色
        bg4.setColor(Color.parseColor("#EEEE00"));
    }

}
