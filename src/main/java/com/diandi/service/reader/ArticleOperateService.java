package com.diandi.service.reader;

/**
 * Created on 2017/12/26.
 * 读者对文章可进行的操作服务
 * <p>
 * 1. 评论
 * 2. 分享
 * 3. 赞赏
 * 4. 收藏
 * 5. 投诉
 *
 * @author DuanJiaNing
 */
public interface ArticleOperateService {

    /**
     * 文章分享次数加一
     *
     * @param articleId   文章id
     * @param sharerId 分享者id
     * @return 文章被分享的次数
     */
    int insertShare(int articleId, int sharerId);

    /**
     * 新增投诉记录，同时文章投诉次数加一
     *
     * @param articleId    文章id
     * @param complainId 投诉者id
     * @param content    投诉内容
     * @return 新纪录id
     */
    int insertComplain(int articleId, int complainId, String content);

    /**
     * 新增文章收藏记录，同时文章收藏次数加一
     *
     * @param articleId      文章id
     * @param collectorId 收藏者id
     * @return 新纪录id
     */
    int insertCollect(int articleId, int collectorId);

    /**
     * 取消文章收藏，同时文章收藏数减一
     *
     * @param authorId 收藏者id
     * @param articleId    收藏的文章id
     * @return 删除成功返回true
     */
    boolean deleteCollect(int authorId, int articleId);

    /**
     * 新增喜欢记录，同时文章喜欢数加一
     *
     * @param articleId  文章id
     * @param likerId 给出喜欢的人的id，为游客时传-1
     * @return 文章喜欢数
     */
    int insertLike(int articleId, int likerId);

    /**
     * 取消喜欢，同时文章喜欢数减一
     *
     * @param likerId 喜欢者id
     * @param articleId 文章文章id
     * @return 删除成功返回true
     */
    boolean deleteLike(int likerId, int articleId);

}
