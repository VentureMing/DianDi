package com.diandi.entity.article;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 21:19
 */
@Data
public class ArticleStatistics implements Serializable {

    private static final long serialVersionUID = 3495475155109432589L;

    // 表id
    private Integer id;

    //对应文章id
    private Integer articleId;

    //评论次数
    private Integer commentCount;

    //文章浏览次数
    private Integer viewCount;

    //作者回复该文章评论的次数
    private Integer replyCommentCount;

    //文章被收藏次数
    private Integer collectCount;

    //文章举报次数
    private Integer complainCount;

    //文章被分享次数
    private Integer shareCount;

    //赞赏次数
    private Integer admireCount;

    //喜欢次数
    private Integer likeCount;

    //发布日期
    private Timestamp releaseDate;

}
