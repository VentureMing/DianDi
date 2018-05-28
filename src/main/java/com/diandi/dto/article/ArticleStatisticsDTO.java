package com.diandi.dto.article;

import com.diandi.dto.author.AuthorDTO;
import com.diandi.entity.article.ArticleCategory;
import com.diandi.entity.article.ArticleStatistics;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created on 2017/12/19.
 * 博文详细信息（包括统计信息），用于在单独的页面中查看博文的统计信息
 *
 * @author DuanJiaNing
 */
@Data
public class ArticleStatisticsDTO implements Serializable {

    private static final long serialVersionUID = 1258582482361980014L;

    //博文id
    private int id;

    //统计信息
    private ArticleStatistics statistics;

    //博文所属类别
    private ArticleCategory[] categories;


    //状态
    private int state;

    //博文标题
    private String title;

    //博文摘要
    private String summary;

    //博文关键字
    private String[] keyWords;

    //首次发布日期
    private Timestamp releaseDate;

    //最近修改日期
    private Timestamp nearestModifyDate;

    //字数
    private int wordCount;

    //喜欢该篇文章的人
    private AuthorDTO[] likes;

    //收藏了该篇文章的人
    private AuthorDTO[] collects;

    //评论了该篇文章的人
    private AuthorDTO[] commenter;

}
