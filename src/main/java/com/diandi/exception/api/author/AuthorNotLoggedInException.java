package com.diandi.exception.api.author;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 作者未登录异常，一些api需要登录才能执行操作
 * @date 2018/5/6 15:08
 */
public class AuthorNotLoggedInException extends BaseRuntimeException {

    public static final int code = 1;

    public AuthorNotLoggedInException() {
        super(code);
    }

    public AuthorNotLoggedInException(String message) {
        super(message, code);
    }

    public AuthorNotLoggedInException(String message, Throwable cause) {
        super(message, cause, code);
    }

}
