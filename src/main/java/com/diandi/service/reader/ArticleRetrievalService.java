package com.diandi.service.reader;




import com.diandi.dto.article.ArticleListItemDTO;
import com.diandi.service.common.ArticleFilter;
import com.diandi.util.restful.ResultBean;

import java.util.List;

/**
 * Created on 2017/12/14.
 * 文章检索并排序服务
 * <p>
 * 1 全限定检索
 * 2 关键字检索
 * 3 类别检索
 * 4 标签检索
 *
 * @author DuanJiaNing
 */
public interface ArticleRetrievalService extends ArticleFilter<ResultBean<List<ArticleListItemDTO>>> {

}
