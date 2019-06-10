package com.base.library.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.utils.CollectionsUtil;

/**
 * Created by fei.wang on 2019/5/3.
 * 顶部通用导航栏
 */
public class TopNavigationLayout extends FrameLayout{

    ImageView iv_back; // 左边返回按钮
    TextView tvTitle;  // 标题
    RelativeLayout root;  // 根布局
    TextView tv_right;  // 右边的点击按钮

    public TopNavigationLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public TopNavigationLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopNavigationLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TopNavigationLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_toobar_base_library, this, true);
        iv_back = inflate.findViewById(R.id.iv_back);
        tvTitle = inflate.findViewById(R.id.tv_title);
        tv_right = inflate.findViewById(R.id.tv_right);
        root = inflate.findViewById(R.id.root_rl_baselibrary);
    }

    public void setClick(OnClickListener listener){
        root.setOnClickListener(listener);
        iv_back.setOnClickListener(listener);
        tv_right.setOnClickListener(listener);
    }

    public TextView getRightTextView(){
        return tv_right;
    }

    public void setRightTextView(String str){
        CollectionsUtil.setTextView(tv_right,str);
    }

    public void setHintRightTextView(boolean blean){
        if (blean) {
            tv_right.setVisibility(View.GONE);
        } else {
            tv_right.setVisibility(View.VISIBLE);
        }
    }

    public void setHintLeftTextView(boolean blean){
        if (blean) {
            iv_back.setVisibility(View.GONE);
        } else {
            iv_back.setVisibility(View.VISIBLE);
        }
    }

    // 标题
    public void setTvTitle(String str){
        CollectionsUtil.setTextView(tvTitle, str);
    }

}
