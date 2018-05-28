package com.diandi.entity.article;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 文章类别
 * @date 2018/5/6 21:01
 */
@Data
public class ArticleCategory implements Serializable {
    private static final long serialVersionUID = -5574665896661761143L;

    //articleId
    private Integer categoryId;

    //类别所属博主id
    private Integer authorId;

    //类别图标
    private Integer imageId;

    //类别标题
    private String title;

    //类别描述
    private String describe;

    //类别创建时间
    private Timestamp createDate;

}
