package com.diandi.exception.api.author;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 友情链接不存在
 * @date 2018/5/6 15:08
 */
public class UnknownLinkException extends BaseRuntimeException {

    public static final int code = 17;

    public UnknownLinkException(String message) {
        super(message,code);
    }

    public UnknownLinkException() {
        super(code);
    }

    public UnknownLinkException(String message, Throwable e) {
        super(message, e,code);
    }
}
