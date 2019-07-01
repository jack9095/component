package com.kuanquan.universalcomponents.slide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.kuanquan.universalcomponents.R;

public class GuideActivity extends AppCompatActivity {

    /**
     *  每个可滑动的布局 一定要加上 一个tag="slide_flag"
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartFActivity.start(GuideActivity.this);
            }
        });
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideOtherTypeActivity.start(GuideActivity.this);
            }
        });
        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartRefreshLayoutActivity.start(GuideActivity.this);
            }
        });
    }
}
