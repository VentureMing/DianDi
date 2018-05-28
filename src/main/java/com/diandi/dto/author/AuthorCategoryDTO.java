package com.diandi.dto.author;


import com.diandi.entity.author.AuthorImage;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 作者创建的类别
 * @date 2018/4/6 13:49
 */
@Data
public class AuthorCategoryDTO implements Serializable {

    private static final long serialVersionUID = -7413640669767387180L;

    //articleId
    private int id;

    //类别所属作者id
    private int authorId;

    // 类别对应的文章数量
    private int count;

    //类别标题
    private String title;

    //类别描述
    private String describe;

    //类别创建时间
    private Timestamp createDate;

    //类别对应图标
    private AuthorImage image;

}
