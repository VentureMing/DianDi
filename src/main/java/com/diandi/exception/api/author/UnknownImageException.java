package com.diandi.exception.api.author;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 图片不存在
 * @date 2018/5/6 15:08
 */
public class UnknownImageException extends BaseRuntimeException {

    public static final int code = 8;

    public UnknownImageException(String message) {
        super(message,code);
    }

    public UnknownImageException() {
        super(code);
    }

    public UnknownImageException(String message, Throwable e) {
        super(message, e,code);
    }
}
