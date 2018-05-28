package com.diandi.controller;



import com.diandi.dto.author.AuthorBriefDTO;
import com.diandi.manager.properties.WebSiteProperties;
import com.diandi.service.website.WebSiteStatisticsService;
import com.diandi.util.common.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: 登录处理器
 * @date 2018/4/6 11:19
 */
@Controller
@RequestMapping("login")
public class LoginPageController {

    @Autowired
    private WebSiteStatisticsService webSiteStatisticsService;

    @Autowired
    private WebSiteProperties webSiteProperties;

    @RequestMapping
    public ModelAndView loginPage(@RequestParam(value = "activeAuthorCount", required = false) Integer activeCount) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("author/login");

        if (activeCount == null || activeCount < 0)
            activeCount = webSiteProperties.getWebSiteActiveAuthorCount();
        //获取活跃作者
//        List<AuthorBriefDTO> dtos = webSiteStatisticsService.listActiveAuthor(activeCount);
//        if (!CollectionUtils.isEmpty(dtos)) {
//            mv.addObject("briefs", dtos);
//        }

        return mv;
    }
}
