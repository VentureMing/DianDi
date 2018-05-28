package com.diandi.dto.author;

import com.diandi.entity.author.AuthorBasic;
import com.diandi.entity.author.AuthorImage;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 作者资料
 * @date 2018/4/6 13:59
 */
@Data
public class AuthorDTO implements Serializable {
    private static final long serialVersionUID = -6382212838218236774L;

    //articleId
    private int id;

    //个人资料
    private AuthorBasic authorBasic;

    //作者头像（需要单独从相册中查询）
    private AuthorImage profileImage;

    //邮箱
    private String authorEmail;

    //注册时间
    private Timestamp registerDate;
}
