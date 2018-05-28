package com.diandi.exception.api.author;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 未知作者
 * @date 2018/5/6 15:08
 */
public class UnknownAuthorException extends BaseRuntimeException {

    public static final int code = 6;

    public UnknownAuthorException(String message) {
        super(message,code);
    }

    public UnknownAuthorException() {
        super(code);
    }
}
