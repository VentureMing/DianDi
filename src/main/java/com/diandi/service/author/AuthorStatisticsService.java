package com.diandi.service.author;

import com.diandi.dto.author.AuthorDTO;
import com.diandi.dto.author.AuthorStatisticsDTO;
import com.diandi.util.restful.ResultBean;
import org.springframework.stereotype.Service;


/**
 * Created on 2018/1/17.
 * 博主统计信息服务
 *
 * @author DuanJiaNing
 */

public interface AuthorStatisticsService {

    /**
     * 获取作者统计信息
     *
     * @param authorId 作者id
     * @return 查询结果
     */
    ResultBean<AuthorStatisticsDTO> getAuthorStatistics(int authorId);

    /**
     * 根据 articleId 获取作者的 dto 对象
     * @param ids 作者 articleId
     * @return 数组
     */
    AuthorDTO[] listAuthorDTO(int... ids);

}
