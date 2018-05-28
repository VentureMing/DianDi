package com.diandi.dto.article;

import com.diandi.entity.article.ArticleCategory;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created on 2017/12/14.
 * 文章主体内容
 *
 * @author DuanJiaNing
 */
@Data
public class ArticleMainContentDTO implements Serializable {

    private static final long serialVersionUID = -9215772012725472707L;

    //文章id
    private int articleId;

    //文章所属作者id
    private int authorId;
    //文章标题
    private String title;

    //文章主体内容
    private String content;

    //文章摘要
    private String summary;

    //文章关键字
    private String[] keyWords;

    //首次发布日期
    private Timestamp releaseDate;

    //最后一次修改时间
    private Timestamp nearestModifyDate;

    //文章状态
    private int status;

    //总字数
    private int wordCount;

}
