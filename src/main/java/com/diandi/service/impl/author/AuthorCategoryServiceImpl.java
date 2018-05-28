package com.diandi.service.impl.author;


import com.diandi.dao.article.ArticleCategoryDao;
import com.diandi.dao.article.ArticleDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.dto.author.AuthorCategoryDTO;
import com.diandi.entity.article.Article;
import com.diandi.entity.article.ArticleCategory;
import com.diandi.entity.author.AuthorImage;
import com.diandi.exception.internal.SQLException;
import com.diandi.manager.DataFillingManager;
import com.diandi.manager.ImageManager;
import com.diandi.manager.StringConstructorManager;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.manager.properties.DBProperties;
import com.diandi.service.author.AuthorCategoryService;
import com.diandi.util.common.ArrayUtils;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.ArticleStatusEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.diandi.util.enums.AuthorImageCategoryEnum.DEFAULT_AUTHOR_ARTICLE_CATEGORY_ICON;

/**
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorCategoryServiceImpl implements AuthorCategoryService {

    @Autowired
    private ArticleCategoryDao categoryDao;

    @Autowired
    private DataFillingManager fillingManager;

    @Autowired
    private DBProperties dbProperties;

    @Autowired
    private StringConstructorManager constructorManager;

    @Autowired
    private AuthorProperties authorProperties;

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private AuthorImageDao imageDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public ResultBean<List<AuthorCategoryDTO>> listArticleCategory(int authorId, int offset, int rows) {
        return null;
    }

    @Override
    public boolean updateArticleCategory(int authorId, int categoryId, int newIconId, String newTitle, String newBewrite) {
        return false;
    }

    @Override
    public int insertArticleCategory(int authorId, int iconId, String title, String desc) {
        return 0;
    }

    @Override
    public boolean deleteCategoryAndMoveArticlesTo(int authorId, int categoryId, int newCategoryId) {
        return false;
    }

    @Override
    public AuthorCategoryDTO getCategory(int authorId, int categoryId) {
        return null;
    }
    //    @Override
//    public ResultBean<List<AuthorCategoryDTO>> listArticleCategory(int authorId, int offset, int rows) {
//
//        List<ArticleCategory> categories = categoryDao.listCategoryByAuthorId(authorId, offset, rows);
//        if (CollectionUtils.isEmpty(categories)) return null;
//
//        List<AuthorCategoryDTO> result = new ArrayList<>();
//        for (ArticleCategory category : categories) {
//            result.add(getAuthorCategoryDTO(authorId, category));
//        }
//
//        return new ResultBean<>(result);
//    }
//
//    @Override
//    public boolean updateArticleCategory(int authorId, int categoryId, int newIconId, String newTitle,
//                                      String newDescribe) {
//
//        ArticleCategory category = categoryDao.getCategory(authorId, categoryId);
//        Integer oldIconId = category.getImageId();
//        if (!StringUtils.isEmpty(newTitle)) category.setTitle(newTitle);
//        if (!StringUtils.isEmpty(newDescribe)) category.setDescribe(newDescribe);
//        if (newIconId > 0) category.setImageId(newIconId);
//        category.setCategoryId(categoryId);
//        int effect = categoryDao.update(category);
//        if (effect <= 0) return false;
//
//        // 修改图片可见性，引用次数
//        imageManager.imageUpdateHandle(authorId, newIconId, oldIconId);
//
//        return true;
//    }
//
//    @Override
//    public int insertArticleCategory(int authorId, int iconId, String title, String describe) {
//
//        ArticleCategory category = new ArticleCategory();
//        category.setDescribe(describe);
//        if (iconId > 0) category.setImageId(iconId);
//        category.setAuthorId(authorId);
//        category.setTitle(title);
//        int effect = categoryDao.insert(category);
//        if (effect <= 0) return -1;
//
//        // 修改图片可见性，引用次数
//        imageManager.imageInsertHandle(authorId, iconId);
//
//        return category.getCategoryId();
//    }
//
//    @Override
//    public boolean deleteCategoryAndMoveArticlesTo(int authorId, int categoryId, int newCategoryId) {
//
//        ArticleCategory category = categoryDao.getCategory(authorId, categoryId);
//        if (category == null) return false;
//
//        // 图片引用次数--
//        Integer iconId;
//        if ((iconId = category.getImageId()) != null && imageDao.getUseCount(iconId) > 0) {
//            imageDao.updateUseCountMinus(iconId);
//        }
//
//        // 删除数据库类别记录
//        int effectDelete = categoryDao.delete(categoryId);
//        if (effectDelete <= 0) throw new SQLException();
//
//        // 修改博文类别
//        List<Article> articles = articleDao.listAllCategoryByAuthorId(authorId);
//        String sp = dbProperties.getStringFiledSplitCharacterForNumber();
//
//        // 移除类别即可
//        if (newCategoryId <= 0) {
//            articles.forEach(article -> {
//
//                int[] cids = StringUtils.intStringDistinctToArray("", sp);
//                if (CollectionUtils.intArrayContain(cids, categoryId)) {
//                    int[] ar = ArrayUtils.removeFromArray(cids, categoryId);
////                    article.setCategoryIds(StringUtils.intArrayToString(ar, sp));
//                    int effectUpdate = articleDao.update(article);
//                    if (effectUpdate <= 0) throw new SQLException();
//                }
//
//            });
//
//        } else { // 替换类别
//            articles.forEach(article -> {
//
//                int[] cids = StringUtils.intStringDistinctToArray("", sp);
//                if (CollectionUtils.intArrayContain(cids, categoryId)) {
//                    ArrayUtils.replace(cids, categoryId, newCategoryId);
////                    article.setCategoryIds(StringUtils.intArrayToString(cids, sp));
//                    int effectUpdate = articleDao.update(article);
//                    if (effectUpdate <= 0) throw new SQLException();
//                }
//            });
//
//        }
//
//        return true;
//    }
//
//    @Override
//    public AuthorCategoryDTO getCategory(int authorId, int categoryId) {
//        return getAuthorCategoryDTO(authorId, categoryDao.getCategory(authorId, categoryId));
//    }
//
//    // 获得单个类别
//    private AuthorCategoryDTO getAuthorCategoryDTO(int authorId, ArticleCategory category) {
//
//        Integer iconId = category.getImageId();
//
//        AuthorImage icon;
//        if (iconId == null) {
//            // 默认图片
//            int pictureManagerId = authorProperties.getImageManagerAuthorId();
//            icon = imageDao.getAuthorUniqueImage(pictureManagerId, DEFAULT_AUTHOR_ARTICLE_CATEGORY_ICON.getCode());
//        } else {
//            icon = imageDao.getImageById(iconId);
//        }
//
//        if (icon != null)
//            icon.setPath(constructorManager.constructImageUrl(icon, DEFAULT_AUTHOR_ARTICLE_CATEGORY_ICON));
//
//        int count = articleDao.countArticleByCategory(authorId, category.getCategoryId(), ArticleStatusEnum.PUBLIC.getCode());
//        return fillingManager.articleCategoryToDTO(category, icon, count);
//    }

}
