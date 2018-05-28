package com.diandi.manager;

import com.diandi.dto.article.*;
import com.diandi.dto.article.ArticleListItemDTO;
import com.diandi.dto.author.*;
import com.diandi.entity.article.*;
import com.diandi.entity.author.AuthorAccount;
import com.diandi.entity.author.AuthorBasic;
import com.diandi.entity.author.AuthorImage;
import com.diandi.entity.author.AuthorLink;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2017/12/25.
 * entity 数据转换为 dto 数据
 *
 * @author DuanJiaNing
 */
@Component
public class DataFillingManager {

    public ArticleStatisticsCountDTO articleStatisticsCountToDTO(ArticleStatistics statistics) {

        ArticleStatisticsCountDTO dto = new ArticleStatisticsCountDTO();
        dto.setAdmireCount(statistics.getAdmireCount());
        dto.setArticleId(statistics.getArticleId());
        dto.setCollectCount(statistics.getCollectCount());
        dto.setCommentCount(statistics.getCommentCount());
        dto.setComplainCount(statistics.getComplainCount());
        dto.setId(statistics.getId());
        dto.setLikeCount(statistics.getLikeCount());
        dto.setReplyCommentCount(statistics.getReplyCommentCount());
        dto.setShareCount(statistics.getShareCount());
        dto.setViewCount(statistics.getViewCount());
        return dto;
    }

    public ArticleCommentDTO articleCommentToDTO(ArticleComment comment, AuthorDTO commentator) {
        ArticleCommentDTO dto = new ArticleCommentDTO();
        dto.setArticleId(comment.getArticleId());
        dto.setContent(comment.getContent());
        dto.setId(comment.getCommentId());
        dto.setReleaseDate(comment.getReleaseDate());
        dto.setState(comment.getState());
        dto.setCommentator(commentator);
        return dto;
    }

