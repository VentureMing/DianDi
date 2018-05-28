package com.diandi.exception.api.parameter;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 字符串未按指定字符间隔
 * @date 2018/5/6 15:08
 */
public class ParameterStringSplitException extends BaseRuntimeException {

    public static final int code = 2;

    public ParameterStringSplitException(String message) {
        super(message, code);
    }

    public ParameterStringSplitException() {
        super(code);
    }
}
