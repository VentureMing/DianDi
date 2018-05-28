package com.diandi.exception.internal;

/**
 * @author shangmingyu
 * @Description: lucene IO操作出错
 * @date 2018/5/8 16:47
 */

public class InternalLuceneIOException extends InternalRuntimeException {
    public static final int code = 2;

    public InternalLuceneIOException(Throwable e) {
        super(e, code);
    }

    public InternalLuceneIOException() {
        super(code);
    }
}
