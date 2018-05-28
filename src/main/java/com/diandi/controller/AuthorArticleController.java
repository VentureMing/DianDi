package com.diandi.controller;


import com.diandi.dto.article.ArticleTitleIdDTO;
import com.diandi.dto.author.ArticleListItemDTO;
import com.diandi.entity.article.Article;
import com.diandi.manager.FileManager;
import com.diandi.service.author.AuthorArticleService;
import com.diandi.util.common.*;
import com.diandi.util.enums.ArticleFormatEnum;
import com.diandi.util.enums.ArticleStatusEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created on 2018/1/15.
 * 博主文章api
 * <p>
 * 1 新增文章
 * 2 获取文章
 * 3 获取指定文章
 * 4 修改文章
 * 5 删除文章
 * 6 批量删除文章文章
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/author/{authorId}/article")
public class AuthorArticleController extends BaseAuthorController {

    @Autowired
    private AuthorArticleService authorArticleService;

    @Autowired
    private FileManager fileManager;

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean add(HttpServletRequest request,
                          @PathVariable Integer authorId,
                          @RequestParam("title") String title,
                          @RequestParam("contentHTML") String contentHTML,
                          @RequestParam("contentMd") String contentMd,
                          @RequestParam("summary") String summary,
                          @RequestParam(value = "keyWords", required = false) String keyWords) {

        // 检查不能为null的参数是否为null
        if (StringUtils.isEmpty_(title) || StringUtils.isEmpty_(contentHTML) || StringUtils.isEmpty_(summary))
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));

        // 将 Unicode 解码
        contentHTML = StringUtils.unicodeToString(contentHTML);
        contentMd = StringUtils.unicodeToString(contentMd);

        handleArticleContentCheck(request, title, contentHTML, contentMd, summary, keyWords);


        String sp = websiteProperties.getUrlConditionSplitCharacter();
