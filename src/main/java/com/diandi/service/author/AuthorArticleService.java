package com.diandi.service.author;

import com.diandi.dto.author.ArticleListItemDTO;
import com.diandi.dto.article.ArticleTitleIdDTO;
import com.diandi.entity.article.Article;
import com.diandi.service.common.ArticleFilter;
import com.diandi.util.enums.ArticleFormatEnum;
import com.diandi.util.enums.ArticleStatusEnum;
import com.diandi.util.restful.ResultBean;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created on 2017/12/18.
 * 博主对自己的博文管理服务
 *
 * @author DuanJiaNing
 */
public interface AuthorArticleService extends ArticleFilter<ResultBean<List<ArticleListItemDTO>>> {

    /**
     * 1 新增博客
     * 2 为博文生成一条统计信息记录
     * 3 解析博文中引用的本地图片（以使其useCount自增）
     * 4 lucene添加索引
     *
     * @param authorId   博主id
     * @param status      状态
     * @param title       标题
     * @param content     内容
     * @param contentMd   md内容
     * @param summary     摘要
     * @param keyWords    关键字
     * @param analysisImg 解析博文中的图片引用
     * @return 新纪录id
     */
    int insertArticle(int authorId, ArticleStatusEnum status,
                      String title, String content, String contentMd, String summary, String[] keyWords, boolean analysisImg);

    /**
     * 1 更新博文
     * 2 更新博文中引用的本地图片（取消引用的useCount--，新增的useCount++）
     * 3 更新lucene
     *
     * @param authorId     博主id
     * @param articleId        博文id
     * @param newStatus     新状态，不修改传null
     * @param newTitle      新标题，不修改传null
     * @param newContent    新内容，不修改传null
     * @param newContentMd  md内容，不修改传null
     * @param newSummary    新摘要，不修改传null
     * @param newKeyWords   新关键字，，不修改传null
     * @return 更新失败为false
     */
    boolean updateArticle(int authorId, int articleId, ArticleStatusEnum newStatus,
                          String newTitle, String newContent, String newContentMd, String newSummary, String[] newKeyWords);

    /**
     * 1 删除博文
     * 2 删除统计信息记录
     * 3 博文中引用的图片useCount--
     * 4 删除lucene索引
     *
     * @param authorId 博主id
     * @param articleId    博文id
     * @return 删除的记录
     */
    boolean deleteArticle(int authorId, int articleId);

    /**
     * 批量删除博文
     *
     * @param authorId 博主id
     * @param articleIds   博文id
     * @return 操作失败为false
     */
    boolean deleteArticlePatch(int authorId, int[] articleIds);

    /**
     * 检查博文是否存在
     *
     * @param articleId 博文id
     * @return 存在返回true，否则false
     */
    boolean getArticleForCheckExist(int articleId);

    /**
     * 获得指定博主的指定博文
     *
     * @param authorId 博主id
     * @param articleId    博文id
     * @return 查询结果
     */
    ResultBean<Article> getArticle(int authorId, int articleId);

     ResultBean<Article> getArticleByArticleId( int articleId);

    /**
     * 通过作者id和文章名获得文章id
     *
     * @param authorId 博主id
     * @param articleName  博文标题
     * @return 存在返回id，否则-1
     */
    int getArticleId(int authorId, String articleName);

    /**
     * 通过上传的 zip 文件批量导入博文
     *
     * @param file      文件
     * @param authorId 博主id
     * @return 成功导入的博文标题和id
     */
    List<ArticleTitleIdDTO> insertArticlePatch(MultipartFile file, int authorId);

    /**
     * 生成用于 [导出所有博文] 功能的 zip 文件
     *
     * @param authorId 博主 articleId
     * @param format    格式
     * @return zip 文件全路径
     */
    String getAllArticleForDownload(int authorId, ArticleFormatEnum format);
}
