package com.diandi.service.author;


import com.diandi.entity.author.AuthorBasic;

/**
 * Created on 2017/12/14.
 * 博主个人资料服务
 *
 * @author DuanJiaNing
 */
public interface AuthorBasicService {

    /**
     * 新增博主资料
     *
     * @param authorId 博主id
     * @param profileId  博主头像id
     * @param phone     手机号
     * @param nickName     昵称
     * @param intro     一句话简介
     * @return 新纪录id
     */
    int insertAuthorBasic(int authorId, int profileId, String phone, String nickName, String intro);

    /**
     * 更新博主信息
     *
     * @param authorId  博主id
     * @param profileId   博主头像id,不改变传 -1
     * @param newPhone   新的电话号码，不改变传 null
     * @param newNickName   新的昵称，不改变传 null
     * @param newIntro   新的一句话简介，不改变传 null
     * @return 更新失败为false
     */
    boolean updateAuthorBasic(int authorId, int profileId, String newPhone, String newNickName, String newIntro);

    /**
     * 删除博主信息
     *
     * @param authorId 博主id
     * @return 删除成功为true
     */
    boolean deleteAuthorBasic(int authorId);

    /**
     * 查询博主信息
     *
     * @param authorId 博主id
     * @return 查询结果
     */
    AuthorBasic getAuthorBasic(int authorId);

    /**
     * 通过电话号码获得资料
     *
     * @param phone 电话号码
     * @return 结果
     */
    AuthorBasic getAuthorBasicByPhone(String phone);

    AuthorBasic getAuthorBasicByNickName(String nickName);
}