//        int[] cids = StringUtils.intStringDistinctToArray(categoryIds, sp);




        String[] kw = StringUtils.stringArrayToArray(keyWords, sp);
        // UPDATE: 2018/1/16 更新 文章审核 图片引用
        int id = authorArticleService.insertArticle(authorId,ArticleStatusEnum.PUBLIC, title, contentHTML, contentMd, summary, kw, false);
        if (id <= 0) handlerOperateFail(request);

        return new ResultBean<>(id);
    }

    /**
     * 检索文章
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<List<ArticleListItemDTO>> list(HttpServletRequest request,
                                                     @PathVariable Integer authorId,

                                                     @RequestParam(value = "keyWords", required = false) String keyWord,

                                                     @RequestParam(value = "sort", required = false) String sort,
                                                     @RequestParam(value = "order", required = false) String order,
                                                     @RequestParam(value = "status", required = false) Integer status) {
        handleAuthorSignInCheck(request, authorId);

        //检查排序规则
        String sor = sort == null ? Rule.VIEW_COUNT.name() : sort.toUpperCase();
        String ord = order == null ? Order.DESC.name() : order.toUpperCase();
        handleSortRuleCheck(request, sor, ord);

        String sp = websiteProperties.getUrlConditionSplitCharacter();



        ArticleStatusEnum stat = null;
        if (status != null) stat = ArticleStatusEnum.valueOf(status);
        if (stat == null) stat = ArticleStatusEnum.PUBLIC; // status传参错误

        //执行数据查询
        ArticleSortRule rule = new ArticleSortRule(Rule.valueOf(sor), Order.valueOf(ord));
//        int os = offset == null || offset < 0 ? 0 : offset;
//        int rs = rows == null || rows < 0 ? authorProperties.getRequestArticleListCount() : rows;
        ResultBean<List<ArticleListItemDTO>> listResultBean = authorArticleService.listFilterAll(keyWord, authorId,
               rule, stat);
        if (listResultBean == null) handlerEmptyResult(request);

        return listResultBean;
    }

    /**
     * 获取指定文章
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public ResultBean<Article> get(HttpServletRequest request,
                                   @PathVariable Integer authorId,
                                   @PathVariable Integer articleId) {
        handleAuthorSignInCheck(request, authorId);

        ResultBean<Article> article = authorArticleService.getArticle(authorId, articleId);
        if (article == null) handlerEmptyResult(request);

        // 编码为 Unicode
        Article articleData = article.getData();
        articleData.setArticleContentHtml(StringUtils.stringToUnicode(articleData.getArticleContentHtml()));
        articleData.setArticleContentMd(StringUtils.stringToUnicode(articleData.getArticleContentMd()));

        return article;
    }

    /**
     * 更新文章
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.PUT)
    public ResultBean update(HttpServletRequest request,
                             @PathVariable Integer authorId,
                             @PathVariable Integer articleId,
                             @RequestParam(value = "title", required = false) String newTitle,
                             @RequestParam(value = "contentHTML", required = false) String newContent,
                             @RequestParam(value = "contentMd", required = false) String newContentMd,
                             @RequestParam(value = "summary", required = false) String newSummary,
                             @RequestParam(value = "keyWords", required = false) String newKeyWord,
                             @RequestParam(value = "status", required = false) Integer newStatus) {

        // 所有参数都为null，则不更新。
        if (Stream.of(newTitle, newContent, newSummary, newKeyWord, newStatus)
                .filter(Objects::nonNull).count() <= 0)
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));

        // 检查修改到的文章状态是否允许
        if (newStatus != null && !articleValidateManager.isArticleStatusAllow(newStatus))
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));

        handleAuthorSignInCheck(request, authorId);
        handleArticleExistAndCreatorCheck(request, authorId, articleId);

        // 将 Unicode 解码
        newContent = StringUtils.unicodeToString(newContent);
        newContentMd = StringUtils.unicodeToString(newContentMd);

        handleArticleContentCheck(request, newTitle, newContent, newContentMd, newSummary, newKeyWord);

        String sp = websiteProperties.getUrlConditionSplitCharacter();
//        int[] cids = newCategoryIds == null ? null : StringUtils.intStringDistinctToArray(newCategoryIds, sp);




        String[] kw = newKeyWord == null ? null : StringUtils.stringArrayToArray(newKeyWord, sp);
        ArticleStatusEnum stat = newStatus == null ? null : ArticleStatusEnum.valueOf(newStatus);

        //执行更新
        if (!authorArticleService.updateArticle(authorId, articleId,  stat, newTitle, newContent, newContentMd, newSummary, kw))
            handlerOperateFail(request);

        return new ResultBean<>("");
    }

    /**
     * 删除文章
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.DELETE)
    public ResultBean delete(HttpServletRequest request,
                             @PathVariable Integer authorId,
                             @PathVariable Integer articleId) {

        handleAuthorSignInCheck(request, authorId);
        handleArticleExistAndCreatorCheck(request, authorId, articleId);

        if (!authorArticleService.deleteArticle(authorId, articleId))
            handlerOperateFail(request);

        return new ResultBean<>("");
    }

    /**
     * 批量删除文章
     */
    @RequestMapping(value = "/patch", method = RequestMethod.DELETE)
    public ResultBean deletePatch(HttpServletRequest request,
                                  @PathVariable Integer authorId,
                                  @RequestParam("ids") String ids) {

        handleAuthorSignInCheck(request, authorId);

        int[] articleIds = StringUtils.intStringDistinctToArray(ids,
                websiteProperties.getUrlConditionSplitCharacter());
        if (CollectionUtils.isEmpty(articleIds))
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));

        for (int id : articleIds) {
            handleArticleExistAndCreatorCheck(request, authorId, id);
        }

        if (!authorArticleService.deleteArticlePatch(authorId, articleIds))
            handlerOperateFail(request);

        return new ResultBean<>("");
    }

    /**
     * 批量导入文章
     * <p>
     * 返回成功导入文章的文章名和id
     */
    @RequestMapping(value = "/patch", method = RequestMethod.POST)
    public ResultBean<List<ArticleTitleIdDTO>> patchImportArticle(HttpServletRequest request,
                                                               @PathVariable Integer authorId,
                                                               @RequestParam("zipFile") MultipartFile file) {

        handleAuthorSignInCheck(request, authorId);

        // 检查是否为 zip 文件
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".zip"))
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));

        List<ArticleTitleIdDTO> articlesTitles = authorArticleService.insertArticlePatch(file, authorId);
        if (CollectionUtils.isEmpty(articlesTitles))
            handlerOperateFail(request);

        return new ResultBean<>(articlesTitles);
    }

    /**
     * 下载文章
     */
    @RequestMapping(value = "/download-type={type}", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable Integer authorId,
                         @PathVariable String type) {
        handleAuthorSignInCheck(request, authorId);

        // 检查请求的文件类别
        ArticleFormatEnum format = ArticleFormatEnum.get(type);
        if (format == null) {
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));
        }

        String zipFilePath = authorArticleService.getAllArticleForDownload(authorId, format);
        if (StringUtils.isEmpty(zipFilePath)) handlerOperateFail(request);

        // 输出文件流
        outFile(zipFilePath, request, response);

        // 删除临时 zip 文件
        fileManager.deleteFileIfExist(zipFilePath);
    }

    // 输出文件流
    private void outFile(String zipFilePath, HttpServletRequest request, HttpServletResponse response) {

        try (ServletOutputStream os = response.getOutputStream()) {
            File zipFile = new File(zipFilePath);
            if (!zipFile.exists()) handlerOperateFail(request);

            response.setContentType("application/x-zip-compressed");
            FileInputStream in = new FileInputStream(zipFile);
            byte[] buff = new byte[in.available()];
            in.read(buff);

            os.write(buff);
            os.flush();

            in.close();
            os.close();

        } catch (IOException e) {
            handlerOperateFail(request, e);
        }

    }

    // 检查文章是否存在，且文章是否属于指定博主
    private void handleArticleExistAndCreatorCheck(HttpServletRequest request, int authorId, int articleId) {
        if (!articleValidateManager.isCreatorOfArticle(authorId, articleId))
            throw exceptionManager.getUnknownArticleException(new RequestContext(request));
    }


    //文章内容审核
    private void handleArticleContentCheck(HttpServletRequest request, String title, String content, String contentMd, String summary,
                                           String keyWords) {
        if (!articleValidateManager.verifyArticle(title, content, contentMd, summary, keyWords))
            throw exceptionManager.getArticleIllegalException(new RequestContext(request));

    }

    // 检查排序规则
    private void handleSortRuleCheck(HttpServletRequest request, String sort, String order) {

        if (sort != null && !Rule.contains(sort)) {
            throw exceptionManager.getArticleSortRuleUndefinedException(new RequestContext(request));
        }

        if (order != null && !Order.contains(order)) {
            throw exceptionManager.getArticleSortOrderUndefinedException(new RequestContext(request));
        }
    }

}
