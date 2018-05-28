package com.diandi.exception.api.common;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 图片格式异常
 * @date 2018/5/6 15:08
 */
public class ImageFormatErrorException extends BaseRuntimeException {

    public static final int code = 22;

    public ImageFormatErrorException() {
        super(code);
    }

    public ImageFormatErrorException(String message) {
        super(message, code);
    }

    public ImageFormatErrorException(String message, Throwable e) {
        super(message, e, code);
    }
}
