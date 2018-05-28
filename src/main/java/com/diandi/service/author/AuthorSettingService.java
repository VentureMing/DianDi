package com.diandi.service.author;


import com.diandi.entity.author.AuthorSetting;

/**
 * Created on 2018/3/26.
 *
 * @author DuanJiaNing
 */
public interface AuthorSettingService {

    /**
     * 获得博主配置
     *
     * @param authorId 博主id
     * @return 查询结果
     */
    AuthorSetting getSetting(int authorId);

    /**
     * 更新博主主页个人信息栏位置
     *
     * @param pos 0为左，1为右
     * @param authorId 博主id
     * @return 更新成功为true
     */
    boolean updateMainPageNavPos(int authorId, int pos);

}
