package com.diandi.exception.api.article;


import com.diandi.exception.BaseRuntimeException;
/**
 * @author shangmingyu
 * @Description: 未知文章
 * @date 2018/5/6 15:08
 */

public class UnknownArticleException extends BaseRuntimeException {

    public static final int code = 5;

    public UnknownArticleException(String message) {
        super(message,code);
    }

    public UnknownArticleException() {
        super(code);
    }
}
