package com.diandi.dao;

import com.diandi.entity.author.AuthorSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author shangmingyu
 * @Description: Dao层数据库操作的最上层约定,增，删，改
 * @date 2018/5/6 21:37
 */
@Repository
public interface BaseDao<T> {
    /**
     * 更新数据
     *
     * @param t 数据
     * @return 操作影响的行数
     */
    int update(T t);

    /**
     * 根据id删除数据
     *
     * @param id articleId
     * @return 操作影响的行数
     */
    int delete(int id);

    /**
     * 新增数据
     *
     * @param t 数据
     * @return 操作影响的行数
     */
    int insert(T t);
}
