package com.diandi.exception.api.author;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 登录失败
 * @date 2018/5/6 15:08
 */
public class LoginFailException extends BaseRuntimeException {

    public static final int code = 20;

    public LoginFailException() {
        super(code);
    }

    public LoginFailException(String message) {
        super(message, code);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause, code);
    }
}
