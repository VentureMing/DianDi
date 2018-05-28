package com.diandi.service.impl.author;

import com.diandi.dao.article.ArticleCollectDao;
import com.diandi.dao.article.ArticleDao;
import com.diandi.dao.article.ArticleLikeDao;
import com.diandi.dao.article.ArticleStatisticsDao;
import com.diandi.dao.author.AuthorAccountDao;
import com.diandi.dao.author.AuthorBasicDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.dao.author.AuthorSettingDao;
import com.diandi.entity.article.Article;
import com.diandi.entity.article.ArticleCollect;
import com.diandi.entity.article.ArticleLike;
import com.diandi.entity.author.AuthorAccount;
import com.diandi.entity.author.AuthorBasic;
import com.diandi.entity.author.AuthorImage;
import com.diandi.entity.author.AuthorSetting;
import com.diandi.exception.internal.UnknownInternalException;
import com.diandi.manager.ArticleLuceneIndexManager;
import com.diandi.manager.ImageManager;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.service.author.AuthorAccountService;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorAccountServiceImpl implements AuthorAccountService {

    @Autowired
    private AuthorAccountDao accountDao;

    @Autowired
    private AuthorImageDao pictureDao;

    @Autowired
    private AuthorBasicDao basicDao;

    @Autowired
    private AuthorSettingDao settingDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleCollectDao collectDao;

    @Autowired
    private ArticleLikeDao likeDao;

    @Autowired
    private ArticleStatisticsDao articleStatisticsDao;

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private ArticleLuceneIndexManager luceneIndexManager;

    @Autowired
    private AuthorProperties propertiesManager;

    @Override
    public int insertAccount(String email, String password) {

        String shaPwd;
        try {
            //将密码通过sha的方式保存
            shaPwd = new BigInteger(StringUtils.toSha(password)).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UnknownInternalException(e);
        }

        AuthorAccount account = new AuthorAccount();
        account.setAuthorEmail(email);
        account.setAuthorPassword(shaPwd);
        int effect = accountDao.insert(account);
        if (effect <= 0) return -1;

        int authorId = account.getAuthorId();

//        // 生成设置数据
//        AuthorSetting setting = new AuthorSetting();
//        setting.setAuthorId(authorId);
//        setting.setMainPageNavPos(propertiesManager.getMainPageNavPos());
//        settingDao.insert(setting);

        return authorId;
    }

    @Override
    public AuthorAccount getAccountById(int authorId) {
        return accountDao.getAccountById(authorId);
    }

    @Override
    public AuthorAccount getAccountByEmail(String authorEmail) {
        return accountDao.getAccountByEmail(authorEmail);
    }

    @Override
    public boolean deleteAccount(int authorId) {

        // 图片管理员不允许注销账号
        if (propertiesManager.getImageManagerAuthorId().equals(authorId))
            return false;

        //博主图片需要手动删除
        List<AuthorImage> ps = pictureDao.getImageByAuthorId(authorId);
        if (!CollectionUtils.isEmpty(ps))
            ps.stream().map(AuthorImage::getPath).forEach(imageManager::deleteImageFromDisk);

        // 删除博文的lucene索引
        List<Article> articleIds = articleDao.listAllLabelByAuthorId(authorId);
        if (!CollectionUtils.isEmpty(articleIds))
            articleIds.stream().mapToInt(Article::getArticleId).forEach(luceneIndexManager::delete);

        // 将博主喜欢和收藏的博文的喜欢/收藏次数减一
        List<ArticleCollect> collects = collectDao.listAllIdByAuthorId(authorId);
        if (!CollectionUtils.isEmpty(collects))
            collects.stream().mapToInt(ArticleCollect::getArticleId).forEach(articleStatisticsDao::updateCollectCountMinus);

        List<ArticleLike> likes = likeDao.listAllIdByAuthorId(authorId);
        if (!CollectionUtils.isEmpty(likes))
            likes.stream().mapToInt(ArticleLike::getArticleId).forEach(articleStatisticsDao::updateLikeCountMinus);


        // 账户相关数据删除由关系数据库负责处理
        // 删除 author_account 数据
        // ->
        int effect = accountDao.delete(authorId);
        if (effect <= 0) return false;

        return true;
    }

    @Override
    public boolean updateAccountUserName(int authorId, String newUserName) {

        AuthorAccount account = new AuthorAccount();
        account.setAuthorId(authorId);
        account.setAuthorEmail(newUserName);
        int effect = accountDao.update(account);
        if (effect <= 0) return false;

        return true;
    }

    @Override
    public boolean updateAccountPassword(int authorId, String oldPassword, String newPassword) {

        String oldSha;
        String newSha;

        try {
            oldSha = new BigInteger(StringUtils.toSha(oldPassword)).toString();
            newSha = new BigInteger(StringUtils.toSha(newPassword)).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UnknownInternalException(e);
        }

        AuthorAccount account = accountDao.getAccountById(authorId);
        String oriSha = account.getAuthorPassword();

        // 旧密码正确，同时与新密码相同（并没有修改）
        if (!oriSha.equals(oldSha) || oldSha.equals(newSha)) return false;

        AuthorAccount a = new AuthorAccount();
        a.setAuthorId(authorId);
        a.setAuthorPassword(newSha);
        int effect = accountDao.update(a);
        if (effect <= 0) return false;

        return true;
    }

    @Override
    public AuthorAccount getAccountByPhone(String phone) {
        AuthorBasic basicInfo = basicDao.getBasicInfoByPhone(phone);
        if (basicInfo == null) return null;
        return accountDao.getAccountById(basicInfo.getAuthorId());
    }
}
