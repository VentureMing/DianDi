package com.diandi.dao.author;

import com.diandi.dao.BaseDao;
import com.diandi.entity.author.AuthorSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 21:35
 */
@Repository
public interface AuthorSettingDao extends BaseDao<AuthorSetting> {

    /**
     * 查询作者设置
     *
     * @param authorId
     * @return 查询结果
     */
    AuthorSetting getSetting(int authorId);

    /**
     * 更新主页个人信息栏位置
     *
     * @param authorId
     * @param pos       0为左，1为右
     * @return 影响行数
     */
    int updateMainPageNavPos(@Param("authorId") int authorId,
                             @Param("pos") int pos);
}
