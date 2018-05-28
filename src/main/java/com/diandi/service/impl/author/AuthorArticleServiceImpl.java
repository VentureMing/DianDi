package com.diandi.service.impl.author;



import com.diandi.dao.article.ArticleCategoryDao;
import com.diandi.dao.article.ArticleStatisticsDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.dto.article.ArticleTitleIdDTO;
import com.diandi.dto.author.ArticleListItemDTO;
import com.diandi.entity.article.Article;
import com.diandi.entity.article.ArticleCategory;
import com.diandi.entity.article.ArticleStatistics;
import com.diandi.exception.internal.InternalLuceneIOException;
import com.diandi.exception.internal.LuceneException;
import com.diandi.exception.internal.SQLException;
import com.diandi.manager.DataFillingManager;
import com.diandi.manager.FileManager;
import com.diandi.manager.ImageManager;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.manager.properties.WebSiteProperties;
import com.diandi.service.author.AuthorArticleService;
import com.diandi.service.impl.common.ArticleFilterAbstract;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.FileUtils;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.ArticleFormatEnum;
import com.diandi.util.enums.ArticleStatusEnum;
import com.diandi.util.enums.AuthorImageCategoryEnum;
import com.diandi.util.restful.ResultBean;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static com.diandi.util.enums.ArticleFormatEnum.MD;
import static com.diandi.util.enums.ArticleStatusEnum.PUBLIC;

