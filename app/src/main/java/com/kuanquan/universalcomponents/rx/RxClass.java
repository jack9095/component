package com.kuanquan.universalcomponents.rx;

import android.annotation.SuppressLint;
import com.base.library.base.network.schedulers.SchedulerProvider;
import com.base.library.utils.GsonUtils;
import com.base.library.utils.LogUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * RxJava 中 操作符
 * https://blog.csdn.net/itchosen/article/details/86572976
 * https://www.jianshu.com/p/299fbeeb3c51
 */
@SuppressLint("CheckResult")
public class RxClass {

    /**
     * compose操作于整个数据流中，能够从数据流中得到原始的Observable<T>/Flowable<T>...
     * 当创建Observable/Flowable...时，compose操作符会立即执行，而不像其他的操作符需要在onNext()调用后才执行
     */
    public static void justOne() {
        // 订阅时依次发出10条数据,可以看到 just 最少可以容纳 10个元素 ，并且既可以有 int 也可以有 String 等等
        Observable.just(89, 23, "9", "1", 2, 3, 8, 5, 6, 0)
                .compose(SchedulerProvider.getInstance().<Serializable>applySchedulers())
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Exception {
                        LogUtil.e("justOne", GsonUtils.toJson(serializable));
                    }
                });
    }

    /**
     * from 操作符
     */
    public static void fromOne() {
        String[] strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
        List<String> lists = new ArrayList<>();
        lists.add("1");
        lists.add("2");
        lists.add("3");
        // 作用同just不过是把参数封装成数组或者可迭代的集合在依次发送出来，突破了just 10个参数的限制
//        Observable.fromArray(strings)
        Observable.fromIterable(lists)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.e("fromOne", GsonUtils.toJson(s));
                    }
                });
    }

    private static String[] strings1 = {"Hello", "World"};
    private static String[] strings2 = {"Hello", "RxJava"};

    /**
     * defer 操作符  顾名思义，延迟创建
     */
    public static void deferOne() {
        Observable<Object> defer = Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return Observable.fromArray(strings1);
            }
        });
        strings1 = strings2;  // 订阅前把 strings 给改了
        defer.compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LogUtil.e("deferOne", GsonUtils.toJson(o));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

        // 打印出来的数据 "Hello" ，"RxJava"
        // defer操作符起到的不过是一个“预创建”的作用，真正创建是发生在订阅的时候
    }


    /**
     * error 操作符
     * 创建一个会发出一个error事件的Observable
     */
    public static void errorOne() {
        Observable.error(new RuntimeException("这是一个异常"))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LogUtil.e("errorOne", GsonUtils.toJson(o));
                    }
                });
    }


    /**
     * range(范围) 操作符
     * 创建一个发射一组整数序列的Observable
     */
    public static void rangeOne() {
        // 参数1：从哪里开始   参数二：总共有几个 下面这组打印出来 3、4、5、6、7
        Observable.range(3, 5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e("errorOne", integer);
                    }
                });
    }

    /**
     * interval 操作符
     * 创建一个无限的计时序列，每隔一段时间发射一个数字（从0开始）的 Observable
     */
    public static void intervalOne() {
        // 参数1：时期，间隔时间   参数二：时间的单位，这里使用的是 秒
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong < 60) {

                        }
                        LogUtil.e("intervalOne", aLong);
                    }
                });

        try {
            System.in.read();// 阻塞当前线程，防止JVM结束程序  这里测试使用
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * buffer(转换) 操作符
     * buffer(int count)
     * 将原发射出来的数据以 count 为单元打包之后在分别发射出来
     * 下面的数据发射出去 3 个一组
     */
    public static void bufferOne() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .buffer(3)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        LogUtil.e("bufferOne", GsonUtils.toJson(integers));
                    }
                });
    }

    /**
     * map(转换) 操作符
     * 将原 Observable 发射出来的数据转换为另外一种类型的数据
     * <p>
     * 这里通过 map 把 String 转换成 Integer 发射出去
     */
    public static void mapOne() {
        Observable.just("Jack", "RxJava")
                .map(new Function<String, Integer>() { //泛型第一个类型是原数据类型，第二个类型是想要变换的数据类型

                    @Override
                    public Integer apply(String str) throws Exception {
                        // 这是转换成了Student类型
                        // Student student = new Student();
                        // student.setName(s);
                        // return student;
                        return str.hashCode();        //将数据转换为了int（取得其hashCode值）
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e("mapOne", integer);
                    }
                });
    }

    /**
     * flatMap 操作符
     * 作用类似于 map 又比 map 强大，map 是单纯的数据类型的转换，而 flapMap 可以将原数据转换成新的 Observables，
     * 再将这些 Observables 的数据顺序缓存到一个新的队列中，在统一发射出来
     * <p>
     * 应用场景：一个接口调用时依赖另一个接口的返回值，
     * 如一个接口调用成功后才调用第二个接口或者第二个接口需要第一个接口的返回值作为参数调用
     */
    public static void flatMapOne() {
        Observable.just("Jack", "RxJava")
                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Exception {
                        return Observable.just(s.hashCode());
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.e("flatMapOne", integer);
            }
        });
        /*
            这里虽然结果和map是相同的，但是过程却是不同的。
            flatMap是先将原来的三个字符串（"Hello","RxJava","Nice to meet you"）依次取其hashCode，
            在利用Observable.just将转换之后的int类型的值在发射出来。map只是单穿的转换了数据类型，
            而flapMap是转换成了新的Observable了，这在开发过程中遇到嵌套网络请求的时候十分方便。
         */
    }

    /**
     * filter(过滤) 操作符
     * 对发射的数据做一个限制，只有满足条件的数据才会被发射
     */
    public static void filterOne() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 5; // 大于 5 的数才能发射出去
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.e("filterOne", integer);
            }
        });
    }

    /**
     * take 操作符
     * 只发射前 N 项的数据（takeLast与take想反，只取最后 N 项数据）
     */
    public static void takeOne() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .take(2)  // 只发射前面两个数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e("takeOne", integer);
                    }
                });
    }

    /**
     * skip 操作符
     * 发射数据时忽略前 N 项数据（skipLast忽略后 N 项数据）
     */
    public static void skipOne() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .skip(2)  // 忽略发射前面两个数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e("skipOne", integer);
                    }
                });
    }

    /**
     * elementAt 操作符
     * 获取原数据的第 N 项数据作为唯一的数据发射出去（elementAtOrDefault会在index超出范围时，给出一个默认值发射出来）
     */
    public static void elementAtOne() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
