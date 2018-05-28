package com.diandi.entity.author;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/6 20:40
 */
@Data
public class AuthorSetting implements Serializable{


    private static final long serialVersionUID = 91795394041658276L;
    // articleId
    private Integer id;

    // 作者id
    private Integer authorId;

    // 作者主页个人信息栏位置，0为左，1为右
    private Integer mainPageNavPos;
}
