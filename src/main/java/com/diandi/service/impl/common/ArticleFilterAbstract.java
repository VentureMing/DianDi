package com.diandi.service.impl.common;


import com.diandi.dao.article.ArticleAndCategoryDao;
import com.diandi.dao.article.ArticleDao;
import com.diandi.dao.article.ArticleStatisticsDao;
import com.diandi.dao.author.AuthorFollowedDao;
import com.diandi.entity.article.Article;
import com.diandi.entity.article.ArticleStatistics;
import com.diandi.entity.author.AuthorFollowed;
import com.diandi.exception.internal.LuceneException;
import com.diandi.manager.ArticleListItemComparatorFactory;
import com.diandi.manager.ArticleLuceneIndexManager;
import com.diandi.manager.properties.DBProperties;
import com.diandi.service.common.ArticleFilter;
import com.diandi.util.common.ArticleSortRule;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.ArticleStatusEnum;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created on 2018/1/15.
 * 文章检索
 *
 * @author DuanJiaNing
 */
@Component
public abstract class ArticleFilterAbstract<T> implements ArticleFilter<T> {

    @Autowired
    protected DBProperties dbProperties;

    @Autowired
    protected ArticleDao articleDao;

    @Autowired
    protected ArticleLuceneIndexManager luceneIndexManager;

    @Autowired
    private ArticleStatisticsDao statisticsDao;
    @Autowired
    private ArticleAndCategoryDao articleAndCategoryDao;

    @Autowired
    AuthorFollowedDao authorFollowedDao;
    private static volatile AtomicInteger count = new AtomicInteger();

    @Override
    public int getFilterCount() {
        return count.getAndSet(0);
    }

    /**
     * 构造结果集，statistics是经过筛选而且排序了的结果，可借助statistics的顺序来得到最终结果
     *
     * @param articleHashMap 文章id为键，文章为值的map
     * @param statistics     已排序的文章统计信息集合
     * @param articleImgs
     * @return 最终结果
     */
    protected abstract T constructResult(Map<Integer, Article> articleHashMap, List<ArticleStatistics> statistics, Map<Integer, String> articleImgs);

    @Override
    public T listFilterAll(String keyWord, int authorId, ArticleSortRule sortRule, ArticleStatusEnum status) {
        if (StringUtils.isEmpty(keyWord)) {
            //加载登录作者关注话题和关注人的文章
            //无登录authorId默认为1（管理员）
            return listFilterByAuthorId(authorId, sortRule, status);
        } else {
            // 有关键字时需要依赖lucene进行检索
            // UPDATE: 2018/1/10 搜索准确度比较低
            int[] categoryIds = {2, 5, 5};
            return filterByLucene(keyWord, categoryIds, authorId, sortRule, status);
        }

    }

    /**
     * 关键字不为null时需要通过lucene进行全文检索
     *
     * @param keyWord     关键字
     * @param categoryIds 类别id
     * @param authorId    作者id
     * @param sortRule    排序规则
     * @param status      文章状态
     * @return 经过筛选、排序的结果集
     */
    protected T filterByLucene(String keyWord, int[] categoryIds,
                               int authorId, ArticleSortRule sortRule,
                               ArticleStatusEnum status) {

        // ------------------------关键字筛选
        int[] ids;
        try {
            // 搜索结果无法使用类似于sql limit的方式分页，这里一次性将所有结果查询出，后续考虑使用缓存实现分页
            ids = luceneIndexManager.search(keyWord, 10000);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new LuceneException(e);
        }
        //关键字为首要条件
        if (CollectionUtils.isEmpty(ids)) return null;

        // 关键字检索得到的文章集合
        List<Integer> filterByLuceneIds = new ArrayList<>();
        // UPDATE 取最前面的rows条结果
//        int row = Math.min(rows, );
        for (int i = 0; i < ids.length; i++) filterByLuceneIds.add(ids[i]);

        // ----------------------类别、标签筛选
//        Map<Integer, int[]> map = getMapFilterByCategory(authorId, categoryIds,  status);
        Map<Integer, int[]> map = new HashMap<>();
        Integer[] mids = map.keySet().toArray(new Integer[map.size()]);
        // 类别、标签检索得到的文章集合
        List<Integer> filterByOtherIds = Arrays.asList(mids);

        //求两者交集得到最终结果集
        List<Integer> resultIds = filterByLuceneIds.stream().filter(filterByOtherIds::contains).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(resultIds)) return null;

