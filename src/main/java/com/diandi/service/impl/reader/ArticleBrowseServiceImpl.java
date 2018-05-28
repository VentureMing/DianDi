package com.diandi.service.impl.reader;


import com.diandi.dao.article.ArticleCategoryDao;
import com.diandi.dao.article.ArticleCommentDao;
import com.diandi.dao.article.ArticleDao;
import com.diandi.dao.author.AuthorAccountDao;
import com.diandi.dao.author.AuthorBasicDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.dto.article.ArticleCommentDTO;
import com.diandi.dto.article.ArticleMainContentDTO;
import com.diandi.dto.author.AuthorDTO;
import com.diandi.entity.article.Article;
import com.diandi.entity.article.ArticleCategory;
import com.diandi.entity.article.ArticleComment;
import com.diandi.entity.author.AuthorAccount;
import com.diandi.entity.author.AuthorBasic;
import com.diandi.entity.author.AuthorImage;
import com.diandi.manager.DataFillingManager;
import com.diandi.manager.StringConstructorManager;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.manager.properties.DBProperties;
import com.diandi.service.reader.ArticleBrowseService;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.ArticleCommentStatusEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.diandi.util.enums.AuthorImageCategoryEnum.DEFAULT_AUTHOR_PROFILE;

/**
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
@Service
public class ArticleBrowseServiceImpl implements ArticleBrowseService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleCategoryDao categoryDao;



    @Autowired
    private DBProperties dbProperties;

    @Autowired
    private DataFillingManager dataFillingManager;

    @Autowired
    private StringConstructorManager constructorManager;

    @Autowired
    private AuthorProperties authorProperties;

    @Autowired
    private ArticleCommentDao commentDao;

    @Autowired
    private AuthorAccountDao accountDao;

    @Autowired
    private AuthorImageDao imageDao;

    @Autowired
    private AuthorBasicDao basicDao;

    @Override
    public ResultBean<ArticleMainContentDTO> getArticleMainContent(int articleId) {

        //查询数据
        Article article = articleDao.getArticleById(articleId);
        if (article == null) return null;
        String ch = dbProperties.getStringFiledSplitCharacterForNumber();
        int[] cids = StringUtils.intStringDistinctToArray("", ch);
        List<ArticleCategory> categories = cids == null ? null : categoryDao.listCategoryByIds(cids);


        //填充数据
        String sc = dbProperties.getStringFiledSplitCharacterForString();
        ArticleMainContentDTO dto = dataFillingManager.articleMainContentToDTO(article,   sc);

        return new ResultBean<>(dto);
    }

    @Override
    public ResultBean<List<ArticleCommentDTO>> listArticleComment(int articleId, int offset, int rows) {

        List<ArticleCommentDTO> result = new ArrayList<>();

        List<ArticleComment> comments = commentDao.listCommentByArticleId(articleId, offset, rows,
                ArticleCommentStatusEnum.RIGHTFUL.getCode());
        for (ArticleComment comment : comments) {

            //评论者数据
            int sid = comment.getCommentatorId();
            AuthorAccount smAccount = accountDao.getAccountById(sid);
            AuthorBasic basicInfo = getBasicInfo(sid);
            AuthorDTO smDTO = dataFillingManager.authorAccountToDTO(smAccount, basicInfo,
                    getHeadImage(basicInfo.getProfileId()));

            ArticleCommentDTO dto = dataFillingManager.articleCommentToDTO(comment, smDTO);
            result.add(dto);
        }

        return CollectionUtils.isEmpty(result) ? null : new ResultBean<>(result);
    }

    private AuthorImage getHeadImage(Integer id) {
        AuthorImage headImage;
        if (id != null) {
            headImage = imageDao.getImageById(id);
        } else {
            headImage = imageDao.getAuthorUniqueImage(authorProperties.getImageManagerAuthorId(),
                    DEFAULT_AUTHOR_PROFILE.getCode());
        }

        if (headImage != null) {
            headImage.setPath(constructorManager.constructImageUrl(headImage, DEFAULT_AUTHOR_PROFILE));
        }

        return headImage;
    }


    //获得博主资料
    private AuthorBasic getBasicInfo(Integer authorId) {
        if (authorId == null) return null;
        return basicDao.getBasicInfoByAuthorId(authorId);
    }
}
