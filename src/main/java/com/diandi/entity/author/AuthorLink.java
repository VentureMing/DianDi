package com.diandi.entity.author;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 20:40
 */
@Data
public class AuthorLink implements Serializable {
    private static final long serialVersionUID = 6022440431325193229L;

    // articleId
    private Integer id;

    //博主id
    private Integer authorId;

    //图片id
    private Integer imageId;

    //标题
    private String title;

    //url
    private String url;

    //描述
    private String describe;
}
