package com.diandi.exception.internal;

/**
 * @author shangmingyu
 * @Description: lucene SQL操作出错
 * @date 2018/5/8 16:47
 */
public class SQLException extends InternalRuntimeException {

    public static final int code = 3;

    public SQLException() {
        super(code);
    }

    public SQLException(Throwable e) {
        super(e, code);
    }
}
