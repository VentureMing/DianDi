package com.diandi.entity.author;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/23 9:27
 */
@Data
public class AuthorFollowed implements Serializable {

    private static final long serialVersionUID = 4522307361995463782L;

    private Integer followedId;

    private Integer followedAuthorId;

    private Integer followedCategoryId;
}
