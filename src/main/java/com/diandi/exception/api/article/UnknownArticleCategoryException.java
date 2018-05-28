package com.diandi.exception.api.article;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 未知文章类别
 * @date 2018/5/6 15:08
 */
public class UnknownArticleCategoryException extends BaseRuntimeException {

    public static final int code = 7;

    public UnknownArticleCategoryException(String message) {
        super(message,code);
    }

    public UnknownArticleCategoryException() {
        super(code);
    }
}
