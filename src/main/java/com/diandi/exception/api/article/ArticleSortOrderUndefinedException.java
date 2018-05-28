package com.diandi.exception.api.article;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 排序顺序错误。
 * @date 2018/5/6 15:08
 */
public class ArticleSortOrderUndefinedException extends BaseRuntimeException {

    public static final int code = 12;

    public ArticleSortOrderUndefinedException(String message) {
        super(message, code);
    }

    public ArticleSortOrderUndefinedException() {
        super(code);
    }
}