//                .elementAt(2)  // 取角标为 2 也就是上面数据的 3 发射出去
                .elementAt(2,10) // 参数二是默认值
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e("elementAtOne", integer);
                    }
                });
    }

    /**
     * distinct 操作符
     * 过滤掉重复项
     *
     * 下面打印出的数据还是 1，2，3，4，9  重复的都被过滤掉
     */
    public static void distinctOne() {
        Observable.just(1, 2, 3, 4, 1, 2, 4, 3, 9)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e("distinctOne", integer);
                    }
                });
    }

    /**
     * startWith 操作符
     * 在发射一组数据之前先发射另一组数据
     *  可以放单一的也可以放集合、数组
     * 下面打印出的数据是 "One", "Two", "Three"   "1", "2", "3"
     */
    public static void startWithOne() {
        Observable.just("1", "2", "3")
                .startWithArray("One", "Two", "Three")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        LogUtil.e("startWithOne", str);
                    }
                });
    }

    /**
     * merge 操作符
     * 将多个 Observables 发射的数据合并后在发射
     */
    public static void mergeOne() {
        Observable.merge(Observable.just(1,2,3),
                Observable.just(4,5,6),
                Observable.just(7,8,9),
                Observable.just(10))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e("mergeOne", integer);
                    }
                });
    }

    /**
     * zip 操作符
     * 按照自己的规则发射与发射数据项最少的相同的数据
     *
     * 这个是更灵活的合并或者选择返回数据的规则
     */
    public static void zipOne() {
        Observable.zip(Observable.just(1, 8, 7),
                Observable.just(2, 5,0),
                Observable.just(3, 6,0),
                Observable.just(4,9,10)
                // 前面的参数对应上面的数据类型，最后一个参数可以改成自己想要的类型，这里依旧还是使用 Integer 类型
                , new Function4<Integer, Integer, Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2, Integer integer3, Integer integer4) throws Exception {
                        return (integer < integer2) ? integer3 : integer4;  // 这里是三元运算符
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.e("zipOne", integer);
                    }
                });

        // 打印结果 3、9  因为最少的项 就是第三项 只有两个 如果第三项 是 3，6，0 ，那么打印结果就是 3，6，10

        /*
            通过观察以上例子可以发现我们的发射规则是:
            如果发射的第一个数据小于第二个数则发射第三个数据，
            否则发射第四个数据（我们来验证一下，1确实是小于2的，所以发射的是3；8并不小于5，所以发射的是9，
            又因为四个发射箱，最少的之后两项，所以最后只发射了两项数据）
         */
    }
}
