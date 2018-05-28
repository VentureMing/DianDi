package com.diandi.exception.api.article;


import com.diandi.exception.BaseRuntimeException;

/**
 * @author shangmingyu
 * @Description: 文章内容违规。
 * @date 2018/5/6 15:08
 */
public class ArticleContentIllegalException extends BaseRuntimeException {

    public static final int code = 11;

    public ArticleContentIllegalException(String message) {
        super(message, code);
    }

    public ArticleContentIllegalException() {
        super(code);
    }
}
