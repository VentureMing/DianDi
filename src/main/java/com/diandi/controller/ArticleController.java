package com.diandi.controller;


import com.diandi.dto.article.ArticleListItemDTO;
import com.diandi.dto.article.ArticleMainContentDTO;
import com.diandi.manager.properties.WebSiteProperties;
import com.diandi.service.reader.ArticleBrowseService;
import com.diandi.service.reader.ArticleRetrievalService;
import com.diandi.util.common.*;
import com.diandi.util.enums.ArticleStatusEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 2018/2/4.
 * <p>
 *
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseArticleController {

    @Autowired
    private ArticleRetrievalService retrievalService;

    @Autowired
    private ArticleBrowseService articleBrowseService;

    @Autowired
    protected WebSiteProperties webSiteProperties;

    /**
     * 检索文章列表(首页)
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<List<ArticleListItemDTO>> list(HttpServletRequest request,
                                                     @RequestParam("authorId") Integer authorId,
                                                     @RequestParam(value = "kword", required = false) String keyWord,
                                                     @RequestParam(value = "sort", required = false) String sort,
                                                     @RequestParam(value = "order", required = false) String order) {
        handleAccountCheck(request, authorId);

        //检查数据合法性
        String sor = StringUtils.isEmpty_(sort) ? Rule.VIEW_COUNT.name() : sort.toUpperCase();
        String ord = StringUtils.isEmpty_(order) ? Order.DESC.name() : order.toUpperCase();
        handleSortRuleCheck(request, sor, ord);


        //执行数据查询
        ArticleSortRule rule = new ArticleSortRule(Rule.valueOf(sor), Order.valueOf(ord));
        //用于大量数据扩展
//        int os = offset == null || offset < 0 ? 0 : offset;
//        int rs = rows == null || rows < 0 ? readerProperties.getRequestAuthorArticleListCount() : rows;

        ResultBean<List<ArticleListItemDTO>> listResultBean = retrievalService.listFilterAll(keyWord, authorId, rule, ArticleStatusEnum.PUBLIC);

        if (listResultBean == null) handlerEmptyResult(request);

        return listResultBean;
    }






    /**
     * 获得文章主体内容
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public ResultBean<ArticleMainContentDTO> get(HttpServletRequest request,
                                                 @PathVariable Integer articleId) {
        handleArticleExistCheck(request, articleId);

        ResultBean<ArticleMainContentDTO> mainContent = articleBrowseService.getArticleMainContent(articleId);
        if (mainContent == null) handlerEmptyResult(request);

        return mainContent;
    }

    // 检查类别
    private void handleCategoryCheck(HttpServletRequest request, int authorId, int[] cids) {

        if (!CollectionUtils.isEmpty(cids)) {
            for (int id : cids) {
                if (!authorValidateManager.checkAuthorArticleCategoryExist(authorId, id))
                    throw exceptionManager.getParameterIllegalException(new RequestContext(request));
            }
        }


    }

    // 检查排序规则
    private void handleSortRuleCheck(HttpServletRequest request, String sort, String order) {

        if (sort != null && !Rule.contains(sort)) {
            throw exceptionManager.getArticleSortRuleUndefinedException(new RequestContext(request));
        }

        if (order != null && !Order.contains(order)) {
            throw exceptionManager.getArticleSortOrderUndefinedException(new RequestContext(request));
        }
    }
}
