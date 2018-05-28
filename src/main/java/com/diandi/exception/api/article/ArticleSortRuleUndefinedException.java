package com.diandi.exception.api.article;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 文章排序规则未定义
 * @date 2018/5/6 15:08
 */
public class ArticleSortRuleUndefinedException extends BaseRuntimeException {

    public static final int code = 13;

    public ArticleSortRuleUndefinedException(String message) {
        super(message, code);
    }

    public ArticleSortRuleUndefinedException() {
        super(code);
    }
}
