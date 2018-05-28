package com.diandi.service.common;


import com.diandi.dto.article.ArticleStatisticsCountDTO;
import com.diandi.dto.article.ArticleStatisticsDTO;
import com.diandi.util.restful.ResultBean;

/**
 * Created on 2017/12/18.
 *
 * @author DuanJiaNing
 */
public interface ArticleStatisticsService {

    /**
     * 获取博文统计信息
     *
     * @param blogId 博文id
     * @return 查询结果
     */
    ResultBean<ArticleStatisticsDTO> getArticleStatistics(int blogId);

    /**
     * 获取博文统计信息（只获取数据量）
     *
     * @param blogId 博文id
     * @return 查询结果
     */
    ResultBean<ArticleStatisticsCountDTO> getArticleStatisticsCount(int blogId);

    /**
     * 更新博文浏览次数（加一）
     *
     * @param blogId 博文id
     * @return 更新成功为true
     */
    boolean updateArticleViewCountPlus(int blogId);
}

