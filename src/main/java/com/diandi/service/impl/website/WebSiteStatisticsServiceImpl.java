package com.diandi.service.impl.website;


import com.diandi.dto.author.AuthorBriefDTO;
import com.diandi.dto.author.AuthorDTO;
import com.diandi.dto.author.AuthorStatisticsDTO;
import com.diandi.manager.DataFillingManager;
import com.diandi.manager.WebSiteManager;
import com.diandi.service.author.AuthorStatisticsService;
import com.diandi.service.website.WebSiteStatisticsService;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shangmingyu
 * @Description: 站点相关数据统计
 * @date 2018/4/6 13:28
 */
@Service
public class WebSiteStatisticsServiceImpl implements WebSiteStatisticsService {
    @Autowired
    private AuthorStatisticsService statisticsService;

    @Autowired
    private WebSiteManager webSiteManager;

    @Autowired
    private DataFillingManager fillingManager;

    @Override
    public List<AuthorBriefDTO> listActiveAuthor(int count) {

        int[] ids = webSiteManager.getActiveAuthorIds(count);
        AuthorDTO[] authorDTOS = statisticsService.listAuthorDTO(ids);
        if (CollectionUtils.isEmpty(authorDTOS)) return null;
        List<AuthorBriefDTO> dtos = new ArrayList<>();
        for (AuthorDTO author : authorDTOS) {
            ResultBean<AuthorStatisticsDTO> statistics = statisticsService.getAuthorStatistics(author.getId());
            final AuthorBriefDTO dto = fillingManager.authorToBasic(author, statistics.getData());
            dtos.add(dto);
        }

        return dtos;
    }
}
