package com.base.library.base.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 基础ViewModel类，管理LiveData
 */
public class BaseViewModel extends ViewModel {
    public MutableLiveData<String> loadState = new MutableLiveData<>(); // 网络加载状态的 LiveData
//    private Map<String, MutableLiveData> maps;

    /**
     * 构造函数（在ViewModelProvider里通过class.newInstance创建实例）
     */
    public BaseViewModel() {
//        maps = new ConcurrentHashMap<>();  //初始化集合(线程安全)
    }

    /**
     * 通过指定的数据实体类获取对应的MutableLiveData类
     */
//    protected <T> MutableLiveData<T> get(Class<T> clazz) {
//        return get(null, clazz);
//    }

    /**
     * 通过指定的key或者数据实体类获取对应的MutableLiveData类
     */
//    protected <T> MutableLiveData<T> get(String key, Class<T> clazz) {
//        String keyName;
//        if (TextUtils.isEmpty(key)) {
//            keyName = clazz.getCanonicalName();
//        } else {
//            keyName = key;
//        }
//        MutableLiveData<T> mutableLiveData = maps.get(keyName);
//        // 判断集合是否已经存在liveData对象，若存在就返回
//        if (mutableLiveData != null) {
//            return mutableLiveData;
//        }
//        // 如果 Map 集合中没有对应实体类的 LiveData 对象，就创建并添加至集合中
//        mutableLiveData = new MutableLiveData<>();
//        assert keyName != null;
//        maps.put(keyName, mutableLiveData);
//        return mutableLiveData;
//    }

    // 可以缓解Rx内存占用不能释放的问题
    protected CompositeDisposable mCompositeDisposable;

    // 添加订阅
    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 在对应的FragmentActivity销毁之后调用
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onCleared() {
        super.onCleared();
//        if (maps != null) {
//            maps.clear();
//        }

        // 移除订阅
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
//            mCompositeDisposable.dispose();
        }
    }
}