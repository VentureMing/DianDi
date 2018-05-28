package com.diandi.dto.author;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shangmingyu
 * @Description: 作者简介
 * @date 2018/4/6 13:49
 */
@Data
public class AuthorBriefDTO implements Serializable {

    private static final long serialVersionUID = 2039922124890988207L;

    // 作者统计信息
    private AuthorStatisticsDTO authorStatisticsDTO;

    // 作者 dto
    private AuthorDTO authorDTO;
}
