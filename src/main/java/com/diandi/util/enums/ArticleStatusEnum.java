package com.diandi.util.enums;

/**
 * @author shangmingyu
 * @Description: 文章状态，code 的值对应数据库表 article 的 state 字段
 * @date 2018/5/7 13:06
 */
public enum ArticleStatusEnum {

    /**
     * 博文是公开的，所有人都可以看到
     */
    PUBLIC(1),

    /**
     * 博文是私有的，只有博主才可以看到
     */
    PRIVATE(2),

    /**
     * 文章已经被删除了，根据回收策略进行删除
     */
    DELETED(3),

    /**
     * 正在审核
     */
    VERIFY(0);

    private final int code;

    ArticleStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ArticleStatusEnum valueOf(int code) {
        for (ArticleStatusEnum anEnum : values()) {
            if (anEnum.getCode() == code)
                return anEnum;
        }
        return null;
    }
}
