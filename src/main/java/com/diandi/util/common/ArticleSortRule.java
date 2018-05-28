package com.diandi.util.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author shangmingyu
 * @Description: 文章排序规则
 * @date 2018/5/8 16:28
 */
@Getter
@AllArgsConstructor
public class ArticleSortRule {

    /**
     * 排序依据
     */
    private final Rule rule;

    /**
     * 升序或降序
     */
    private final Order order;

    public static ArticleSortRule valueOf(String ruleName, String orderName) {
        Rule rule = Rule.valueOf(ruleName);
        Order order = Order.valueOf(orderName);
        return new ArticleSortRule(rule, order);
    }

}
