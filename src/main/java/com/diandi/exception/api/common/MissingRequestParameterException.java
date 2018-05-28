package com.diandi.exception.api.common;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 缺失请求参数
 * @date 2018/5/6 15:08
 */
public class MissingRequestParameterException extends BaseRuntimeException {

    public static final int code = 16;

    public MissingRequestParameterException() {
        super(code);
    }

    public MissingRequestParameterException(String message) {
        super(message, code);
    }

    public MissingRequestParameterException(String message, Throwable cause) {
        super(message, cause, code);
    }

}
