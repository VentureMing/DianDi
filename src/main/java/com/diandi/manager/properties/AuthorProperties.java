package com.diandi.manager.properties;

import lombok.Data;

/**
 * @author shangmingyu
 * @Description: 作者参数配置
 * @date 2018/4/6 19:14
 */
@Data
public class AuthorProperties {


//    /**
//     * 作者友情链接默认请求条数
//     */
//    private Integer requestAuthorLinkCount;

    /**
     * 作者收藏文章默认请求条数
     */
    private Integer requestAuthorCollectCount;

    /**
     * 作者文章类别默认请求条数
     */
    private Integer requestAuthorArticleCategoryCount;

    /**
     * 作者获取文章列表时的默认获取条数
     */
    private Integer requestArticleListCount;

    /**
     * 作者相册图片默认请求数量
     */
    private Integer requestAuthorImageCount;

    /**
     * 作者图片保存根路径
     */
    private String authorImageRootPath;

    /**
     * 拥有唯一图片管理权限的作者的id
     */
    private Integer imageManagerAuthorId;

    /**
     * 默认的文章收藏类别
     */
    private Integer defaultArticleCollectCategory;

    /**
     * 保存在session属性中的作者id对应的名字
     */
    private String sessionNameOfAuthorId;

    /**
     * 保存在session属性中的作者对应的名字
     */
    private String sessionNameOfAuthorNickName;

    /**
     * 保存在session属性中的错误信息对应的名字
     */
    private String sessionNameOfErrorMsg;

    /**
     * 保存在session属性中的页面所属作者id
     */
    private String pageOwnerAuthorId;

    /**
     * 保存在session属性中的页面所属作者name
     */
    private String pageOwnerAuthorNickName;

    /**
     * 保存在session属性中的作者登录标识，有值（任意值）就表示已登录
     */
    private String sessionAuthorLoginSignal;






    /**
     * 文章标签默认请求条数
     */
    private Integer requestAuthorArticleLabelCount;

    /**
     * 默认的文章收藏类别
     */
    private Integer mainPageNavPos;

    /**
     * 批量导入文章时临时 zip 文件路径
     */
    private String patchImportArticleTempPath;

    /**
     * 批量下载时临时 zip 文件和 md/html 文件路径
     */
    private String patchDownloadArticleTempPath;
}
