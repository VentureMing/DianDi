package com.diandi.dao.author;

import com.diandi.dao.BaseDao;
import com.diandi.entity.author.AuthorBasic;
import org.springframework.stereotype.Repository;

/**
 * @author shangmingyu
 * @Description: 获取作者个人基本信息
 * @date 2018/5/6 21:33
 */
@Repository
public interface AuthorBasicDao extends BaseDao<AuthorBasic> {

    /**
     * 根据authorId获得作者信息
     *
     * @param authorId 作者id
     * @return 查询结果
     */
    AuthorBasic getBasicInfoByAuthorId(int authorId);

    /**
     * 根据电话查询作者信息
     *
     * @param phone 电话
     * @return 结果
     */
    AuthorBasic getBasicInfoByPhone(String phone);


    AuthorBasic getBasicInfoByNickName(String nickName);
}
