package com.diandi.entity.article;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 文章
 * @date 2018/5/6 20:46
 */
@Data
public class Article implements Serializable{
    private static final long serialVersionUID = 632222670817946882L;

    //文章id
    private Integer articleId;

    //文章所属作者id
    private Integer authorId;

//    //文章所属类别id
//    private String categoryIds;

    //文章状态
    private Integer articleState;

    //文章标题
    private String articleTitle;

    //文章主体内容
    private String articleContentHtml;

    //文章主体内容(md格式)
    private String articleContentMd;

    //文章摘要
    private String summary;

    //首次发布日期
    private Timestamp releaseDate;

    //最后一次修改时间
    private Timestamp nearestModifyDate;

    //文章关键字
    private String keyWords;

    //总字数
    private Integer wordCount;
}
