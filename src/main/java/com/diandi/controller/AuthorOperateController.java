package com.diandi.controller;


import com.diandi.service.reader.ArticleOperateService;
import com.diandi.util.common.StringUtils;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2017/12/25.
 * 读者对文章可以进行的操作
 * <p>
 * 1 分享文章
 * 2 收藏文章
 * 3 投诉文章
 * 4 喜欢文章
 * 5 取消收藏
 * 6 取消喜欢
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/author/{authorId}/{articleId}")
public class AuthorOperateController extends BaseAuthorController {

    @Autowired
    private ArticleOperateService operateService;

    /**
     * 分享文章
     */
    @RequestMapping(value = "/operate=share", method = RequestMethod.POST)
    public ResultBean shareArticle(HttpServletRequest request,
                                @PathVariable Integer articleId,
                                @PathVariable Integer authorId) {
        handleAuthorSignInCheck(request, authorId);
        handleArticleExistCheck(request, articleId);

        //执行
        int count = operateService.insertShare(articleId, authorId);

        return new ResultBean<>(count);
    }

    /**
     * 收藏文章
     */
    @RequestMapping(value = "/operate=collect", method = RequestMethod.POST)
    public ResultBean collectArticle(HttpServletRequest request,
                                  @PathVariable Integer articleId,
                                  @PathVariable Integer authorId,
                                  @RequestParam(value = "reason", required = false) String reason) {

        handleAuthorSignInCheck(request, authorId);
        handleArticleExistCheck(request, articleId);

        // 如果文章属于当前作者，收藏失败d
        if (articleValidateManager.isCreatorOfArticle(authorId, articleId)) {
            handlerOperateFail(request);
        }

        //执行

        int id = operateService.insertCollect(articleId, authorId);
        if (id <= 0) handlerOperateFail(request);
        return new ResultBean<>(id);
    }

    /**
     * 投诉文章
     */
    @RequestMapping(value = "/operate=complain", method = RequestMethod.POST)
    public ResultBean complainArticle(HttpServletRequest request,
                                   @PathVariable Integer articleId,
                                   @PathVariable Integer authorId,
                                   @RequestParam("content") String content) {
        if (StringUtils.isEmpty_(content))
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));

        handleAuthorSignInCheck(request, authorId);
        handleArticleExistCheck(request, articleId);

        //执行
        int id = operateService.insertComplain(articleId, authorId, content);
        if (id <= 0) handlerOperateFail(request);

        return new ResultBean<>(id);
    }

    /**
     * 喜欢文章
     */
    @RequestMapping(value = "/operate=like", method = RequestMethod.POST)
    public ResultBean likeArticle(HttpServletRequest request,
                               @PathVariable Integer articleId,
                               @PathVariable Integer authorId) {

        handleAuthorSignInCheck(request, authorId);
        handleArticleExistCheck(request, articleId);

        //执行
        int count = operateService.insertLike(articleId, authorId);

        return new ResultBean<>(count);
    }

    /**
     * 取消收藏
     */
    @RequestMapping(value = "/operate=collect", method = RequestMethod.DELETE)
    public ResultBean removeCollect(HttpServletRequest request,
                                    @PathVariable Integer articleId,
                                    @PathVariable Integer authorId) {

        handleAuthorSignInCheck(request, authorId);
        handleArticleExistCheck(request, articleId);

        //执行
        boolean result = operateService.deleteCollect(authorId, articleId);
        if (!result) handlerOperateFail(request);

        return new ResultBean<>("");

    }

    /**
     * 取消喜欢
     */
    @RequestMapping(value = "/operate=like", method = RequestMethod.DELETE)
    public ResultBean removeLike(HttpServletRequest request,
                                 @PathVariable Integer articleId,
                                 @PathVariable Integer authorId) {
        handleAuthorSignInCheck(request, authorId);
        handleArticleExistCheck(request, articleId);

        //执行
        boolean result = operateService.deleteLike(authorId, articleId);
        if (!result) handlerOperateFail(request);

        return new ResultBean<>("");
    }

}
