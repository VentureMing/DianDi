package com.diandi.exception.internal;

/**
 * @author shangmingyu
 * @Description: 未知内部错误
 * @date 2018/5/8 16:47
 */
public class UnknownInternalException extends InternalRuntimeException {

    public static final int code = 21;

    public UnknownInternalException(Throwable cause) {
        super(cause, code);
    }
}
