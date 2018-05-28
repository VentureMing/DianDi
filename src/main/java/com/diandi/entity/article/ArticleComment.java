package com.diandi.entity.article;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 文章评论
 * @date 2018/5/6 21:05
 */
@Data
public class ArticleComment implements Serializable {
    private static final long serialVersionUID = -3862882347120487340L;

    //articleId
    private Integer commentId;

    //文章id
    private Integer articleId;

    //评论者id
    private Integer commentatorId;

    //被评论者id
    private Integer listenerId;

    //评论内容
    private String content;

    //评论时间
    private Timestamp releaseDate;

    //评论状态
    private Integer state;
}
