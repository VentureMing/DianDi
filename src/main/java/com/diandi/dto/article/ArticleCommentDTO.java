package com.diandi.dto.article;

import com.diandi.dto.author.AuthorDTO;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 文章评论
 * @date 2018/5/6 21:30
 */
@Data
public class ArticleCommentDTO implements Serializable {

    private static final long serialVersionUID = 7217522025154588809L;

    // 评论id
    private int id;

    // 博文id
    private int articleId;

    // 评论者
    private AuthorDTO commentator;

    // 被评论者
//    private BloggerDTO listener;

    // 评论内容
    private String content;

    // 评论时间
    private Timestamp releaseDate;

    // 评论状态
    private int state;

}
