package com.diandi.dao.article;

import com.diandi.dao.BaseDao;
import com.diandi.entity.article.ArticleComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 21:31
 */
@Repository
public interface ArticleCommentDao extends BaseDao<ArticleComment> {
    /**
     * 根据文章id查询评论
     *
     * @param articleId
     * @param status 文章状态
     * @param offset 偏移位置
     * @param rows   行数
     * @return 查询结果
     */
    List<ArticleComment> listCommentByArticleId(@Param("articleId") int articleId,
                                          @Param("offset") int offset,
                                          @Param("rows") int rows,
                                          @Param("status") int status);

    /**
     * 根据articleId获得所有针对该文章的评论
     *
     * @param articleId
     * @return 查询结果
     */
    List<ArticleComment> listAllCommentByArticleId(int articleId);
}
