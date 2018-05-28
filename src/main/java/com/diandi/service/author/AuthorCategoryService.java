package com.diandi.service.author;

import com.diandi.dto.author.AuthorCategoryDTO;
import com.diandi.util.restful.ResultBean;



import java.util.List;

/**
 * Created on 2017/12/18.
 * 博主博文类别服务
 *
 * @author DuanJiaNing
 */
public interface AuthorCategoryService {

    /**
     * 获取博主创建的博文类别，按时间倒序排序
     *
     * @param authorId 博主id
     * @param offset    结果集起始位置
     * @param rows      行数
     * @return 查询结果
     */
    ResultBean<List<AuthorCategoryDTO>> listArticleCategory(int authorId, int offset, int rows);

    /**
     * 修改类别
     *
     * @param authorId  博主id
     * @param categoryId 类别id
     * @param newIconId  新的类别图标，不修改传 -1
     * @param newTitle   新标题，不修改传 null
     * @param newBewrite 新描述，不修改传 null
     * @return 更新失败为false
     */
    boolean updateArticleCategory(int authorId, int categoryId, int newIconId, String newTitle, String newBewrite);

    /**
     * 新增博文类别
     * 若指定了类别图标，将图标对应图片修改为“公开”
     *
     * @param authorId 类别所属博主id
     * @param iconId    类别标签id，为null传递-1
     * @param title     标题
     * @param desc      描述
     * @return 新纪录id
     */
    int insertArticleCategory(int authorId, int iconId, String title, String desc);

    /**
     * 删除类别，同时移动类别下所有文章到新的类别中
     *
     * @param authorId     博主id
     * @param categoryId    要删除的类别
     * @param newCategoryId 新的类别（新类别创建者创建的类别），不修改传递-1
     */
    boolean deleteCategoryAndMoveArticlesTo(int authorId, int categoryId, int newCategoryId);

    /**
     * 根据id获得指定博文类别
     *
     * @param authorId  博主id
     * @param categoryId 博文类别id
     * @return 查询结果
     */
    AuthorCategoryDTO getCategory(int authorId, int categoryId);
}
