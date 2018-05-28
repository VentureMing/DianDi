package com.diandi.manager.properties;

import lombok.Data;

/**
 * @author shangmingyu
 * @Description: 读者参数配置
 * @date 2018/5/6 19:10
 */
@Data
public class ReaderProperties {

    /**
     * 默认返回的作者文章列表数量
     */
    private Integer requestAuthorArticleListCount;

    /**
     * 默认返回的作者文章评论数量
     */
    private Integer requestAuthorArticleCommentCount;
}
