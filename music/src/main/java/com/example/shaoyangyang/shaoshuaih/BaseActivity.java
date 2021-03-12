package com.example.shaoyangyang.shaoshuaih;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setStatusBar(Color.WHITE);
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void addOnClickListeners(View.OnClickListener listener, @IdRes int... ids) {
        if (ids != null) {
            for (@IdRes int id : ids) {
                findViewById(id).setOnClickListener(listener);
            }
        }
    }

    protected void showToast(String str){
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
    }

    // 状态栏颜色 start
    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);

            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }

    }

    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    protected @ColorInt int getStatusBarColor() {
        return Color.WHITE;
    }
    // 状态栏颜色 end


    private long mExitTime;
    public void oDoubleCheck() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * 判断是否有权限
     * @param permissions 权限
     * @return true 有权限  false 没权限
     */
    public boolean hasPermissions(String... permissions){
        for(String permission:permissions){
            if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * 申请权限
     * @param requestCode 请求码
     * @param permissions 申请的权限
     */
    public void requestPermissions(int requestCode, String... permissions){
        ActivityCompat.requestPermissions(this,permissions,requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getPermission();
                }else {
                    Toast.makeText(this,"你拒绝了读写权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public void getPermission() {}


    //格式化数字
    protected String formatTime(int length) {
        Date date = new Date(length);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");    //规定固定的格式
        return simpleDateFormat.format(date);
    }

    /**
     * 把字符串转换成时间戳
     * @param date  "08:01"
     * @return 时间戳 毫秒
     */
    public long date2TimeStamp(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
