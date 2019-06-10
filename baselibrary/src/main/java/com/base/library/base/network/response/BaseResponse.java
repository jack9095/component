package com.base.library.base.network.response;

import java.io.Serializable;

/**
 * 返回结果封装
 */

public class BaseResponse<T> implements Serializable {

    public int code; // 返回的code   0 成功
    public T data; // 具体的数据结果
    public String msg; // message 可用来返回接口的说明
}
