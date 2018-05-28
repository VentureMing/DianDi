package com.diandi.dao.author;

import com.diandi.dao.BaseDao;
import com.diandi.entity.author.AuthorAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: 作者账户相关操作
 * @date 2018/4/6 21:33
 */
@Repository
public interface AuthorAccountDao extends BaseDao<AuthorAccount> {

    /**
     * 根据用户名查询账户
     *
     * @param authorEmail 用户名
     * @return 查询结果
     */
    AuthorAccount getAccountByEmail(String authorEmail);

    /**
     * 根据authorId查询账户
     *
     * @param authorId 作者id
     * @return 查询结果
     */
    AuthorAccount getAccountById(int authorId);

    /**
     * 获取id
     * @param count 获取活跃用户个数
     * @return id数组
     */
    // UPDATE: 2018/5/1 更新 调整活跃用户获取策略后删除
    List<Integer> listId(int count);
}
