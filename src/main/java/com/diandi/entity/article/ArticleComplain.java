package com.diandi.entity.article;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 文章投诉
 * @date 2018/5/6 21:12
 */
@Data
public class ArticleComplain implements Serializable {

    private static final long serialVersionUID = 3482853035262296456L;
    //表id
    private Integer id;

    //投诉的文章id
    private Integer articleId;

    //投诉者id
    private Integer complainerId;

    //投诉内容
    private String content;

    //投诉时间
    private Timestamp time;
}
