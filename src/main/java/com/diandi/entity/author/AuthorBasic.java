package com.diandi.entity.author;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shangmingyu
 * @Description: 作者个人基本信息
 * @date 2018/5/6 14:27
 */
@Data
public class AuthorBasic implements Serializable {
    private static final long serialVersionUID = 8399806880631037893L;

    //articleId
    private Integer id;

    //作者id
    private Integer authorId;

    //作者头像
    private Integer profileId;

    //作者昵称
    private String nickName;

    //电话
    private String phone;


    //一句话简介
    private String intro;
}
