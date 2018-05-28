package com.diandi.service.impl.reader;


import com.diandi.dao.article.ArticleCollectDao;
import com.diandi.dao.article.ArticleComplainDao;
import com.diandi.dao.article.ArticleLikeDao;
import com.diandi.dao.article.ArticleStatisticsDao;
import com.diandi.entity.article.ArticleCollect;
import com.diandi.entity.article.ArticleComplain;
import com.diandi.entity.article.ArticleLike;
import com.diandi.service.reader.ArticleOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2017/12/26.
 *
 * @author DuanJiaNing
 */
@Service
public class ArticleOperateServiceImpl implements ArticleOperateService {

    @Autowired
    private ArticleStatisticsDao statisticsDao;

    @Autowired
    private ArticleCollectDao collectDao;

    @Autowired
    private ArticleLikeDao likeDao;

    @Autowired
    private ArticleComplainDao complainDao;

    @Override
    public int insertShare(int articleId, int sharerId) {

        statisticsDao.updateShareCountPlus(articleId);
        Integer count = statisticsDao.getShareCount(articleId);

        return count == null ? -1 : count;
    }

    @Override
    public int insertCollect(int articleId, int collectorId) {

        ArticleCollect collect = new ArticleCollect();
        collect.setArticleId(articleId);
        collect.setCollectorId(collectorId);
        collectDao.insert(collect);

        //博文收藏次数加一
        statisticsDao.updateCollectCountPlus(articleId);

        Integer id = collect.getCollectId();
        return id == null ? -1 : id;
    }

    @Override
    public int insertLike(int articleId, int likerId) {

        ArticleLike like = new ArticleLike();
        like.setArticleId(articleId);
        like.setLikerId(likerId);
        likeDao.insert(like);

        //博文喜欢次数加一
        statisticsDao.updateLikeCountPlus(articleId);

        Integer count = statisticsDao.getLikeCount(articleId);
        return count == null ? -1 : count;
    }

    @Override
    public int insertComplain(int articleId, int complainId, String content) {

        ArticleComplain complain = new ArticleComplain();
        complain.setArticleId(articleId);
        complain.setComplainerId(complainId);
        complain.setContent(content);
        complainDao.insert(complain);

        //博文投诉次数加一
        statisticsDao.updateComplainCountPlus(articleId);

        Integer id = complain.getId();
        return id == null ? -1 : id;
    }

    @Override
    public boolean deleteCollect(int authorId, int articleId) {
        int effect = collectDao.deleteCollectByAuthorIdAndArticleId(authorId, articleId);
        if (effect <= 0) return false;

        //博文收藏数减一
        statisticsDao.updateCollectCountMinus(articleId);

        return true;
    }

    @Override
    public boolean deleteLike(int likerId, int articleId) {
        int effect = likeDao.deleteLikeByAuthorId(likerId, articleId);
        if (effect <= 0) return false;

        //博文喜欢数减一
        statisticsDao.updateLikeCountMinus(articleId);

        return true;
    }


}
