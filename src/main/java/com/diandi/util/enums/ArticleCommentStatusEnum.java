package com.diandi.util.enums;

/**
 * @author shangmingyu
 * @Description: 文章评论状态，code 的值对应数据库表 article_comment 的 state 字段
 * @date 2018/5/7 13:06
 */
public enum ArticleCommentStatusEnum {

    /**
     * 正在审核
     */
    BEING_AUDITED(0),

    /**
     * 审核通过
     */
    RIGHTFUL(1);

    private final int code;

    ArticleCommentStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
