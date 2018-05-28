package com.diandi.dao.article;

import com.diandi.dao.BaseDao;
import com.diandi.entity.article.ArticleCollect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: 收藏文章的相关数据库操作
 * @date 2018/4/16 21:30
 */
@Repository
public interface ArticleCollectDao extends BaseDao<ArticleCollect>{
    /**
     * 根据作者和文章删除收藏文章
     *
     * @param authorId
     * @param articleId
     * @return 执行结果
     */
    int deleteCollectByAuthorIdAndArticleId(@Param("authorId") int authorId, @Param("articleId") int articleId);

    /**
     * 获取指定作者指定类别的收藏文章(后续修改)
     *
     * @param categoryId
     * @param categoryId 类别id
     * @param offset    结果集偏移量
     * @param rows      结果集数量
     * @return 查询结果
     */
    List<ArticleCollect> listCollectArticle(@Param("authorId") int authorId,
                                      @Param("categoryId") int categoryId,
                                      @Param("offset") int offset,
                                      @Param("rows") int rows);

    /**
     * 根据文章id和作者id更新收藏（文章删除后修改收藏列表）
     *
     * @param authorId （收藏者）
     * @param articleId
     * @param categoryId  收藏到类别
     * @return 影响行数
     */
    int updateByUnique(@Param("authorId") int authorId,
                       @Param("articleId") int articleId,
                       @Param("categoryId") Integer categoryId);

    /**
     * 根据articleId获得所有收藏记录
     *
     * @param articleId
     * @return 查询结果
     */
    List<ArticleCollect> listAllCollectByArticleId(int articleId);

    /**
     * 统计指定作者的文章收藏数
     *
     * @param
     * @return 数量
     */
    Integer countByCollectorId(int authorId);

    /**
     * 获取收藏记录
     *
     * @param authorId
     * @param articleId
     * @return 查询记录
     */
   ArticleCollect getCollect(@Param("authorId") int authorId, @Param("articleId") int articleId);

    /**
     * 查询指定作者收藏的所有文章
     * @param authorId
     * @return
     */
    List<ArticleCollect> listAllIdByAuthorId(int authorId);
}
