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
import com.diandi.service.author.AuthorCollectArticleService;
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
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorCollectArticleServiceImpl implements AuthorCollectArticleService {

    @Autowired
    private ArticleCollectDao articleCollectDao;

    @Autowired
    private ArticleStatisticsDao articleStatisticsDao;

    @Autowired
    private ArticleCategoryDao articleCategoryDao;



    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AuthorAccountDao authorAccountDao;

    @Autowired
    private AuthorBasicDao authorBasicDao;

    @Autowired
    private AuthorImageDao authorImageDao;

    @Autowired
    private DataFillingManager fillingManager;

    @Autowired
    private DBProperties dbProperties;

    @Autowired
    private StringConstructorManager constructorManager;

    @Override
    public ResultBean<List<FavouriteArticleListItemDTO>> listCollectArticle(int authorId, int categoryId, int offset, int rows, ArticleSortRule sortRule) {
        List<ArticleCollect> collects = articleCollectDao.listCollectArticle(authorId, categoryId, offset, rows);
        if (CollectionUtils.isEmpty(collects)) return null;

        //排序
        List<ArticleStatistics> temp = new ArrayList<>();
        //方便排序后的重组
        Map<Integer, ArticleCollect> articleCollectMap = new HashMap<>();
        for (ArticleCollect collect : collects) {
            int articleId = collect.getArticleId();
            ArticleStatistics statistics = articleStatisticsDao.getStatistics(articleId);
            temp.add(statistics);
            articleCollectMap.put(articleId, collect);
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




            String nickName= authorBasicDao.getBasicInfoByAuthorId(article.getAuthorId()).getNickName();
            ArticleListItemDTO listItemDTO = fillingManager.articleListItemToDTO(statistics,
                    CollectionUtils.isEmpty(categories) ? null : categories.toArray(new ArticleCategory[categories.size()]),
                    article, null,nickName);

            // AuthorDTO
            int author = article.getAuthorId();
            AuthorAccount account = authorAccountDao.getAccountById(author);
            AuthorBasic basicInfoByAuthorId = authorBasicDao.getBasicInfoByAuthorId(author);
            AuthorImage headImage = basicInfoByAuthorId.getProfileId() == null ? null :
                    authorImageDao.getImageById(basicInfoByAuthorId.getProfileId());

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
            ArticleCollect collect = articleCollectMap.get(articleId);
            FavouriteArticleListItemDTO dto = fillingManager.collectArticleListItemToDTO(authorId, collect, listItemDTO, authorDTO);
            result.add(dto);
        }

        return new ResultBean<>(result);
    }

    @Override
    public boolean updateCollect(int authorId, int articleId, int newCategory) {
        int effect = articleCollectDao.updateByUnique(authorId, articleId, null);
        return effect > 0;
    }

    @Override
    public boolean getCollectState(int authorId, int articleId) {
        ArticleCollect collect = articleCollectDao.getCollect(authorId, articleId);
        return collect != null;
    }

    @Override
    public int countByAuthorId(int authorId) {
        return articleCollectDao.countByCollectorId(authorId);
    }
}
