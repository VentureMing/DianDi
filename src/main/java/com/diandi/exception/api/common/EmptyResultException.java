package com.diandi.exception.api.common;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 查询结果为空
 * @date 2018/5/6 15:08
 */
public class EmptyResultException extends BaseRuntimeException {

    public static final int code = 14;

    public EmptyResultException() {
        super(code);
    }

    public EmptyResultException(String message) {
        super(message, code);
    }
}
