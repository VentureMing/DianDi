package com.diandi.exception.internal;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 服务器内部错误，用于终止操作，这类错误不应该被传递到前端
 * @date 2018/5/6 15:08
 */
public class InternalRuntimeException extends BaseRuntimeException {

    public InternalRuntimeException(int code) {
        super(code);
    }

    public InternalRuntimeException(String message, int code) {
        super(message, code);
    }

    public InternalRuntimeException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public InternalRuntimeException(Throwable cause, int code) {
        super(cause, code);
    }
}
