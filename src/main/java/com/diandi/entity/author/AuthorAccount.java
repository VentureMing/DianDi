package com.diandi.entity.author;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 作者账户信息
 * @date 2018/4/6 14:16
 */
@Data
public class AuthorAccount implements Serializable{

    private static final long serialVersionUID = 4471470536311738053L;

    //articleId
    private Integer authorId;

    //用户名
    private String authorEmail;

    //密码
    private String authorPassword;

    //注册时间
    private Timestamp registerDate;
}
