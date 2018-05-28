package com.diandi.service.common;


import com.diandi.util.common.ArticleSortRule;
import com.diandi.util.enums.ArticleStatusEnum;

/**
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
public interface ArticleFilter<T> {

    /**
     * 全限定检索（包括关键字）
     *
     * @param keyWord  关键字,不做限定时传null
     * @param authorId 作者id
     *                 //     * @param offset      结果集起始位置
     *                 //     * @param rows        行数
     * @param sortRule 排序规则，为null则不做约束
     * @param status   文章状态
     * @return 查询结果
     */
    T listFilterAll(String keyWord, int authorId, ArticleSortRule sortRule, ArticleStatusEnum status);

    //只通过文章作者检索

    T listFilterOnlyByAuthorId(int authorId,
                               ArticleSortRule sortRule, ArticleStatusEnum status);

    /**
     * 类别检索（无关键字）
     *
     * @param authorId 作者id
     * @param sortRule 排序规则，为null则不做约束
     * @param status   文章状态
     * @return 查询结果
     */
    T listFilterByAuthorId(int authorId,
                           ArticleSortRule sortRule, ArticleStatusEnum status);

    /**
     * 获得一次检索后的结果集总条数
     *
     * @return 数量
     */
    int getFilterCount();

}
