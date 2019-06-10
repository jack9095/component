package com.kuanquan.universalcomponents.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.base.library.utils.LogUtil;
import com.kuanquan.universalcomponents.R;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscription;

public class RxActivity extends AppCompatActivity {

    private static final String TAG = RxActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_activity);


        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext("哈哈"); // 把数据发射出去
                emitter.onComplete();
                LogUtil.e(TAG,"subscribe   " + Thread.currentThread().getName());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LogUtil.e(TAG,"doOnNext   " + Thread.currentThread().getName());
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtil.e(TAG,"doOnComplete   " + Thread.currentThread().getName());
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(TAG,"doOnError   " + Thread.currentThread().getName());
                    }
                })
//                .subscribe();
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.e(TAG,"onSubscribe     " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Object o) {
                        LogUtil.e(TAG,"onNext    " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG,"onError   " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e(TAG,"onComplete   " + Thread.currentThread().getName());
                    }
                });

    }

    // 被压
    private void onFlowable(){
        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {
                long requested = emitter.requested();
                emitter.onNext(requested); // 把数据发射出去
                emitter.onComplete();
                LogUtil.e(TAG,"subscribe   " + Thread.currentThread().getName());
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        LogUtil.e(TAG,"doOnNext   " + Thread.currentThread().getName());
//                    }
//                })
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        LogUtil.e(TAG,"doOnComplete   " + Thread.currentThread().getName());
//                    }
//                })
//                .doOnError(new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        LogUtil.e(TAG,"doOnError   " + Thread.currentThread().getName());
//                    }
//                })
//                .subscribe();
                .subscribe(new FlowableSubscriber<Object>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        LogUtil.e(TAG,"onSubscribe     " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Object o) {
                        LogUtil.e(TAG,"onNext    " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.e(TAG,"onError   " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e(TAG,"onComplete   " + Thread.currentThread().getName());
                    }
                });
    }
}
