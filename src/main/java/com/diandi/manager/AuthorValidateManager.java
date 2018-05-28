package com.diandi.manager;


import com.diandi.dao.article.ArticleCategoryDao;
import com.diandi.dao.author.AuthorAccountDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.entity.author.AuthorImage;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.AuthorImageCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author shangmingyu
 * @Description: 作者相关信息审核
 * @date 2018/5/6 15:08
 */
@Component
public class AuthorValidateManager {

    @Autowired
    private AuthorAccountDao authorAccountDao;

    @Autowired
    private AuthorImageDao authorImageDao;

    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    @Autowired
    private AuthorProperties authorProperties;

    /**
     * 检查作者是否存在
     *
     * @param id
     * @return 存在返回true
     */
    public boolean checkAccountExist(int id) {
        return authorAccountDao.getAccountById(id) != null;
    }

    /**
     * 检查文章类别是否存在
     *
     * @param authorId
     * @param categoryId 类别id
     * @return 存在时返回true
     */
    public boolean checkAuthorArticleCategoryExist(int authorId, int categoryId) {
        return articleCategoryDao.getCategory(authorId, categoryId) != null;
    }

    /**
     * 检查作者是否有权限操纵（新增，更新，删除）某些类别图片
     *
     * @param authorId
     * @param category  图片类别
     * @return 可以操纵返回true
     */
    public boolean checkAuthorImageLegal(int authorId, int category) {
        int imageManagerId = authorProperties.getImageManagerAuthorId();

        // 图片管理者可以操作任何类别图片,非图片管理者不能操作系统默认图片
        return authorId == imageManagerId || !AuthorImageCategoryEnum.isDefaultImageCategory(category);
    }

    /**
     * 检查当前作者是否登录
     *
     * @param authorId
     * @return 登录返回true
     */
    public boolean checkAuthorSignIn(HttpServletRequest request, Integer authorId) {

        // 检查当前登录否
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(authorProperties.getSessionNameOfAuthorId());

        return authorId.equals(obj);

    }

    /**
     * 检查是否有指定图片
     *
     * @param imageId  图片id
     * @return 有返回true
     */
    public boolean checkAuthorImageExist( int imageId) {
        AuthorImage image = authorImageDao.getImageById(imageId);
        return (image != null );
    }

    /**
     * 注册时检查邮箱合法性
     *
     * @param email 邮箱
     * @return 合法返回true
     */
    public boolean checkEmail(String email) {
        if (StringUtils.isEmpty_(email)) return false;

        // UPDATE: 2018/2/2 更新 当前版本对邮箱（如格式）不做限制
        return true;
    }

    /**
     * 校验密码：6-12 为，由字母和数字组成
     *
     * @param password 密码
     * @return 合法返回true
     */
    public boolean checkPassword(String password) {
        if (StringUtils.isEmpty_(password)) return false;

        String regex = "^(?:(?=.*[A-z])(?=.*[0-9])).{6,12}$";
        return password.matches(regex);
    }

    /**
     * 检查主页个人信息栏位置，0 在左，1 在右
     *
     * @param mainPageNavPos 位置
     * @return 非 0 或 1 时返回 false
     */
    public boolean checkMainPageNavPos(int mainPageNavPos) {
        return mainPageNavPos == 0 || mainPageNavPos == 1;
    }
}
