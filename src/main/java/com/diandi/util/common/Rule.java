package com.diandi.util.common;

/**
 * @author shangmingyu
 * @Description: 排序依据
 * @date 2018/5/8 16:24
 */
public enum Rule {
    /**
     * 评论次数
     */
    COMMENT_COUNT("评论次数"),

    /**
     * 浏览次数
     */
    VIEW_COUNT("浏览次数"),

    /**
     * 被收藏次数
     */
    COLLECT_COUNT("收藏次数"),

    /**
     * 喜欢次数
     */
    LIKE_COUNT("喜欢次数"),

    /**
     * 最初发布日期
     */
    RELEASE_DATE("发布日期"),

    /**
     * 举报次数
     */
    COMPLAIN_COUNT("举报次数"),

    /**
     * 被分享次数
     */
    SHARE_COUNT("分享次数"),

    /**
     * 回复该次数
     */
    REPLY_COMMENT_COUNT("评论回复次数"),

    /**
     * 赞赏次数
     */
    // UPDATE: 2018/4/22 保留但不使用
    ADMIRE_COUNT("赞赏次数");

    private final String title;

    Rule(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }

    /**
     * 检查是否存在与给定名字对应的枚举成员
     *
     * @param name 名字必须与某个枚举成员名完全相同
     * @return 存在返回 true，否则false
     */
    public static boolean contains(String name) {
        for (Rule rule : values()) {
            if (rule.name().equals(name)) return true;
        }

        return false;
    }
}