/**
 * Created on 2017/12/19.
 * 博主检索博文
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorArticleServiceImpl extends ArticleFilterAbstract<ResultBean<List<ArticleListItemDTO>>> implements AuthorArticleService {

    @Autowired
    private ArticleStatisticsDao statisticsDao;

    @Autowired
    private WebSiteProperties websiteProperties;

    @Autowired
    private AuthorProperties authorProperties;

    @Autowired
    private DataFillingManager dataFillingManager;

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private ArticleCategoryDao categoryDao;

    @Autowired
    private AuthorImageDao pictureDao;

    @Override
    public int insertArticle(int authorId, ArticleStatusEnum status, String title, String content, String contentMd,
                             String summary, String[] keyWords, boolean analysisImg) {

        // 1 插入数据到article表
        String ch = dbProperties.getStringFiledSplitCharacterForNumber();
        String chs = dbProperties.getStringFiledSplitCharacterForString();
        Article article = new Article();
        article.setAuthorId(authorId);


        article.setArticleState(status.getCode());
        article.setArticleTitle(title);
        article.setArticleContentHtml(content);
        article.setArticleContentMd(contentMd);
        article.setSummary(summary);
        article.setKeyWords(StringUtils.arrayToString(keyWords, chs));
        article.setWordCount(content.length());

        int effect = articleDao.insert(article);
        if (effect <= 0) return -1;

        int articleId = article.getArticleId();

        // 2 插入数据到article_statistics表（生成博文信息记录）
        ArticleStatistics statistics = new ArticleStatistics();
        statistics.setArticleId(articleId);
        effect = statisticsDao.insert(statistics);
        if (effect <= 0) throw new SQLException();

        if (analysisImg) {
            // 3 解析本地图片引用并使自增
            int[] imids = parseContentForImageIds(content, authorId);
            // UPDATE: 2018/1/19 更新 自增并没有实际作用
            if (!CollectionUtils.isEmpty(imids)) {
                // 修改图片可见性，引用次数
                Arrays.stream(imids).forEach(id -> imageManager.imageInsertHandle(authorId, id));
            }
        }

        // 4 lucene创建索引
        try {
            luceneIndexManager.add(article);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LuceneException(e);
        }

        return articleId;
    }

    // 解析博文中引用的相册图片
    private int[] parseContentForImageIds(String content, int authorId) {
        //http://localhost:8080/image/1/type=public/523?default=5
        //http://localhost:8080/image/1/type=private/1
        String regex = "http://" + websiteProperties.getAddr() + "/image/" + authorId + "/.*?/(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        List<String> res = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group();
            int index = str.lastIndexOf("/");
            res.add(str.substring(index + 1));
        }

        return res.stream()
                .mapToInt(Integer::valueOf)
                .distinct()
                .toArray();
    }

    @Override
    public boolean updateArticle(int authorId, int articleId, ArticleStatusEnum newStatus,
                                 String newTitle, String newContent, String newContentMd, String newSummary, String[] newKeyWords) {

        // 1 更新博文中引用的本地图片（取消引用的useCount--，新增的useCount++）
        Article oldArticle = articleDao.getArticleById(articleId);
        if (newContent != null) {
            if (!oldArticle.getArticleContentHtml().equals(newContent)) {

                final int[] oldIids = parseContentForImageIds(oldArticle.getArticleContentHtml(), authorId); // 1 2 3 4
                final int[] newIids = parseContentForImageIds(newContent, authorId); // 1 3 4 6

                // 求交集 1 3 4
                int[] array = IntStream.of(oldIids).filter(value -> {
                    for (int id : newIids) if (id == value) return true;
                    return false;
                }).toArray();

                // -- 2
                int[] allM = new int[oldIids.length + array.length];
                System.arraycopy(oldIids, 0, allM, 0, oldIids.length);
                System.arraycopy(array, 0, allM, oldIids.length, array.length);
                IntStream.of(allM).distinct().forEach(pictureDao::updateUseCountMinus);

                // ++ 6
                int[] allP = new int[newIids.length + array.length];
                System.arraycopy(newIids, 0, allP, 0, newIids.length);
                System.arraycopy(array, 0, allP, newIids.length, array.length);
                IntStream.of(allP).distinct().forEach(id -> {
                    pictureDao.updateUseCountPlus(id);

                    // 将用到的图片修改为public（有必要的话）
                    try {
                        imageManager.moveImageAndUpdateDbIfNecessary(authorId, id, AuthorImageCategoryEnum.PUBLIC);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
        }

        // 2 更新博文
        String ch = dbProperties.getStringFiledSplitCharacterForNumber();
        String chs = dbProperties.getStringFiledSplitCharacterForString();
        Article article = new Article();
        article.setArticleId(articleId);
//        if (newCategories != null) article.setCategoryIds(StringUtils.intArrayToString(newCategories, ch));
        // 博文未通过审核时不能修改状态
        if (newStatus != null && !oldArticle.getArticleState().equals(ArticleStatusEnum.VERIFY.getCode()))
            article.setArticleState(newStatus.getCode());
        if (newTitle != null) article.setArticleTitle(newTitle);
        if (newContent != null) article.setArticleContentHtml(newContent);
        if (newSummary != null) article.setSummary(newSummary);
        if (newContentMd != null) article.setArticleContentMd(newContentMd);
        if (newKeyWords != null) article.setKeyWords(StringUtils.arrayToString(newKeyWords, chs));
        int effect = articleDao.update(article);
        if (effect <= 0) throw new SQLException();

        // 3 更新lucene
        try {
            luceneIndexManager.update(article);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LuceneException(e);
        }

        return true;
    }

    @Override
    public boolean deleteArticle(int authorId, int articleId) {

        Article article = articleDao.getArticleById(articleId);
        if (article == null) return false;

        // 1 删除博文记录
        int effect = articleDao.delete(articleId);
        if (effect <= 0) return false;

        // 2 删除统计信息
        int effectS = statisticsDao.deleteByUnique(articleId);
        // MAYBUG 断点调试时effectS始终为0，但最终事务提交时记录却会正确删除，？？？ 因而注释下面的判断
        //if (effectS <= 0) throw new UnknownException(article");

        // 3 图片引用useCount--
        int[] ids = parseContentForImageIds(article.getArticleContentHtml(), authorId);
        if (!CollectionUtils.isEmpty(ids))
            Arrays.stream(ids).forEach(pictureDao::updateUseCountMinus);

        // 4 删除lucene索引
        luceneIndexManager.delete(articleId);

        return true;
    }

    @Override
    public boolean deleteArticlePatch(int authorId, int[] articleIds) {

        for (int id : articleIds) {
            if (!deleteArticle(authorId, id))
                throw new SQLException();
        }

        return true;
    }

    @Override
    public boolean getArticleForCheckExist(int articleId) {
        return !(articleDao.getArticleIdById(articleId) == null);
    }

    @Override
    public ResultBean<Article> getArticle(int authorId, int articleId) {

        Article article = articleDao.getArticleById(articleId);

        String ch = dbProperties.getStringFiledSplitCharacterForNumber();
        String chs = dbProperties.getStringFiledSplitCharacterForString();
        String whs = websiteProperties.getUrlConditionSplitCharacter();

        if (article != null && article.getAuthorId().equals(authorId)) {

            String cids = "";

            String keyWords = article.getKeyWords();

//            if (!StringUtils.isEmpty(cids))
//                article.setCategoryIds(cids.replace(ch, whs));



            if (!StringUtils.isEmpty_(keyWords))
                article.setKeyWords(keyWords.replace(chs, whs));

            return new ResultBean<>(article);

        }

        return null;
    }


    @Override
    public ResultBean<Article> getArticleByArticleId( int articleId) {

            return new ResultBean<>(articleDao.getArticleById(articleId));

    }

    @Override
    protected ResultBean<List<ArticleListItemDTO>> constructResult(Map<Integer, Article> articleHashMap,
                                                                List<ArticleStatistics> statistics,
                                                                Map<Integer, String> articleImgs) {
        // 重组结果
        List<ArticleListItemDTO> result = new ArrayList<>();
        for (ArticleStatistics s : statistics) {
            Integer articleId = s.getArticleId();
            int[] ids = {};
            List<ArticleCategory> categories = CollectionUtils.isEmpty(ids) ? null : categoryDao.listCategoryByIds(ids);
            Article article = articleHashMap.get(articleId);
            ArticleListItemDTO dto = dataFillingManager.authorArticleListItemToDTO(article, s, categories);
            result.add(dto);
        }

        return new ResultBean<>(result);
    }

    @Override
    public int getArticleId(int authorId, String articleName) {
        Integer id = articleDao.getArticleIdByUniqueKey(authorId, articleName);
        return Optional.ofNullable(id).orElse(-1);
    }

    @Override
    public List<ArticleTitleIdDTO> insertArticlePatch(MultipartFile file, int authorId) {

        fileManager.mkdirsIfNotExist(authorProperties.getPatchImportArticleTempPath());

        // 保存到临时文件
        String fullPath = authorProperties.getPatchImportArticleTempPath() +
                File.separator +
                "temp-" +
                authorId +
                "-" +
                System.currentTimeMillis() +
                "-" +
                file.getOriginalFilename();

        FileUtils.saveFileTo(file, fullPath);

        // 解析博文
        List<ArticleTitleIdDTO> result = new ArrayList<>();
        final Parser parser = Parser.builder().build();
        final HtmlRenderer renderer = HtmlRenderer.builder().build();

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(fullPath);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                BufferedInputStream stream = new BufferedInputStream(zipFile.getInputStream(entry));
                InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));

                ArticleTitleIdDTO node = analysisAndInsertMdFile(parser, renderer, entry, reader, authorId);
                if (node != null)
                    result.add(node);
            }

        } catch (IOException e) {
            throw new InternalLuceneIOException(e);
        } finally {
            if (zipFile != null) try {
                zipFile.close();

                // 删除临时文件
                fileManager.deleteFileIfExist(fullPath);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return result;
    }

    @Override
    public String getAllArticleForDownload(int authorId, ArticleFormatEnum format) {
        List<Article> articles = articleDao.listAllByFormat(authorId, format.getCode());
        if (CollectionUtils.isEmpty(articles)) return null;

        fileManager.mkdirsIfNotExist(authorProperties.getPatchDownloadArticleTempPath());

        String zipFilePath = authorProperties.getPatchDownloadArticleTempPath() +
                File.separator +
                System.currentTimeMillis() +
                "-" +
                "total-of-" +
                articles.size() +
                "-articles.zip";

        File zipFile = new File(zipFilePath);
        List<String> tempArticleFile = new ArrayList<>();

        // 压缩博文
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            for (Article article : articles) {
                String path = addArticleToZip(article, zipOut, format);
                if (path != null) {
                    tempArticleFile.add(path);
                }
            }
        } catch (IOException e) {
            throw new InternalLuceneIOException(e);
        } finally {
            if (zipOut != null) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    throw new InternalLuceneIOException(e);
                }
            }
        }

        // 统一删除临时博文文件
        tempArticleFile.forEach(fileManager::deleteFileIfExist);

        return zipFile.getAbsolutePath();
    }

    private String addArticleToZip(Article article, ZipOutputStream zipOut, ArticleFormatEnum format) throws IOException {

        // 新建博文文件
        String title = article.getArticleTitle();
        String content = format == MD ? article.getArticleContentMd() : article.getArticleContentHtml();

        String bp = authorProperties.getPatchDownloadArticleTempPath() +
                File.separator +
                title +
                (format == MD ? ".md" : ".html");

        FileOutputStream fo = new FileOutputStream(bp);
        OutputStreamWriter writer = new OutputStreamWriter(fo, "UTF-8");
        writer.write(content);
        writer.close();

        // 添加到 zip 压缩文件
        File entryFile = new File(bp);
        zipOut.putNextEntry(new ZipEntry(entryFile.getName()));

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(entryFile));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer)) > 0) {
            zipOut.write(buffer, 0, len);
        }

        bis.close();
        zipOut.closeEntry();

        return bp;
    }

    // 解析 md 文件读取字符流，新增记录到数据库
    private ArticleTitleIdDTO analysisAndInsertMdFile(Parser parser, HtmlRenderer renderer, ZipEntry entry, InputStreamReader reader, int authorId) throws IOException {

        if (!entry.getName().endsWith(".md")) return null;

        // 文件名作为标题
        String title = entry.getName().replace(".md", "");

        StringBuilder b = new StringBuilder((int) entry.getSize());
        int len = 0;
        char[] buff = new char[1024];
        while ((len = reader.read(buff)) > 0) {
            b.append(buff, 0, len);
        }

        // reader.close();
        // zip 文件关闭由 370行：zipFile.close() 统一关闭

        // 内容
        String mdContent = b.toString();

        // 对应的 html 内容
        Document document = parser.parse(mdContent);
        String htmlContent = renderer.render(document);

        // 摘要
        String firReg = htmlContent.replaceAll("<.*?>", ""); // 避免 subString 使有遗留的 html 标签，前端显示时会出错
        String tmpStr = firReg.length() > 500 ? firReg.substring(0, 500) : firReg;
        String aftReg = tmpStr.replaceAll("\\n", "");
        String summary = aftReg.length() > 200 ? aftReg.substring(0, 200) : aftReg;

        // UPDATE: 2018/4/4 更新 图片引用
        int id = insertArticle(authorId, PUBLIC, title, htmlContent, mdContent, summary, null, false);
        if (id < 0) return null;

        ArticleTitleIdDTO node = new ArticleTitleIdDTO();
        node.setTitle(title);
        node.setId(id);

        return node;
    }

}
