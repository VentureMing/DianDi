package com.diandi.dao.article;

import com.diandi.dao.BaseDao;
import com.diandi.entity.article.Article;
import org.apache.ibatis.annotations.Param;
import com.diandi.util.enums.ArticleStatusEnum;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 21:29
 */
@Repository
public interface ArticleDao extends BaseDao<Article> {

    //根据作者id获取文章id

    List<Integer> listArticleIdsByAuthorIds(@Param("authorIds") List<Integer> authorIds,
                                            @Param("status") int status);

    /**
     * 根据文章id查询文章
     *
     * @param ids    文章id
     * @param status 文章状态
     * @return 查询结果
     */
    List<Article> listArticleByArticleIds(@Param("ids") List<Integer> ids,
                                          @Param("status") int status);

    /**
     * 根据authorId和文章状态查询其发布的文章
     *
     * @param authorId
     * @param status
     * @return 查询到的文章
     * @see ArticleStatusEnum
     */
    List<Article> listArticle(@Param("authorId") int authorId,
                           @Param("status") int status);


    /**
     * 根据标题和作者查询文章id
     *
     * @param authorId
     * @param title     标题（同一作者的标题不能重复）
     * @return 查询结果
     */
    Integer getArticleIdByUniqueKey(@Param("authorId") int authorId,
                                    @Param("title") String title);

    /**
     * 通过id查询文章
     *
     * @param articleId
     * @return 查询结果
     */
    Article getArticleById(int articleId);



    /**
     * 通过文章id查询id，该方法只为检查文章是否存在
     *
     * @param articleId
     * @return 查询结果
     */
    Integer getArticleIdById(int articleId);



    /**
     * 根据作者获得其所有文章的字数统计
     *
     * @param authorId
     * @return 只有 wordCount 属性有值的结果
     */
    List<Article> listAllWordCountByAuthorId(@Param("authorId") int authorId,
                                             @Param("state") int state);





    /**
     * 查询指定作者的所有文章，限定查询文章的内容为 md 或 html。
     *
     * @param authorId
     * @param format    md 或 html
     * @return 查询结果，format 为 md 时只查询 content_md，为 html 时只查询 content
     */
    List<Article> listAllByFormat(@Param("authorId") int authorId,
                                  @Param("format") int format);



    /**
     * 统计指定作者的文章数量
     *
     * @param authorId
     * @return 数量
     */
    Integer countArticleByAuthorId(@Param("authorId") int authorId,
                                   @Param("state") int state);







//    -------------------------------------------------------------------------------------------------


    /**
     * 查询出所有的文章
     *
     * @return
     */
    List<Article> listAll();





    /**
     * 查询出指定作者的所有文章包含的标签
     *
     * @param authorId
     * @return 只查询了标签和article_id的结果集
     */
    List<Article> listAllLabelByAuthorId(int authorId);








}
