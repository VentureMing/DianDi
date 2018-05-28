package com.diandi.exception.api.common;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 未指明操作，不明确的API调用
 * @date 2018/5/6 15:08
 */

public class UnspecifiedOperationException extends BaseRuntimeException {

    public static final int code = 9;

    public UnspecifiedOperationException() {
        super(code);
    }

    public UnspecifiedOperationException(String message) {
        super(message, code);
    }
}
