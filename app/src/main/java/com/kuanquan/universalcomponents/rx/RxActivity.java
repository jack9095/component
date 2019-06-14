package com.kuanquan.universalcomponents.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.base.library.base.network.schedulers.SchedulerProvider;
import com.base.library.utils.LogUtil;
import com.kuanquan.universalcomponents.R;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscription;

/**
 * 基础操作 和 被压操作
 */
public class RxActivity extends AppCompatActivity {

    private static final String TAG = RxActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_activity);

//        RxClass.justOne();
//        RxClass.fromOne();
//        RxClass.deferOne();  // 预创建 懒加载
//        RxClass.errorOne();
//        RxClass.rangeOne();  // 一组整数序列
//        RxClass.intervalOne();  // 倒计时
//        RxClass.bufferOne();   // 转换数据
//        RxClass.mapOne();   // 转换数据类型
//        RxClass.flatMapOne();
//        RxClass.filterOne();   // 过滤
//        RxClass.takeOne();   //
//        RxClass.skipOne();   // 与 take 操作符有异曲同工之妙
//        RxClass.elementAtOne();   // 指定发射哪一个数据
//        RxClass.distinctOne();   // 去重
//        RxClass.startWithOne();   // 组合
//        RxClass.mergeOne();   // 合并操作符
        RxClass.zipOne();   //





        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                // 子线程
                emitter.onNext("哈哈"); // 把数据发射出去
                emitter.onComplete();
//                LogUtil.e(TAG,"subscribe   " + Thread.currentThread().getName());
            }
        })
                .compose(SchedulerProvider.getInstance().applySchedulers())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        // 主线程 doOnNext 方法在 onNext 前面执行
//                        LogUtil.e(TAG,"doOnNext   " + Thread.currentThread().getName());
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        // 主线程  doOnComplete 在  onComplete方法之前执行
//                        LogUtil.e(TAG,"doOnComplete   " + Thread.currentThread().getName());
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
                        // 主线程
//                        LogUtil.e(TAG,"onSubscribe     " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Object o) {
                        // 主线程
//                        LogUtil.e(TAG,"onNext    " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG,"onError   " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        // 主线程
//                        LogUtil.e(TAG,"onComplete   " + Thread.currentThread().getName());
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
