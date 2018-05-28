package com.diandi.exception.api.common;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description:
 * @date 2018/5/6 15:08
 */
public class DuplicationDataException extends BaseRuntimeException {

    public static final int code = 18;

    public DuplicationDataException() {
        super(code);
    }

    public DuplicationDataException(String message) {
        super(message, code);
    }

    public DuplicationDataException(String message, Throwable cause) {
        super(message, cause, code);
    }
}