        //构造结果,排序并重组
        count.set(resultIds.size());
        List<Article> articles = articleDao.listArticleByArticleIds(resultIds, status.getCode());
        return sortAndConstructResult(articles, sortRule);
    }


    public T listFilterByAuthorId(int authorId,
                                  ArticleSortRule sortRule, ArticleStatusEnum status) {


        //获取登录作者关注的作者id和关注话题id
        List<Integer> authorIdList = authorFollowedDao.listAuthorIdsByAuthorId(authorId);
        List<Integer> categoryIdList = authorFollowedDao.listCategoryIdsByAuthorId(authorId);

        List<Integer> articleList = articleDao.listArticleIdsByAuthorIds(authorIdList, status.getCode());
        List<Integer> categoryList = articleAndCategoryDao.listArticleIdsByCategoryIds(categoryIdList);
        List<Integer> finalList = new ArrayList<Integer>();

        //获取文章id
        if (articleList.size() != 0) {
            finalList.addAll(articleList);
        }
        if (categoryList.size() != 0) {
            finalList.addAll(categoryList);
        } ;
        if(finalList.size()==0) return null;
        //文章id去重
        Set<Integer> articleSet = new HashSet<>(finalList);
        Integer[] ids = new Integer[articleSet.size()];
        articleSet.toArray(ids);
        if (CollectionUtils.isEmpty(ids)) return null;

        //构造结果,排序并重组
        count.set(ids.length);
        List<Article> resultArticles = articleDao.listArticleByArticleIds(Arrays.asList(ids), status.getCode());
        return sortAndConstructResult(resultArticles, sortRule);
    }


    public T listFilterOnlyByAuthorId(int authorId,
                                  ArticleSortRule sortRule, ArticleStatusEnum status) {


        //获取登录作者关注的作者id和关注话题id


//        List<Integer> articleList = ;
//
//        if(articleList.size()==0) return null;
//
//        Integer[] ids = new Integer[articleList.size()];
//        articleList.toArray(ids);
//        if (CollectionUtils.isEmpty(ids)) return null;

        //构造结果,排序并重组

        List<Article> resultArticles = articleDao.listArticle(authorId, status.getCode());
        count.set(resultArticles.size());
        return sortAndConstructResult(resultArticles, sortRule);
    }


    // 对筛选出的进行排序并重组结果集
    private T sortAndConstructResult(List<Article> articles, ArticleSortRule sortRule) {

        //用于排序
        List<ArticleStatistics> temp = new ArrayList<>();

        //方便排序后的重组
        Map<Integer, Article> articleHashMap = new HashMap<>();

        // 从 html 中获得图片
        Map<Integer, String> articleImgs = new HashMap<>();

        for (Article article : articles) {
            int articleId = article.getArticleId();
            ArticleStatistics statistics = statisticsDao.getStatistics(articleId);
            temp.add(statistics);
            articleHashMap.put(articleId, article);

            String content = article.getArticleContentHtml();
            Pattern pattern = Pattern.compile("<img src=\"(.*)\" .*>");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find())
                articleImgs.put(articleId, matcher.group(1));

        }

        ArticleListItemComparatorFactory factory = new ArticleListItemComparatorFactory();
        temp.sort(factory.get(sortRule.getRule(), sortRule.getOrder()));

        return constructResult(articleHashMap, temp, articleImgs);

    }


}
