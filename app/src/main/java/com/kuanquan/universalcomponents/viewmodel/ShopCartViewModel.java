package com.kuanquan.universalcomponents.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

/**
 * 我的页面 数据的网络请求
 */
public class ShopCartViewModel extends MainBaseViewModel {

    public MutableLiveData<String> headLiveData = new MutableLiveData<>();

}
