package com.diandi.exception.api.common;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 未知错误，一般为服务器错误，但无法归类的错误
 * @date 2018/5/6 15:08
 */

public class UnknownException extends BaseRuntimeException {

    public static final int code = 10;

    public UnknownException(String message) {
        super(message, code);
    }

    public UnknownException() {
        super(code);
    }

    public UnknownException(String message, Throwable e) {
        super(message, e, code);
    }
}
