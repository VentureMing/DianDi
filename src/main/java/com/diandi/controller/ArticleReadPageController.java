package com.diandi.controller;


import com.diandi.dto.article.ArticleMainContentDTO;
import com.diandi.dto.article.ArticleStatisticsCountDTO;
import com.diandi.dto.author.AuthorStatisticsDTO;
import com.diandi.entity.article.Article;
import com.diandi.manager.AuthorSessionManager;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.service.author.*;
import com.diandi.service.common.ArticleStatisticsService;
import com.diandi.service.reader.ArticleBrowseService;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/3/7.
 *
 * @author DuanJiaNing
 */
@Controller
@RequestMapping("/read/{articleId}")
public class ArticleReadPageController {


    @Autowired
    private AuthorArticleService articleService;

    @Autowired
    private ArticleStatisticsService statisticsService;

    @Autowired
    private AuthorStatisticsService authorStatisticsService;


    @Autowired
    private AuthorSessionManager sessionManager;
    @Autowired
    private AuthorProperties authorProperties;
    @Autowired
    private ArticleBrowseService articleBrowseService;

    @Autowired
    private AuthorLikeArticleService likeService;

    @Autowired
    private AuthorCollectArticleService collectArticleService;

    @RequestMapping
    public ModelAndView page(HttpServletRequest request,
                             @PathVariable int articleId) {
        ModelAndView mv = new ModelAndView();

//        // 文章作者作者账户
//        AuthorAccount account = accountService.getAccountById(authorId);
//
//        if (account == null) {
//            request.setAttribute("code", UnknownAuthorException.code);
//            mv.setViewName("/author/register");
//            return mv;
//        }
//
//        int articleId = articleService.getArticleId(account.getAuthorId(), articleName);
//        if (articleId == -1) {
//            mv.setViewName("error/error");
//            mv.addObject("code", 5);
//            mv.addObject(authorProperties.getSessionNameOfErrorMsg(), "文章不存在！");
//            return mv;
//        }

        ResultBean<Article> article=articleService.getArticleByArticleId(articleId);
        // 文章浏览次数自增1
        statisticsService.updateArticleViewCountPlus(articleId);

        ResultBean<ArticleMainContentDTO> mainContent = articleBrowseService.getArticleMainContent(articleId);
        ResultBean<ArticleStatisticsCountDTO> statistics = statisticsService.getArticleStatisticsCount(articleId);

        mv.addObject("articleOwnerAuthorId", article.getData().getAuthorId());
        mv.addObject("main", mainContent.getData());
        mv.addObject("stat", statistics.getData());


        // 登陆作者 articleId
        int loginAuthorId = sessionManager.getLoginAuthorId(request);

        ResultBean<AuthorStatisticsDTO> loginAuthorStat = authorStatisticsService.getAuthorStatistics(loginAuthorId);
        mv.addObject("loginAuthorStat", loginAuthorStat.getData());

        if (loginAuthorId != 1) {
            if (likeService.getLikeState(loginAuthorId, articleId))
                mv.addObject("likeState", true);
            if (collectArticleService.getCollectState(loginAuthorId, articleId))
                mv.addObject("collectState", true);
        }

        mv.setViewName("author/read_article");
        return mv;
    }

}
