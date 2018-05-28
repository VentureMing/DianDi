package com.diandi.service.author;


import com.diandi.entity.author.AuthorAccount;

/**
 * Created on 2017/12/14.
 * 针对博主账户的业务
 * <p>
 * 1 新增账户
 * 2 根据用户名或密码获取博主账户，可用于 登陆/注册 时校验用户
 * 3 根据博主id获取博主账户
 * 4 更新账户信息
 *
 * @author DuanJiaNing
 */
public interface AuthorAccountService {

    /**
     * 新增账户
     *
     * @param email
     * @param password 密码
     * @return 博主id
     */
    int insertAccount(String email, String password);

    /**
     * 根据id获取账户--------------------------无用
     *
     * @param authorId 博
     * @return 查询结果
     */
    AuthorAccount getAccountById(int authorId);

    /**
     * 根据注册邮箱获取账户
     *
     * @param authorEmail
     * @return 查询结果
     */
    AuthorAccount getAccountByEmail(String authorEmail);

    /**
     * 删除账号
     *
     * @param authorId 博主id
     * @return 上传成功返回true
     */
    boolean deleteAccount(int authorId);

    /**
     * 更新用户名(昵称  需要修改)-------------无用删除
     *
     * @param authorId   博主id
     * @param newUserName 新的用户名
     * @return 更新成功返回true
     */
    boolean updateAccountUserName(int authorId, String newUserName);

    /**
     * 更新密码
     *
     * @param authorId   博主id
     * @param oldPassword 旧密码
     * @param newPassword 新的密码
     * @return 更新成功返回true
     */
    boolean updateAccountPassword(int authorId, String oldPassword, String newPassword);

    /**
     * 根据电话号码获得账户
     *
     * @param phone 电话号码
     * @return 结果
     */
    AuthorAccount getAccountByPhone(String phone);
}
