package com.diandi.service.impl.author;


import com.diandi.dao.article.*;
import com.diandi.dao.author.AuthorAccountDao;
import com.diandi.dao.author.AuthorBasicDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.dao.author.AuthorLinkDao;
import com.diandi.dto.author.AuthorDTO;
import com.diandi.dto.author.AuthorStatisticsDTO;
import com.diandi.entity.article.Article;
import com.diandi.entity.author.AuthorAccount;
import com.diandi.entity.author.AuthorBasic;
import com.diandi.entity.author.AuthorImage;
import com.diandi.manager.DataFillingManager;
import com.diandi.manager.StringConstructorManager;
import com.diandi.service.author.AuthorStatisticsService;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.enums.ArticleStatusEnum;
import com.diandi.util.enums.AuthorImageCategoryEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2018/1/17.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorStatisticsServiceImpl implements AuthorStatisticsService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleLikeDao likeDao;

    @Autowired
    private AuthorLinkDao linkDao;

    @Autowired
    private ArticleCategoryDao categoryDao;



    @Autowired
    private ArticleCollectDao collectDao;

    @Autowired
    private ArticleStatisticsDao statisticsDao;

    @Autowired
    private DataFillingManager dataFillingManager;

    @Autowired
    private StringConstructorManager stringConstructorManager;

    @Autowired
    private AuthorAccountDao accountDao;

    @Autowired
    private AuthorImageDao imageDao;

    @Autowired
    private AuthorBasicDao basicDao;
    @Override
    public ResultBean<AuthorStatisticsDTO> getAuthorStatistics(int authorId) {

        int articleCount = articleDao.countArticleByAuthorId(authorId, ArticleStatusEnum.PUBLIC.getCode());

        List<Article> articles = articleDao.listAllWordCountByAuthorId(authorId, ArticleStatusEnum.PUBLIC.getCode());
        int wordCountSum = articles.stream().mapToInt(Article::getWordCount).sum();
        int likeCount = articles.stream().mapToInt(Article::getArticleId).map(statisticsDao::getLikeCount).sum();

        int likeGiveCount = likeDao.countLikeByLikerId(authorId);

        int categoryCount = categoryDao.countByAuthorId(authorId);

//        int labelCount = labelDao.countByAuthorId(authorId);

        int collectCount = collectDao.countByCollectorId(authorId);
        int collectedCount = articles.stream().mapToInt(Article::getArticleId).map(statisticsDao::getCollectCount).sum();

//        int linkCount = linkDao.countLinkByAuthorId(authorId);

        AuthorStatisticsDTO dto = dataFillingManager.authorStatisticToDTO(articleCount, wordCountSum, likeCount, likeGiveCount,
                categoryCount,  collectCount, collectedCount);

        return new ResultBean<>(dto);
    }

    // 获得作者dto
    @Override
    public AuthorDTO[] listAuthorDTO(int... ids) {
        if (CollectionUtils.isEmpty(ids)) return null;

        AuthorDTO[] dtos = new AuthorDTO[ids.length];
        int c = 0;
        for (int id : ids) {
            AuthorAccount account = accountDao.getAccountById(id);
            AuthorBasic basicInfoByAuthorId = basicDao.getBasicInfoByAuthorId(id);
            AuthorImage headImage = null;
            Integer avatarId = basicInfoByAuthorId.getProfileId();
            if (avatarId != null)
                headImage = imageDao.getImageById(avatarId);

            if (headImage != null)
                headImage.setPath(stringConstructorManager.constructImageUrl(headImage, AuthorImageCategoryEnum.DEFAULT_AUTHOR_PROFILE));

            // 设置默认头像
            if (headImage == null) {
                headImage = new AuthorImage();
                headImage.setAuthorId(id);
                headImage.setImageCategory(AuthorImageCategoryEnum.PUBLIC.getCode());
                headImage.setImageId(0);
                headImage.setPath(stringConstructorManager.constructImageUrl(headImage, AuthorImageCategoryEnum.DEFAULT_AUTHOR_PROFILE));
            }

            AuthorDTO dto = dataFillingManager.authorAccountToDTO(account, basicInfoByAuthorId, headImage);
            dtos[c++] = dto;
        }

        return Arrays.copyOf(dtos, c);
    }


}
