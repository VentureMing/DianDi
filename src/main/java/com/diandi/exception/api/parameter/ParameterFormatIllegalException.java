package com.diandi.exception.api.parameter;

import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 参数格式错误
 * @date 2018/5/6 15:08
 */
public class ParameterFormatIllegalException extends BaseRuntimeException {

    public static final int code = 3;

    public ParameterFormatIllegalException() {
        super(code);
    }

    public ParameterFormatIllegalException(String message) {
        super(message, code);
    }
}
