package com.diandi.dto.author;

import com.diandi.entity.author.AuthorImage;
import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2017/12/15.
 * 博主友情链接
 *
 * @author DuanJiaNing
 */
@Data
public class AuthorLinkDTO implements Serializable {

    private static final long serialVersionUID = -558005429949054040L;

    // articleId
    private int id;

    //博主id
    private int authorId;

    //图片
    private AuthorImage icon;

    //标题
    private String title;

    //url
    private String url;

    //描述
    private String dercribe;

}
