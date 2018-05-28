package com.diandi.dto.article;


import com.diandi.entity.article.ArticleCategory;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author shangmingyu
 * @Description: 作者文章列表项，用于作者查看自己发布的文章列表
 * @date 2018/5/6 13:54
 */
@Data
public class ArticleListItemDTO implements Serializable {

    private static final long serialVersionUID = -2446239587924752480L;

    //文章id
    private int articleId;
    //作者昵称
    private String nickName;

//    //所属类别
//    private ArticleCategory[] categories;

    //标题
    private String title;

    //图片
    private String image;

    //摘要
    private String summary;

    //首次发布日期
    private Timestamp releaseDate;

    //评论次数
    private int commentCount;

    //浏览次数
    private int viewCount;

    //被收藏次数
    private int collectCount;

    //喜欢次数
    private int likeCount;

}
