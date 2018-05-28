package com.diandi.dao.article;

import com.diandi.dao.BaseDao;
import com.diandi.entity.article.ArticleCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: 文章类别的相关Dao层操作
 * @date 2018/5/6 21:30
 */
@Repository
public interface ArticleCategoryDao extends BaseDao<ArticleCategory> {
    /**
     * 根据id数组查询类别
     *
     * @param ids 类别id
     * @return 查询结果
     */
    List<ArticleCategory> listCategoryByIds(@Param("ids") int[] ids);

    /**
     * 查询作者创建的所有类别
     *
     * @param authorId 作者id
     * @param offset   结果集偏移
     * @param rows     行数
     * @return 查询结果
     */
    List<ArticleCategory> listCategoryByAuthorId(@Param("authorId") int authorId,
                                                  @Param("offset") int offset,
                                                  @Param("rows") int rows);

    /**
     * 在限定作者的情况下获取指定id的文章类别
     *
     * @param authorId   作者 articleId
     * @param categoryId 类别id
     * @return 查询结果
     */
    ArticleCategory getCategory(@Param("authorId") int authorId,
                                @Param("categoryId") int categoryId);

    /**
     * 统计指定作者创建的类别数量
     *
     * @param authorId 作者id
     * @return 数量
     */
    Integer countByAuthorId(int authorId);
}
