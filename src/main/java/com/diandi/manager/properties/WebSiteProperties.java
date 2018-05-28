package com.diandi.manager.properties;

import lombok.Data;

/**
 * @author shangmingyu
 * @Description: 站点参数配置
 * @date 2018/5/6 15:26
 */
@Data
public class WebSiteProperties {
    /**
     * 站点域名
     */
    private String addr;

    /**
     * lucene生成的索引保存路径
     */
    private String luceneIndexDir;

    /**
     * 默认的url请求参数的间隔字符
     * 如url中传递多个文章类别id时：1,2,3,8 这里间隔字符为","
     */
    private String urlConditionSplitCharacter;

    /**
     * 网站管理者的邮箱
     */
    private String manageEmailAddress;

    /**
     * 配置了smtp 的邮件发送者
     */
    private String mailSenderAddress;

    /**
     * 配置了smtp 的邮件发送者的授权码
     */
    private String mailSenderPassword;

    /**
     * 默认获取活跃作者数
     */
    private Integer webSiteActiveAuthorCount;
}
