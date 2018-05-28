package com.diandi.dto.author;


import com.diandi.dto.article.ArticleListItemDTO;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 作者收藏的文章清单
 * @date 2018/4/6 13:49
 */
@Data
public class FavouriteArticleListItemDTO implements Serializable {

    private static final long serialVersionUID = 1348316821909506029L;

    // 记录id
    private int id;

    //作者id
    private int authorId;

    // 文章内容
    private ArticleListItemDTO article;

    //作者id
    private AuthorDTO author;


    //收藏/喜欢时间
    private Timestamp date;

}
