package com.base.library.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.library.utils.LogUtil;
import com.base.library.utils.ToastUtils;
import com.base.library.widget.LoadDialog;

/**
 * BaseActivity基础类，处理ViewModelProvider的初始化
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewModelProvider viewModelProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        viewModelProvider = getViewModelProvider();
        setStatusBar(Color.WHITE);
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
        initView();
        initData();
    }

    public LoadDialog progressDialog;

    public LoadDialog showProgressDialog() {
        progressDialog = new LoadDialog(this);
        //点击外部都不可取消
        progressDialog.setCanceledOnTouchOutside(false);
        //点击返回键可取消
        progressDialog.setCancelable(true);
        try {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog.show();
        } catch (Exception e){
            LogUtil.e("异常 = ", e);
        }
        return progressDialog;
    }


    public void dismissProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                // progressDialog.hide();会导致android.view.WindowLeaked
                progressDialog.dismiss();
            }
        }catch (Exception e){
            LogUtil.e("进度弹框取消异常 = ",e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModelProvider = null;
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }

        if (progressDialog != null) {
            dismissProgressDialog();
        }
    }

    /**
     * 创建ViewModel对象
     *
     * @param clazz
     * @return 对应的 T 的 ViewModel
     * 泛型中的限定，必须是ViewModel的子类
     */
    public <T extends ViewModel> T get(Class<T> clazz) {
        return viewModelProvider.get(clazz);
    }

    /**
     * 初始化ViewModelProvider对象
     *
     * @return ViewModelProvider
     */
    private ViewModelProvider getViewModelProvider() {
        return ViewModelProviders.of(this);
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    // 添加点击事件
    protected void addOnClickListeners(View.OnClickListener listener, @IdRes int... ids) {
        if (ids != null) {
            for (@IdRes int id : ids) {
                findViewById(id).setOnClickListener(listener);
            }
        }
    }

    protected void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
//        ToastUtils.showMessage(this,str);
    }

    /**
     * is bind eventBus
     *
     * @return
     */
    protected abstract boolean isBindEventBusHere();

    public void postEventBus(String type) {
        EventBus.getDefault().post(new EventCenter<Object>(type));
    }

    public void postEventBusSticky(String type) {
        EventBus.getDefault().postSticky(new EventCenter<Object>(type));
    }

    public void postEventBusSticky(String type, Object obj) {
        EventBus.getDefault().postSticky(new EventCenter<Object>(type, obj));
    }

    public void postEventBus(String type, Object obj) {
        EventBus.getDefault().post(new EventCenter<Object>(type, obj));
    }

    /**
     * 移动到position位置
     * @param mRecyclerView RecyclerView
     * @param position 位置对应的角标
     */
    protected void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            mRecyclerView.smoothScrollToPosition(position);
        }
    }

    /*********************************************** 设置状态栏的颜色 start *************************************************/
    /**
     * Android 6.0 以上设置状态栏颜色
     */
    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);

            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }

    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected @ColorInt int getStatusBarColor() {
        return Color.WHITE;
    }

    /*********************************************** 设置状态栏的颜色 end *************************************************/


    // 退出时的时间
    private long mExitTime;
    public void oDoubleCheck() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出员工门户", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
