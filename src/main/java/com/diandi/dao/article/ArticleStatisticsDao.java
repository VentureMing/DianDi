package com.diandi.dao.article;

import com.diandi.dao.BaseDao;
import com.diandi.entity.article.ArticleStatistics;
import org.springframework.stereotype.Repository;

/**
 * @author shangmingyu
 * @Description: article_statistics相关操作
 * @date 2018/5/6 21:32
 */
@Repository
public interface ArticleStatisticsDao extends BaseDao<ArticleStatistics> {

    /**
     * 通过唯一约束（articleId）删除记录
     *
     * @param articleId articleId
     * @return 操作影响行数
     */
    int deleteByUnique(int articleId);

    /**
     * 查询文章的统计信息articleId
     *
     * @param articleId
     * @return 查询结果
     */
    ArticleStatistics getStatistics(int articleId);

    /**
     * 评论次数加一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateCommentCountPlus(int articleId);

    /**
     * 浏览次数加一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateViewCountPlus(int articleId);

    /**
     * 作者回复该文章评论的次数加一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateReplyCommentCountPlus(int articleId);

    /**
     * 文章被收藏次数加一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateCollectCountPlus(int articleId);

    /**
     * 举报次数加一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateComplainCountPlus(int articleId);

    /**
     * 被分享次数加一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateShareCountPlus(int articleId);

    /**
     * 赞赏次数加一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateAdmireCountPlus(int articleId);

    /**
     * 喜欢次数加一
     *
     * @param articleId articleId
     * @return 执行结果
     */
    int updateLikeCountPlus(int articleId);

    /**
     * 评论次数减一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateCommentCountMinus(int articleId);

    /**
     * 浏览次数减一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateViewCountMinus(int articleId);

    /**
     * 回复该评论的次数减一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateReplyCommentCountMinus(int articleId);

    /**
     * 被收藏次数减一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateCollectCountMinus(int articleId);

    /**
     * 举报次数减一
     *
     * @param articleId
     * @return 执行结果
     */
    int updateComplainCountMinus(int articleId);

    /**
     * 被分享次数减一
     *
     * @param articleId articleId
     * @return 执行结果
     */
    int updateShareCountMinus(int articleId);

    /**
     * 赞赏次数减一
     *
     * @param articleId articleId
     * @return 执行结果
     */
    int updateAdmireCountMinus(int articleId);

    /**
     * 喜欢次数减一
     *
     * @param articleId articleId
     * @return 执行结果
     */
    int updateLikeCountMinus(int articleId);


    /**
     * 查询评论次数
     *
     * @param articleId articleId
     * @return 查询结果
     */
    Integer getCommentCount(int articleId);

    /**
     * 查询浏览次数
     *
     * @param articleId articleId
     * @return 查询结果
     */
    Integer getViewCount(int articleId);

    /**
     * 查询作者回复该评论的次数
     *
     * @param articleId articleId
     * @return 查询结果
     */
    Integer getReplyCommentCount(int articleId);

    /**
     * 查询被收藏次数
     *
     * @param articleId articleId
     * @return 查询结果
     */
    Integer getCollectCount(int articleId);

    /**
     * 查询举报次数
     *
     * @param articleId articleId
     * @return 查询结果
     */
    Integer getComplainCount(int articleId);

    /**
     * 查询被分享次数
     *
     * @param articleId articleId
     * @return 查询结果
     */
    Integer getShareCount(int articleId);

    /**
     * 查询赞赏次数
     *
     * @param articleId articleId
     * @return 查询结果
     */
    Integer getAdmireCount(int articleId);

    /**
     * 查询喜欢次数
     *
     * @param articleId articleId
     * @return 查询结果
     */
    Integer getLikeCount(int articleId);
}