    public AuthorDTO authorAccountToDTO(AuthorAccount account, AuthorBasic authorBasic, AuthorImage profileImage) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(account.getAuthorId());
        dto.setRegisterDate(account.getRegisterDate());
        dto.setAuthorEmail(account.getAuthorEmail());
        dto.setProfileImage(profileImage);
        dto.setAuthorBasic(authorBasic);
        return dto;
    }

    public ArticleListItemDTO articleListItemToDTO(ArticleStatistics statistics, ArticleCategory[] categories,
                                                   Article article, String articleImg,String nickName) {
        ArticleListItemDTO dto = new ArticleListItemDTO();

        dto.setNickName(nickName);
        dto.setCollectCount(statistics.getCollectCount());
        dto.setCommentCount(statistics.getCommentCount());
        dto.setArticleId(article.getArticleId());
        dto.setLikeCount(statistics.getLikeCount());
        dto.setReleaseDate(article.getReleaseDate());
        dto.setSummary(article.getSummary());
        dto.setTitle(article.getArticleTitle());
        dto.setViewCount(statistics.getViewCount());
        dto.setImage(articleImg);
        return dto;

    }

    public AuthorLinkDTO authorLinkToDTO(AuthorLink link, AuthorImage icon) {
        AuthorLinkDTO dto = new AuthorLinkDTO();
        dto.setDercribe(link.getDescribe());
        dto.setAuthorId(link.getAuthorId());
        dto.setIcon(icon);
        dto.setId(link.getId());
        dto.setTitle(link.getTitle());
        dto.setUrl(link.getUrl());
        return dto;
    }

    public ArticleMainContentDTO articleMainContentToDTO(Article article, String splitChar) {
        ArticleMainContentDTO dto = new ArticleMainContentDTO();
        dto.setAuthorId(article.getAuthorId());
        dto.setArticleId(article.getArticleId());
        dto.setKeyWords(StringUtils.stringArrayToArray(article.getKeyWords(), splitChar));
        dto.setNearestModifyDate(article.getNearestModifyDate());
        dto.setReleaseDate(article.getReleaseDate());
        dto.setStatus(article.getArticleState());
        dto.setSummary(article.getSummary());
        dto.setTitle(article.getArticleTitle());
        dto.setWordCount(article.getWordCount());
        dto.setContent(article.getArticleContentHtml());

        return dto;
    }


    public AuthorCategoryDTO articleCategoryToDTO(ArticleCategory category, AuthorImage image, int count) {
        AuthorCategoryDTO dto = new AuthorCategoryDTO();
        dto.setAuthorId(category.getAuthorId());
        dto.setCreateDate(category.getCreateDate());
        dto.setDescribe(category.getDescribe());
        dto.setImage(image);
        dto.setId(category.getCategoryId());
        dto.setTitle(category.getTitle());
        dto.setCount(count);
        return dto;
    }

    public com.diandi.dto.author.ArticleListItemDTO authorArticleListItemToDTO(Article article, ArticleStatistics statistics,
                                                                                List<ArticleCategory> categories) {
        com.diandi.dto.author.ArticleListItemDTO dto = new com.diandi.dto.author.ArticleListItemDTO();
        dto.setCategories(CollectionUtils.isEmpty(categories) ? null : categories.toArray(new ArticleCategory[categories.size()]));
        dto.setCollectCount(statistics.getCollectCount());
        dto.setCommentCount(statistics.getCommentCount());
        dto.setComplainCount(statistics.getComplainCount());
        dto.setArticleId(article.getArticleId());
        dto.setLikeCount(statistics.getLikeCount());
        dto.setNearestModifyDate(article.getNearestModifyDate());
        dto.setReleaseDate(article.getReleaseDate());
        dto.setState(article.getArticleState());
        dto.setSummary(article.getSummary());
        dto.setTitle(article.getArticleTitle());
        dto.setViewCount(statistics.getViewCount());
        dto.setWordCount(article.getWordCount());
        return dto;
    }

    public ArticleStatisticsDTO articleStatisticsToDTO(Article article, ArticleStatistics statistics, ArticleCategory[] categories,
                                                    AuthorDTO[] likes, AuthorDTO[] collects,
                                                    AuthorDTO[] commenter, String splitChar) {
        ArticleStatisticsDTO dto = new ArticleStatisticsDTO();
        dto.setCategories(categories);
        dto.setCollects(collects);
        dto.setCommenter(commenter);
        dto.setId(statistics.getId());
        dto.setKeyWords(StringUtils.stringArrayToArray(article.getKeyWords(), splitChar));
        dto.setLikes(likes);
        dto.setNearestModifyDate(article.getNearestModifyDate());
        dto.setReleaseDate(article.getReleaseDate());
        dto.setState(article.getArticleState());
        dto.setStatistics(statistics);
        dto.setSummary(article.getSummary());
        dto.setTitle(article.getArticleTitle());
        dto.setWordCount(article.getWordCount());

        return dto;
    }

    public AuthorStatisticsDTO authorStatisticToDTO(int articleCount, int wordCount, int likeCount, int likedCount,
                                                     int categoryCount, int collectCount,
                                                     int collectedCount) {
        AuthorStatisticsDTO dto = new AuthorStatisticsDTO();
        dto.setArticleCount(articleCount);
        dto.setWordCount(wordCount);
        dto.setLikeCount(likeCount);
        dto.setLikedCount(likedCount);
        dto.setCategoryCount(categoryCount);
        dto.setCollectCount(collectCount);
        dto.setCollectedCount(collectedCount);

        return dto;
    }

    public FavouriteArticleListItemDTO collectArticleListItemToDTO(int authorId, ArticleCollect collect, ArticleListItemDTO article, AuthorDTO author) {
        FavouriteArticleListItemDTO dto = new FavouriteArticleListItemDTO();
        dto.setAuthor(author);
        dto.setArticle(article);
        dto.setAuthorId(authorId);
        dto.setDate(collect.getCollectDate());
        dto.setId(collect.getCollectId());
        return dto;
    }

    public FavouriteArticleListItemDTO likeArticleListItemToDTO(int authorId, ArticleLike like, ArticleListItemDTO article, AuthorDTO liker) {
        FavouriteArticleListItemDTO dto = new FavouriteArticleListItemDTO();
        dto.setAuthor(liker);
        dto.setArticle(article);
        dto.setAuthorId(authorId);
        dto.setDate(like.getLikeDate());
        dto.setId(like.getId());
        return dto;
    }

    public AuthorBriefDTO authorToBasic(AuthorDTO authorDTO, AuthorStatisticsDTO statisticsDTO) {
        AuthorBriefDTO dto = new AuthorBriefDTO();
        dto.setAuthorDTO(authorDTO);
        dto.setAuthorStatisticsDTO(statisticsDTO);
        return dto;
    }
}
