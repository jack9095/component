package com.kuanquan.universalcomponents.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.library.utils.LogUtil;
import com.kuanquan.universalcomponents.R;

/**
 * 首页底部导航
 */
public class BottomNavigationView extends FrameLayout implements View.OnClickListener{

    private TextView[] mTabs;
    private Fragment[] fragments;
    private Button tabBtnMessage;
    private Button tabBtnMail;
    private Button tabBtnWork;
    private Button tabBtnShop;
    private Button tabBtnMine;
    private FragmentActivity mFragmentActivity;
    private int frameLayout; // 在 activity 中替换 fragment 的布局

    // 每一个 button 对应的角标，可用于App奔溃后，在 MainActivity 的 onRestoreInstanceState 和 onSaveInstanceState 使用，可保护fragment不错乱
    private int index;
    private int currentTabIndex; // 当前选中的角标
    private ImageView mImageView;

    public BottomNavigationView(Context context) {
        super(context);
        init();
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    // 每一个 button 对应的角标，可用于App奔溃后，在 MainActivity 的 onRestoreInstanceState 和 onSaveInstanceState 使用，可保护fragment不错乱
    public int getIndex(){
        return index;
    }

    public Fragment[] getFragments(){
        return fragments;
    }

    private void init(){
        View root = LayoutInflater.from(getContext()).inflate(R.layout.bottom_navigation_layout, this, true);
        tabBtnMessage = root.findViewById(R.id.tab_btn_message);
        tabBtnMessage.setOnClickListener(this);
        tabBtnMail = root.findViewById(R.id.tab_btn_mail);
        tabBtnMail.setOnClickListener(this);
        tabBtnWork = root.findViewById(R.id.tab_btn_work);
        tabBtnWork.setOnClickListener(this);
        tabBtnShop = root.findViewById(R.id.tab_btn_shop);
        tabBtnShop.setOnClickListener(this);
        tabBtnMine = root.findViewById(R.id.tab_btn_mine);
        tabBtnMine.setOnClickListener(this);
        root.findViewById(R.id.star_rl).setOnClickListener(this);
        mImageView = root.findViewById(R.id.star_im);
    }

    public void initView(FragmentActivity activity, int frameLayout, Fragment... fragmentArray) {
        this.mFragmentActivity = activity;
        this.frameLayout = frameLayout;
        mTabs = new TextView[5];

        mTabs[0] = tabBtnMessage;
        mTabs[1] = tabBtnMail;
        mTabs[2] = tabBtnWork;
        mTabs[3] = tabBtnShop;
        mTabs[4] = tabBtnMine;

        mTabs[0].setSelected(true);

        LogUtil.e("fragment 数据 = ",fragmentArray.length);
        fragments = new Fragment[]{fragmentArray[0],fragmentArray[1],fragmentArray[2],fragmentArray[3],fragmentArray[4]};

        activity.getSupportFragmentManager().beginTransaction().add(frameLayout,fragmentArray[0]).show(fragmentArray[0]).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tab_btn_message) {
            index = 0;
            mFragmentActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//白色
        } else if (i == R.id.tab_btn_mail) {
            index = 1;
            mFragmentActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
        } else if (i == R.id.tab_btn_work || i == R.id.star_rl) {
            index = 2;
            mFragmentActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
        } else if (i == R.id.tab_btn_shop) {
            index = 3;
            mFragmentActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
        } else if (i == R.id.tab_btn_mine) {
            index = 4;
            mFragmentActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//白色
        }
        disPlay(index);
    }

    public void disPlay(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction fragmentTransaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                fragmentTransaction.add(frameLayout,fragments[index]);
            }
            fragmentTransaction.show(fragments[index]).commit();
        }

        mTabs[currentTabIndex].setSelected(false);
        if (index == 2) {
            mImageView.setSelected(true);
        } else {
            mImageView.setSelected(false);
        }
        mTabs[index].setSelected(true);
        currentTabIndex = index;

//        if (isDark) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
//        } else {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//白色
//        }
    }
}
