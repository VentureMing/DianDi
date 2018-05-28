package com.diandi.dao.article;

import com.diandi.dao.BaseDao;
import com.diandi.entity.article.ArticleAndCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/23 0:13
 */
@Repository
public interface ArticleAndCategoryDao extends BaseDao<ArticleAndCategory> {


    // 根据类别获取文章Id
    List<Integer> listArticleIdsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);



    /**
     * 统计指定类别的文章数量----------未实现
     *
     * @param authorId
     * @param categoryId 类别id
     * @return 数量
     */
    Integer countArticleByCategory(@Param("authorId") int authorId,
                                   @Param("categoryId") int categoryId,
                                   @Param("state") int state);
}
