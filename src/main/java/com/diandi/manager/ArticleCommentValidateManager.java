package com.diandi.manager;

import org.springframework.stereotype.Component;

/**
 * @author shangmingyu
 * @Description: 文章评论审核
 * @date 2018/5/6 15:08
 */
@Component
public class ArticleCommentValidateManager {

    /**
     * 审核评论内容
     *
     * @param content 评论内容
     * @return 审核通过为true
     */
    public boolean checkCommentContent(String content) {
        // UPDATE: 2018/2/3 更新
        return true;
    }

}
