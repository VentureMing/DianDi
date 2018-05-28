package com.diandi.service.website;

import com.diandi.dto.author.AuthorBriefDTO;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: 站点统计数据
 * @date 2018/4/6 13:27
 */
public interface WebSiteStatisticsService {

    /**
     * 获取活跃作者
     * @param count 获取作者格式
     * @return 集合
     */
    List<AuthorBriefDTO> listActiveAuthor(int count);
}
