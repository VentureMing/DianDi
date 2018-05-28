package com.diandi.manager;

import com.diandi.dao.article.ArticleDao;
import com.diandi.dao.article.ArticleStatisticsDao;
import com.diandi.entity.article.Article;
import com.diandi.entity.article.ArticleStatistics;
import com.diandi.service.author.AuthorArticleService;
import com.diandi.util.enums.ArticleStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2017/12/26.
 * 博文相关信息验证
 *
 * @author DuanJiaNing
 */
@Component
public class ArticleValidateManager {

    @Autowired
    private AuthorArticleService authorArticleService;

    @Autowired
    private ArticleDao articleDao;



    @Autowired
    private ArticleStatisticsDao statisticsDao;

    /**
     * 检查博文是否存在
     *
     * @param articleId 博文id
     * @return 博文存在返回true
     */
    public boolean checkArticleExist(int articleId) {
        return articleId > 0 && authorArticleService.getArticleForCheckExist(articleId);
    }

//    /**
//     * 检查标签是否存在
//     *
//     * @param labelId 标签id
//     * @return 存在返回true
//     */
//    public boolean checkLabelsExist(int labelId) {
//        return labelDao.getLabel(labelId) != null;
//    }

    /**
     * 检查博主是否为当前博文的创作者
     *
     * @param authorId 博主id
     * @param articleId    博文id
     * @return 是返回true
     */
    public boolean isCreatorOfArticle(int authorId, int articleId) {
        Article article = articleDao.getArticleById(articleId);
        return article != null && article.getAuthorId().equals(authorId);
    }

    /**
     * 检验博文是否合法
     *
     * @param title    博文标题
     * @param content  博文内容
     * @param summary  摘要
     * @param keyWords 关键字
     * @return 合法返回true
     */
    public boolean verifyArticle(String title, String content, String contentMd, String summary, String keyWords) {
        //TODO 文章内容校验
        return true;
    }

    /**
     * 检查目标博文状态是否允许，一般用户只允许在“公开”，“私有”，“回收站”之间切换。
     *
     * @param status 状态值
     * @return 允许返回true
     */
    public boolean isArticleStatusAllow(int status) {
        List<ArticleStatusEnum> list = Arrays.asList(ArticleStatusEnum.PUBLIC, ArticleStatusEnum.PRIVATE, ArticleStatusEnum.DELETED);
        int contain = 0;
        for (ArticleStatusEnum s : list) {
            if (s.getCode() == status) contain++;
        }

        return contain > 0;
    }

    /**
     * 检测博主是否有指定博文的统计信息记录
     *
     * @param authorId 博主id
     * @param articleId    博文id
     * @return 有返回true
     */
    public boolean isCreatorOfArticleStatistic(int authorId, int articleId) {

        if (!isCreatorOfArticle(authorId, articleId)) return false;

        ArticleStatistics statistics = statisticsDao.getStatistics(articleId);
        if (statistics == null) return false;

        return true;
    }

    /**
     * 检查博文的统计信息是否存在
     *
     * @param articleId 博文id
     * @return 存在返回true
     */
    public boolean checkArticleStatisticExist(int articleId) {
        Integer count = statisticsDao.getViewCount(articleId);
        if (count == null) return false;

        return true;
    }

}
