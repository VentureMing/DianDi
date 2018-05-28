package com.diandi.controller;

import com.diandi.dto.author.AuthorStatisticsDTO;
import com.diandi.entity.article.Article;
import com.diandi.manager.AuthorValidateManager;
import com.diandi.service.author.AuthorArticleService;
import com.diandi.service.author.AuthorStatisticsService;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.ArticleStatusEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/2/13.
 *
 * @author DuanJiaNing
 */
@Controller
@RequestMapping("/edit_article")
public class EditArticlePageController {

    @Autowired
    private AuthorValidateManager authorValidateManager;

    @Autowired
    private AuthorArticleService articleService;

    @Autowired
    private AuthorStatisticsService statisticsService;

    @RequestMapping
    public ModelAndView mainPage(HttpServletRequest request,
                                 @RequestParam(value = "authorId", required = false) Integer authorId,
                                 @RequestParam(value = "articleId", required = false) Integer articleId) {
        ModelAndView mv = new ModelAndView();

        if (authorId == null || !authorValidateManager.checkAuthorSignIn(request, authorId)) {
            return new ModelAndView("redirect:/login");
        } else {
            if (articleId != null) {
                ResultBean<Article> article = articleService.getArticle(authorId, articleId);
                Article data = article.getData();
                mv.addObject("articleId", articleId);
                mv.addObject("categoryId", "");
                mv.addObject("articleTitle", data.getArticleTitle());
                mv.addObject("articleSummary", data.getSummary());
                if (data.getArticleState().equals(ArticleStatusEnum.PRIVATE.getCode())) {
                    mv.addObject("articleIsPrivate", true);
                }
                mv.addObject("articleContentMd", StringUtils.stringToUnicode(data.getArticleContentMd()));
            }

            ResultBean<AuthorStatisticsDTO> authorStatistics = statisticsService.getAuthorStatistics(authorId);
            mv.addObject("loginAuthorStatistics", authorStatistics.getData());

            mv.setViewName("/author/edit");
        }

        return mv;
    }

}
