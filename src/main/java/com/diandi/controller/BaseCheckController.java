package com.diandi.controller;


import com.diandi.manager.ArticleValidateManager;
import com.diandi.manager.AuthorValidateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/2/4.
 *
 * @author DuanJiaNing
 */
public class BaseCheckController extends RestController {

    @Autowired
    protected ArticleValidateManager articleValidateManager;

    @Autowired
    protected AuthorValidateManager authorValidateManager;

    /**
     * 检查博主是否存在，不存在直接抛出异常
     */
    protected void handleAccountCheck(HttpServletRequest request, Integer authorId) {
        if (authorId == null || authorId <= 0 || !authorValidateManager.checkAccountExist(authorId)) {
            throw exceptionManager.getUnknownAuthorException(new RequestContext(request));
        }
    }

    /**
     * 先检查博主是否存在，后检查检查博主是否登录
     * <p>
     * 在API中，一些获取数据的操作是不需要博主登录的，但类似于修改，删除，新增以及关键数据的操纵需要验证身份。
     * <p>
     * 如果验证不通过将直接抛出运行时异常。
     *
     * @param authorId 博主id
     */
    protected void handleAuthorSignInCheck(HttpServletRequest request, Integer authorId) {
        handleAccountCheck(request, authorId);

        // 检查当前登录否
        if (!authorValidateManager.checkAuthorSignIn(request, authorId))
            throw exceptionManager.getAuthorNotLoggedInException(new RequestContext(request));
    }

    /**
     * 检查博文是否存在,不存在直接抛出异常
     */
    protected void handleArticleExistCheck(HttpServletRequest request, Integer articleId) {
        if (articleId == null || articleId <= 0 || !articleValidateManager.checkArticleExist(articleId)) {
            throw exceptionManager.getUnknownArticleException(new RequestContext(request));
        }
    }

}
