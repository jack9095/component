package com.base.library.base.network;

public interface CallBack<T> {

    /**
     * no network 没网络
     */
    void onNoNetWork();

    /**
     * @param t  成功
     */
    void onNext(T t);

    /**
     * @param e 失败
     */
    void onError(String e);
}
