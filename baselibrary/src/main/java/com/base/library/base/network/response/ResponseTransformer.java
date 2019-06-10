package com.base.library.base.network.response;//package com.dingxin.workspace.base.network.response;
//
//import com.dingxin.workspace.base.network.Exception.ApiException;
//import com.dingxin.workspace.base.network.Exception.CustomException;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableSource;
//import io.reactivex.ObservableTransformer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Function;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * 对返回的数据进行处理，区分异常的情况。
// */
//
//public class ResponseTransformer {
//
//    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
//        return upstream -> upstream
//                .onErrorResumeNext((ObservableSource<? extends BaseResponse<T>>) new ErrorResumeFunction<>())
//                .flatMap(new ResponseFunction<>());
//    }
//
//    public static <T> ObservableTransformer<BaseResponse<T>, T> result() {
//        return upstream -> {
//            return upstream
//                    .onErrorResumeNext((ObservableSource<? extends BaseResponse<T>>) new ErrorResumeFunction<>())
//                    .flatMap((Function<? super BaseResponse<T>, ? extends ObservableSource<?>>) new ResponseFunction<>());
//        };
//    }
//
//
//    /**
//     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
//     *
//     * @param <T>
//     */
//    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>> {
//
//        @Override
//        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
//            return Observable.error(CustomException.handleException(throwable));
//        }
//    }
//
//    /**
//     * 服务其返回的数据解析
//     * 正常服务器返回数据和服务器可能返回的exception
//     *
//     * @param <T>
//     */
//    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {
//
//        @Override
//        public ObservableSource<T> apply(BaseResponse<T> tResponse) throws Exception {
//            int code = tResponse.code;
//            String message = tResponse.msg;
//            if (code == 200) {
//                return Observable.just(tResponse.data);
//            } else {
//                return Observable.error(new ApiException(code, message));
//            }
//        }
//    }
//
//
//}
