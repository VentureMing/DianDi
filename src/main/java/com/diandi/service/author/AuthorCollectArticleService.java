package com.diandi.service.author;

import com.diandi.dto.author.FavouriteArticleListItemDTO;
import com.diandi.util.common.ArticleSortRule;
import com.diandi.util.restful.ResultBean;

import java.util.List;

/**
 * Created on 2017/12/18.
 * 博主收藏博文服务
 *
 * @author DuanJiaNing
 */
public interface AuthorCollectArticleService {


    /**
     * 获得博主收藏的博文清单
     *
     * @param authorId 博主id
     * @param offset    结果集起始位置
     * @param rows      行数
     * @param sortRule  排序规则，为null则不做约束
     * @return 查询结果
     */
    ResultBean<List<FavouriteArticleListItemDTO>> listCollectArticle(int authorId, int categoryId,
                                                                  int offset, int rows, ArticleSortRule sortRule);

    /**
     * 更新收藏信息
     *
     * @param authorId   博主id
     * @param articleId      博文id
     * @param newCategory 新的收藏到类别
     * @return 更新成功返回true
     */
    boolean updateCollect(int authorId, int articleId, int newCategory);

    /**
     * 获得博文收藏状态
     *
     * @param authorId 博主id
     * @param articleId    博文id
     * @return 已收藏为 true
     */
    boolean getCollectState(int authorId, int articleId);

    /**
     * 统计博主收藏量
     *
     * @param authorId 博文id
     * @return 查询结果
     */
    int countByAuthorId(int authorId);
}
