package com.diandi.entity.article;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 文章喜欢记录
 * @date 2018/5/6 21:15
 */
@Data
public class ArticleLike implements Serializable {
    private static final long serialVersionUID = -514086295499763845L;

    //记录id
    private Integer id;

    //文章id
    private Integer articleId;

    //给出喜欢的人的id
    private Integer likerId;

    //喜欢时间
    private Timestamp likeDate;
}
