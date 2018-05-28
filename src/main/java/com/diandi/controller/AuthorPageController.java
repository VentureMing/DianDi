package com.diandi.controller;


import com.diandi.dto.article.ArticleListItemDTO;
import com.diandi.entity.author.AuthorAccount;
import com.diandi.exception.api.author.UnknownAuthorException;
import com.diandi.manager.AuthorSessionManager;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.service.author.*;
import com.diandi.service.reader.ArticleRetrievalService;
import com.diandi.util.common.ArticleSortRule;
import com.diandi.util.common.Order;
import com.diandi.util.common.Rule;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.ArticleStatusEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 2018/2/5.
 * 作者主页档案--------------------------------需要修改
 *
 * @author DuanJiaNing
 */
@Controller
@RequestMapping("/home")
public class AuthorPageController extends BaseArticleController {

    @Autowired
    private AuthorAccountService accountService;

    @Autowired
    private AuthorBasicService authorBasicService;

    @Autowired
    private AuthorStatisticsService statisticsService;

    @Autowired
    private AuthorImageService authorImageService;

    @Autowired
    private AuthorProperties authorProperties;

    @Autowired
    private AuthorSessionManager sessionManager;

    @Autowired
    private AuthorSettingService settingService;

    @Autowired
    private ArticleRetrievalService retrievalService;




    @RequestMapping(value = "/articles",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<List<ArticleListItemDTO>> homePage(HttpServletRequest request,
                                                         @RequestParam("authorId") int authorId) {


        //默认根据时间降序排列文章
        String sort="release_date";
        String order="desc";
        String sor = StringUtils.isEmpty_(sort) ? Rule.VIEW_COUNT.name() : sort.toUpperCase();
        String ord = StringUtils.isEmpty_(order) ? Order.DESC.name() : order.toUpperCase();

        handleSortRuleCheck(request, sor, ord);
        //执行数据查询
        ArticleSortRule rule = new ArticleSortRule(Rule.valueOf(sor), Order.valueOf(ord));

        //作者个人文章列表
        ResultBean<List<ArticleListItemDTO>> listResultBean = retrievalService.listFilterOnlyByAuthorId( authorId, rule, ArticleStatusEnum.PUBLIC);

        if (listResultBean == null) handlerEmptyResult(request);

        return listResultBean;
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
