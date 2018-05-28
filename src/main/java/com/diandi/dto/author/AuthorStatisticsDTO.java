package com.diandi.dto.author;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shangmingyu
 * @Description: 作者信息统计
 * @date 2018/5/6 13:54
 */
@Data
public class AuthorStatisticsDTO implements Serializable {
    private static final long serialVersionUID = 3106538799406885406L;

    //发表的文章数
    private int articleCount;

    //总字数
    private long wordCount;

    //收获的喜欢数
    private long likeCount;

    //送出的喜欢数
    private long likedCount;

    //创建的文章类别数
    private int categoryCount;

    //创建的标签数-----关注内容
    private int labelCount;

    //收藏的文章数
    private int collectCount;

    //文章被收藏数
    private int collectedCount;


}
