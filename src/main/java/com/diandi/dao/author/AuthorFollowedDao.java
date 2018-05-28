package com.diandi.dao.author;

import com.diandi.dao.BaseDao;
import com.diandi.entity.author.AuthorFollowed;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/23 9:30
 */
@Repository
public interface AuthorFollowedDao extends BaseDao<AuthorFollowed> {

    List<Integer> listAuthorIdsByAuthorId(@Param("authorId") Integer authorId);

    List<Integer> listCategoryIdsByAuthorId(@Param("authorId") Integer authorId);
}
