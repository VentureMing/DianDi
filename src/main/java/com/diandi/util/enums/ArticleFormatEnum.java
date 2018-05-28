package com.diandi.util.enums;

import com.alibaba.druid.util.StringUtils;

/**
 * @author shangmingyu
 * @Description: 文章格式
 * @date 2018/5/7 21:41
 */
public enum ArticleFormatEnum {
    MD(0),

    HTML(1);

    private final int code;

    ArticleFormatEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean contains(String type) {
        if (StringUtils.isEmpty(type)) return false;

        return type.equalsIgnoreCase(MD.name()) || type.equalsIgnoreCase(HTML.name());
    }

    public static ArticleFormatEnum get(String type) {
        if (contains(type))
            return ArticleFormatEnum.valueOf(type.toUpperCase());
        else return null;
    }
}
