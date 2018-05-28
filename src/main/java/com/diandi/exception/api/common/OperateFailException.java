package com.diandi.exception.api.common;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 操作执行失败异常
 * @date 2018/5/6 15:08
 */
public class OperateFailException extends BaseRuntimeException {

    public static final int code = 18;

    public OperateFailException() {
        super(code);
    }

    public OperateFailException(String message) {
        super(message, code);
    }

    public OperateFailException(String message, Throwable e) {
        super(message, e, code);
    }
}
