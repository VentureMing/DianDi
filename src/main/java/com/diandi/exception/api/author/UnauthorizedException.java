package com.diandi.exception.api.author;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 未授权
 * @date 2018/5/6 15:08
 */
public class UnauthorizedException extends BaseRuntimeException {

    public static final int code = 4;

    public UnauthorizedException() {
        super(code);
    }

    public UnauthorizedException(String message) {
        super(message, code);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause, code);
    }
}
