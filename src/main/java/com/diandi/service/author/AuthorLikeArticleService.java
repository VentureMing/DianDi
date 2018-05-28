package com.diandi.service.author;


import com.diandi.dto.author.FavouriteArticleListItemDTO;
import com.diandi.util.common.ArticleSortRule;
import com.diandi.util.restful.ResultBean;

import java.util.List;

/**
 * Created on 2018/3/11.
 *
 * @author DuanJiaNing
 */
public interface AuthorLikeArticleService {

    /**
     * 检查博主是否喜欢过指定博文
     *
     * @param authorId 博主id
     * @param articleId    博文id
     * @return 已喜欢返回 true
     */
    boolean getLikeState(int authorId, int articleId);

    /**
     * 获取博主喜欢博文列表
     *
     * @param authorId    博主id
     * @param offset       偏移
     * @param rows         行数
     * @param articleSortRule 排序规则
     * @return 查询结果
     */
    ResultBean<List<FavouriteArticleListItemDTO>> listLikeArticle(int authorId, int offset, int rows,
                                                                  ArticleSortRule articleSortRule);

    /**
     * 统计喜欢量
     *
     * @param authorId 博主id
     * @return 查询结果
     */
    int countByAuthorId(int authorId);
}
