package com.diandi.exception.api.parameter;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description:参数错误
 * @date 2018/5/6 15:08
 */
public class ParameterIllegalException extends BaseRuntimeException {

    public static final int code = 3;

    public ParameterIllegalException() {
        super(code);
    }

    public ParameterIllegalException(String message) {
        super(message, code);
    }
}
