package com.diandi.dao.article;

import com.diandi.dao.BaseDao;
import com.diandi.entity.article.ArticleLike;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 21:32
 */
@Repository
public interface ArticleLikeDao extends BaseDao<ArticleLike> {

    /**
     * 根据作者和文章删除喜欢记录
     *
     * @param authorId     作者id
     * @param articleId    文章id
     * @return 操作影响的行数
     */
    int deleteLikeByAuthorId(@Param("authorId") int authorId, @Param("articleId") int articleId);

    /**
     * 根据文章id获得所有喜欢记录
     *
     * @param articleId 文章id
     * @return 查询结果
     */
    List<ArticleLike> listAllLikeByArticleId(int articleId);

    /**
     * 统计指定作者给出喜欢的次数
     *
     * @param authorId
     * @return 数量
     */
    Integer countLikeByLikerId(int authorId);

    /**
     * 根据作者id和文章id获取记录(后续修改)
     *
     * @param authorId 作者id
     * @param articleId    文章id
     * @return 查询记录
     */
    ArticleLike getLike(@Param("authorId") int authorId , @Param("articleId") int articleId);

    /**
     * 查询文章
     *
     * @param bloggerId 作者id
     * @param offset    起始偏移
     * @param rows      行数
     * @return 查询结果
     */
    List<ArticleLike> listLikeArticle(
            @Param("bloggerId") int bloggerId,
            @Param("offset") int offset,
            @Param("rows") int rows);

    /**
     * 查询指定作者喜欢的所有文章 articleId
     *
     * @param authorId 作者 articleId
     * @return 只查询文章 articleId
     */
    List<ArticleLike> listAllIdByAuthorId(int authorId);
}
