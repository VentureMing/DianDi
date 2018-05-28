package com.diandi.dao.author;

import com.diandi.dao.BaseDao;
import com.diandi.entity.author.AuthorImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 21:35
 */
@Repository
public interface AuthorImageDao extends BaseDao<AuthorImage> {

    /**
     * 通过id查询图片
     *
     * @param imageId 图片id
     * @return 查询结果
     */
    AuthorImage getImageById(int imageId);

//    /**
//     * 根据作者某类别下的所有图片（后续修改）
//     *
//     * @param authorId
//     * @param imageCategory  类别
//     * @return 查询结果
//     */
//    AuthorImage getImageByAuthorAndCategory(@Param("authorId") int authorId, @Param("category") int imageCategory);

    /**
     * 获取作者唯一的图片，如图片管理员管理的默认图片，头像。
     *
     * @param authorId
     * @param category
     * @return 查询结果
     */
    AuthorImage getAuthorUniqueImage(@Param("authorId") int authorId ,
                                        @Param("category") int category);

    /**
     * 查询所有图片
     *
     * @param authorId
     * @param offset    偏移位置
     * @param rows      行数
     * @return 查询结果
     */
    List<AuthorImage> listImageByAuthorId (@Param("authorId ") int authorId ,
                                             @Param("offset") int offset,
                                             @Param("rows") int rows);

    /**
     * 根据authorId获得其所有图片
     * @param authorId
     * @return 查询结果
     */
    List<AuthorImage> getImageByAuthorId(int authorId);

    /**
     * 查询指定类别图片
     *
     * @param authorId
     * @param category  类别id
     * @param offset    偏移位置
     * @param rows      行数
     * @return 查询结果
     */
    List<AuthorImage> listImageByAuthorAndCategory(@Param("authorId") int authorId,
                                                      @Param("category") int category,
                                                      @Param("offset") int offset,
                                                      @Param("rows") int rows);

    /**
     * 将图片被引用次数减一
     *
     * @param imageId 图片id
     * @return 操作影响的行数
     */
    int updateUseCountPlus(int imageId);

    /**
     * 将图片被引用次数加一
     *
     * @param imageId 图片id
     * @return 操作影响的行数
     */
    int updateUseCountMinus(int imageId);

    /**
     * 获得图片被引用次数
     *
     * @param imageId 图片id
     * @return 操作影响的行数
     */
    int getUseCount(int imageId);
}
