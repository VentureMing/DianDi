package com.diandi.service.impl.author;


import com.diandi.dao.article.ArticleCommentDao;
import com.diandi.dao.article.ArticleStatisticsDao;
import com.diandi.entity.article.ArticleComment;
import com.diandi.service.author.AuthorCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2018/3/13.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorCommentServiceImpl implements AuthorCommentService {

    @Autowired
    private ArticleStatisticsDao articleStatisticsDao;

    @Autowired
    private ArticleCommentDao articleCommentDao;

    @Override
    public int insertComment(int articleId, int commentatorId, int listenerId, int state, String content) {

        ArticleComment comment = new ArticleComment();
        comment.setArticleId(articleId);
        comment.setContent(content);
        comment.setListenerId(listenerId);
        comment.setCommentatorId(commentatorId);
        comment.setState(state);
        articleCommentDao.insert(comment);

        //博文评论次数加一
        articleStatisticsDao.updateCommentCountPlus(articleId);
        Integer id = comment.getCommentId();

        return id == null ? -1 : id;
    }

    @Override
    public boolean deleteComment(int commentId, int articleId) {

        int effect = articleCommentDao.delete(commentId);

        if (effect <= 0) return false;
        else articleStatisticsDao.updateCommentCountMinus(articleId);

        return true;
    }

}
