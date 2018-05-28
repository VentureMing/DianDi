package com.diandi.entity.article;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/23 0:15
 */
@Data
public class ArticleAndCategory implements Serializable {

    private static final long serialVersionUID = -4436650369551046178L;

    private Integer id;
    //文章id
    private Integer articleId;
    //类别id
    private Integer categoryId;

}
