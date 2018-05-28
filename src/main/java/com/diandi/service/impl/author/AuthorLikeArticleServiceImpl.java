package com.diandi.service.impl.author;


import com.diandi.dao.article.*;
import com.diandi.dao.author.AuthorAccountDao;
import com.diandi.dao.author.AuthorBasicDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.dto.article.ArticleListItemDTO;
import com.diandi.dto.author.AuthorDTO;
import com.diandi.dto.author.FavouriteArticleListItemDTO;
import com.diandi.entity.article.*;
import com.diandi.entity.author.AuthorAccount;
import com.diandi.entity.author.AuthorBasic;
import com.diandi.entity.author.AuthorImage;
import com.diandi.manager.ArticleListItemComparatorFactory;
import com.diandi.manager.DataFillingManager;
import com.diandi.manager.StringConstructorManager;
import com.diandi.manager.properties.DBProperties;
import com.diandi.service.author.AuthorLikeArticleService;
import com.diandi.util.common.ArticleSortRule;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.AuthorImageCategoryEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/3/11.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorLikeArticleServiceImpl implements AuthorLikeArticleService {

    @Autowired
    private ArticleLikeDao articleLikeDao;

    @Autowired
    private ArticleStatisticsDao articleStatisticsDao;

    @Autowired
    private ArticleCategoryDao articleCategoryDao;



    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AuthorAccountDao authorAccountDao;

    @Autowired
    private AuthorBasicDao basicDao;

    @Autowired
    private AuthorImageDao imageDao;

    @Autowired
    private DataFillingManager fillingManager;

    @Autowired
    private DBProperties dbProperties;

    @Autowired
    private StringConstructorManager constructorManager;

    @Override
    public boolean getLikeState(int authorId, int articleId) {
        ArticleLike like = articleLikeDao.getLike(authorId, articleId);
        return like != null;
    }

    @Override
    public ResultBean<List<FavouriteArticleListItemDTO>> listLikeArticle(int authorId, int offset, int rows, ArticleSortRule sortRule) {
        List<ArticleLike> likes = articleLikeDao.listLikeArticle(authorId, offset, rows);
        if (CollectionUtils.isEmpty(likes)) return null;

        //排序
        List<ArticleStatistics> temp = new ArrayList<>();
        //方便排序后的重组
        Map<Integer, ArticleLike> articleLikeMap = new HashMap<>();
        for (ArticleLike like : likes) {
            int articleId = like.getArticleId();
            ArticleStatistics statistics = articleStatisticsDao.getStatistics(articleId);
            temp.add(statistics);
            articleLikeMap.put(articleId, like);
        }
        ArticleListItemComparatorFactory factory = new ArticleListItemComparatorFactory();
        temp.sort(factory.get(sortRule.getRule(), sortRule.getOrder()));

        //构造结果
        List<FavouriteArticleListItemDTO> result = new ArrayList<>();
        for (ArticleStatistics statistics : temp) {
            int articleId = statistics.getArticleId();

            // ArticleListItemDTO
            Article article = articleDao.getArticleById(articleId);
            String ch = dbProperties.getStringFiledSplitCharacterForNumber();

            // category
            int[] cids = StringUtils.intStringDistinctToArray("", ch);
            List<ArticleCategory> categories = null;
            if (!CollectionUtils.isEmpty(cids)) {
                categories = articleCategoryDao.listCategoryByIds(cids);
            }

            String nickName=basicDao.getBasicInfoByAuthorId(article.getAuthorId()).getNickName();
            ArticleListItemDTO listItemDTO = fillingManager.articleListItemToDTO(statistics,
                    CollectionUtils.isEmpty(categories) ? null : categories.toArray(new ArticleCategory[categories.size()]),

                    article, null,nickName);

            // AuthorDTO
            int author = article.getAuthorId();
            AuthorAccount account = authorAccountDao.getAccountById(author);
            AuthorBasic basicInfoByAuthorId = basicDao.getBasicInfoByAuthorId(author);
            AuthorImage headImage = basicInfoByAuthorId.getProfileId() == null ? null :
                    imageDao.getImageById(basicInfoByAuthorId.getProfileId());

            // 使使用默认的博主头像
            if (headImage == null) {
                headImage = new AuthorImage();
                headImage.setImageCategory(AuthorImageCategoryEnum.PUBLIC.getCode());
                headImage.setAuthorId(author);
                headImage.setImageId(-1);
            }

            String url = constructorManager.constructImageUrl(headImage, AuthorImageCategoryEnum.DEFAULT_AUTHOR_PROFILE);
            headImage.setPath(url);

            AuthorDTO authorDTO = fillingManager.authorAccountToDTO(account, basicInfoByAuthorId, headImage);

            // 结果
            ArticleLike like = articleLikeMap.get(articleId);
            FavouriteArticleListItemDTO dto = fillingManager.likeArticleListItemToDTO(authorId, like, listItemDTO, authorDTO);
            result.add(dto);
        }

        return new ResultBean<>(result);
    }

    @Override
    public int countByAuthorId(int authorId) {
        return articleLikeDao.countLikeByLikerId(authorId);
    }
}
