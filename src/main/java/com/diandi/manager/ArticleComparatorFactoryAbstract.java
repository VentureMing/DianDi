package com.diandi.manager;


import com.diandi.util.common.Order;
import com.diandi.util.common.Rule;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shangmingyu
 * @Description: 排序所需的比较器创建工厂，比较器名称定义于{@link Rule}中
 * @date 2018/5/6 15:08
 */
public abstract class ArticleComparatorFactoryAbstract<T> {

    private Map<Rule, Comparator<T>> coms = new HashMap<>();
    Order order = Order.ASC;

    ArticleComparatorFactoryAbstract() {
        initFactory();
    }

    /**
     * 初始所有备用比较器
     */
    protected abstract void initFactory();

    public Comparator<T> get(Rule rule, Order order) {
        this.order = order;
        return coms.get(rule);
    }

    void add(Rule key, Comparator<T> comparator) {
        coms.put(key, comparator);
    }

}
