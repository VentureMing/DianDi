package com.diandi.exception.api.parameter;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 参数类型与目标类型不匹配
 * @date 2018/5/6 15:08
 */
public class ParameterTypeMismatchException extends BaseRuntimeException {

    public static final int code = 19;

    public ParameterTypeMismatchException() {
        super(code);
    }

    public ParameterTypeMismatchException(String message) {
        super(message, code);
    }

    public ParameterTypeMismatchException(String message, Throwable cause) {
        super(message, cause, code);
    }
}
