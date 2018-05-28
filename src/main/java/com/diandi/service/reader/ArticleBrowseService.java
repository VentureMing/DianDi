package com.diandi.service.reader;


import com.diandi.dto.article.ArticleCommentDTO;
import com.diandi.dto.article.ArticleMainContentDTO;
import com.diandi.entity.article.Article;
import com.diandi.util.restful.ResultBean;

import java.util.List;

/**
 * Created on 2017/12/14.
 * 博文浏览服务
 * <p>
 * 1 获得博文主体信息
 * 2 获得博文评论列表
 *
 * @author DuanJiaNing
 */
public interface ArticleBrowseService {

    /**
     * 获得博文主体信息
     *
     * @param articleId
     * @return 查询结果
     */
    ResultBean<ArticleMainContentDTO> getArticleMainContent(int articleId);

    /**
     * 获得博文评论列表，这里获取的是审核通过的评论
     *
     * @param articleId
     * @param offset 结果集起始位置
     * @param rows   行数
     * @return 查询结果
     */
    ResultBean<List<ArticleCommentDTO>> listArticleComment(int articleId, int offset, int rows);

}
