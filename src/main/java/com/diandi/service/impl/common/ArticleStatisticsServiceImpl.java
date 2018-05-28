package com.diandi.service.impl.common;


import com.diandi.dao.article.*;
import com.diandi.dto.article.ArticleStatisticsCountDTO;
import com.diandi.dto.article.ArticleStatisticsDTO;
import com.diandi.dto.author.AuthorDTO;
import com.diandi.entity.article.*;
import com.diandi.manager.DataFillingManager;
import com.diandi.manager.properties.DBProperties;
import com.diandi.service.author.AuthorStatisticsService;
import com.diandi.service.common.ArticleStatisticsService;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.StringUtils;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
@Service
public class ArticleStatisticsServiceImpl implements ArticleStatisticsService {

    @Autowired
    private DBProperties dbProperties;

    @Autowired
    private DataFillingManager dataFillingManager;

    @Autowired
    private ArticleCategoryDao categoryDao;



    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleStatisticsDao statisticsDao;

    @Autowired
    private ArticleLikeDao likeDao;

    @Autowired
    private ArticleCollectDao collectDao;

    @Autowired
    private ArticleCommentDao commentDao;

    @Autowired
    private AuthorStatisticsService statisticsService;

    @Override
    public ResultBean<ArticleStatisticsDTO> getArticleStatistics(int articleId) {

        Article article = articleDao.getArticleById(articleId);
        if (article == null) return null;

        int authorId = article.getAuthorId();

        // 统计信息
        ArticleStatistics statistics = statisticsDao.getStatistics(articleId);
        if (statistics == null) return null;

//        // 类别
//        ArticleCategory[] categories = null;
//        String sn = dbProperties.getStringFiledSplitCharacterForNumber();
//        int[] cids = StringUtils.intStringDistinctToArray(article.getCategoryIds(), sn);
//        if (!CollectionUtils.isEmpty(cids)) {
//            categories = categoryDao.listCategoryByIds(cids).toArray(new ArticleCategory[cids.length]);
//        }



        int c = 0;
        // 喜欢该篇文章的人
        AuthorDTO[] likes = null;
        List<ArticleLike> likeList = likeDao.listAllLikeByArticleId(articleId);
        if (!CollectionUtils.isEmpty(likeList)) {
            int[] ids = new int[likeList.size()];
            for (ArticleLike like : likeList) {
                ids[c++] = like.getLikerId();
            }
            likes = statisticsService.listAuthorDTO(ids);
        }

        // 收藏了该篇文章的人
        AuthorDTO[] collects = null;
        c = 0;
        List<ArticleCollect> collectList = collectDao.listAllCollectByArticleId(articleId);
        if (!CollectionUtils.isEmpty(collectList)) {
            int[] ids = new int[collectList.size()];
            for (ArticleCollect collect : collectList) {
                ids[c++] = collect.getCollectorId();
            }
            collects = statisticsService.listAuthorDTO(ids);
        }

        // 评论过该篇文章的人
        AuthorDTO[] commenter = null;
        c = 0;
        List<ArticleComment> commentList = commentDao.listAllCommentByArticleId(articleId);
        if (!CollectionUtils.isEmpty(commentList)) {
            int[] ids = new int[commentList.size()];
            for (ArticleComment comment : commentList) {
                // 评论者注销，但其评论将保存（匿名）
                Integer id = comment.getCommentatorId();
                if (id != null)
                    ids[c++] = id;
            }
            // ids 需要去重
            commenter = statisticsService.listAuthorDTO(IntStream.of(Arrays.copyOf(ids, c)).distinct().toArray());
        }

        ArticleStatisticsDTO dto = dataFillingManager.articleStatisticsToDTO(article, statistics, null,
                likes, collects, commenter, dbProperties.getStringFiledSplitCharacterForString());

        return new ResultBean<>(dto);
    }


    @Override
    public ResultBean<ArticleStatisticsCountDTO> getArticleStatisticsCount(int articleId) {

        ArticleStatistics statistics = statisticsDao.getStatistics(articleId);
        if (statistics == null) return null;

        ArticleStatisticsCountDTO dto = dataFillingManager.articleStatisticsCountToDTO(statistics);
        return new ResultBean<>(dto);
    }

    @Override
    public boolean updateArticleViewCountPlus(int articleId) {
        int effect = statisticsDao.updateViewCountPlus(articleId);

        if (effect > 0) return true;
        else return false;
    }
}
