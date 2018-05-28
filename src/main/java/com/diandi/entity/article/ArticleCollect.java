package com.diandi.entity.article;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 收藏文章
 * @date 2018/4/6 21:03
 */
@Data
public class ArticleCollect implements Serializable {

    private static final long serialVersionUID = -922624864349188990L;
    // 记录id
    private Integer collectId;

    // 文章id
    private Integer articleId;

    //收藏者id
    private Integer collectorId;



    //收藏时间
    private Timestamp collectDate;

}
