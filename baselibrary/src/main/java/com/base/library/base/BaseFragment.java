package com.base.library.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import com.base.library.utils.LogUtil;
import com.base.library.utils.ToastUtils;
import com.base.library.widget.LoadDialog;

/**
 * BaseFragment，处理ViewModelProvider的初始化
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    private ViewModelProvider viewModelProvider;
    protected Context context;
    protected FragmentActivity activity;
    protected View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelProvider = getViewModelProvider();
        context = this.getActivity();
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
    }

    public LoadDialog progressDialog;

    public LoadDialog showProgressDialog() {
        progressDialog = new LoadDialog(getActivity());
        //点击外部都不可取消
        progressDialog.setCanceledOnTouchOutside(false);
        //点击返回键不可取消
//        progressDialog.setCancelable(false);
        progressDialog.show();
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

    /**
     * 创建ViewModel对象
     *
     * @param clazz
     * @return
     */
    public <T extends ViewModel> T get(Class<T> clazz) {
        return viewModelProvider.get(clazz);
    }

    /**
     * 初始化ViewModelProvider对象
     *
     * @return
     */
    private ViewModelProvider getViewModelProvider() {
        return ViewModelProviders.of(this);
    }





    protected void showToast(String str){
        ToastUtils.showMessage(context,str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModelProvider = null;
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = initLayout(inflater, container);
        view = inflater.inflate(getLayoutId(),null,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    protected void addOnClickListeners(View.OnClickListener listener, @IdRes int... ids) {
        if (ids != null) {
            for (@IdRes int id : ids) {
                view.findViewById(id).setOnClickListener(listener);
            }
        }
    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
//    protected abstract View initLayout(LayoutInflater inflater, ViewGroup container);
    protected abstract int getLayoutId();

    /**
     * init all views and add events
     */
    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);

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
     *
     * @param mRecyclerView RecyclerView
     * @param position      位置对应的角标
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.activity = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }
}