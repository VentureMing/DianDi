package com.diandi.service.common;

import com.diandi.dto.article.ArticleSortRuleDTO;
import com.diandi.util.restful.ResultBean;


import java.util.List;

/**
 * Created on 2018/2/12.
 *
 * @author DuanJiaNing
 */
public interface ArticleSortRuleService {

    /**
     * 获得所有的排序规则
     *
     * @return 结果
     */
    ResultBean<List<ArticleSortRuleDTO>> listSortRule();

    /**
     * 获得排序顺序
     *
     * @return 结果
     */
    ResultBean<List<ArticleSortRuleDTO>> listSortOrder();

}
