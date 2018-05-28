package com.diandi.service.impl.reader;


import com.diandi.dao.article.ArticleCategoryDao;
import com.diandi.dao.author.AuthorBasicDao;
import com.diandi.dto.article.ArticleListItemDTO;
import com.diandi.entity.article.Article;
import com.diandi.entity.article.ArticleCategory;
import com.diandi.entity.article.ArticleStatistics;
import com.diandi.entity.author.AuthorBasic;
import com.diandi.manager.DataFillingManager;
import com.diandi.manager.properties.DBProperties;
import com.diandi.service.impl.common.ArticleFilterAbstract;
import com.diandi.service.reader.ArticleRetrievalService;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017/12/19.
 * 读者检索博文
 *
 * @author DuanJiaNing
 */
@Service
public class ArticleRetrievalServiceImpl extends ArticleFilterAbstract<ResultBean<List<ArticleListItemDTO>>> implements
        ArticleRetrievalService {

    @Autowired
    private ArticleCategoryDao categoryDao;

    @Autowired
    private AuthorBasicDao authorBasicDao;

    @Autowired
    private DataFillingManager dataFillingManager;

    @Autowired
    private DBProperties dbProperties;

    @Override
    protected ResultBean<List<ArticleListItemDTO>> constructResult(Map<Integer, Article> articleHashMap,
                                                                List<ArticleStatistics> statistics,
                                                                Map<Integer, String> articleImgs) {

        // 重组结果
        List<ArticleListItemDTO> result = new ArrayList<>();
        String ch = dbProperties.getStringFiledSplitCharacterForNumber();

        for (ArticleStatistics ss : statistics) {
            Integer articleId = ss.getArticleId();
            Article article = articleHashMap.get(articleId);

            // category
            int[] cids = {};
            List<ArticleCategory> categories = null;
            if (!CollectionUtils.isEmpty(cids)) {
                categories = categoryDao.listCategoryByIds(cids);
            }


            //image
            String articleImg = articleImgs.get(articleId);
            //nickName

           String nickName= authorBasicDao.getBasicInfoByAuthorId(article.getAuthorId()).getNickName();

            ArticleListItemDTO dto = dataFillingManager.articleListItemToDTO(ss,
                    CollectionUtils.isEmpty(categories) ? null : categories.toArray(new ArticleCategory[categories.size()]),
                    article, articleImg,nickName);

            result.add(dto);
        }

        return new ResultBean<>(result);

    }
}
