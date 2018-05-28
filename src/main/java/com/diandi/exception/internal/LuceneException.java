package com.diandi.exception.internal;

/**
 * @author shangmingyu
 * @Description: lucene 操作时出错
 * @date 2018/5/8 16:47
 */
public class LuceneException extends InternalRuntimeException {

    public static final int code = 1;

    public LuceneException(Throwable e) {
        super(e, code);
    }
}
