package com.diandi.dao.author;

import com.diandi.entity.author.AuthorLink;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 21:34
 */
@Repository
public interface AuthorLinkDao {

    /**
//     * 根据博主id查询其友情链接
//     *
//     * @param bloggerId 博主id
//     * @param offset    偏移位置
//     * @param rows      行数
//     * @return 查询结果
//     */
//    List<AuthorLink> listBlogLinkByBloggerId(@Param("bloggerId") int bloggerId,
//                                              @Param("offset") int offset,
//                                              @Param("rows") int rows);
//
//    /**
//     * 检查链接是否存在
//     *
//     * @param linkId 链接id
//     * @return 链接id
//     */
//    Integer getLinkForCheckExist(int linkId);
//
//    /**
//     * 根据id查询链接
//     *
//     * @param linkId articleId
//     * @return 查询结果
//     */
//    AuthorLink getLink(int linkId);
//
//    /**
//     * 统计博主的链接数量
//     *
//     * @param bloggerId 博主id
//     * @return 统计结果
//     */
//    int countLinkByBloggerId(int bloggerId);
}
