package com.diandi.service.impl.common;

import com.diandi.dto.article.ArticleSortRuleDTO;
import com.diandi.service.common.ArticleSortRuleService;
import com.diandi.util.common.Order;
import com.diandi.util.common.Rule;
import com.diandi.util.restful.ResultBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2018/2/12.
 *
 * @author DuanJiaNing
 */
@Service
public class ArticleSortRuleServiceImpl implements ArticleSortRuleService {

    @Override
    public ResultBean<List<ArticleSortRuleDTO>> listSortRule() {

        List<ArticleSortRuleDTO> list = new ArrayList<>();
        Arrays.stream(Rule.values()).forEach(rule -> list.add(new ArticleSortRuleDTO(rule.name().toLowerCase(), rule.title())));

        return new ResultBean<>(list);
    }

    @Override
    public ResultBean<List<ArticleSortRuleDTO>> listSortOrder() {

        List<ArticleSortRuleDTO> list = new ArrayList<>();
        Arrays.stream(Order.values()).forEach(order -> list.add(new ArticleSortRuleDTO(order.name().toLowerCase(), order.title())));

        return new ResultBean<>(list);
    }
}
