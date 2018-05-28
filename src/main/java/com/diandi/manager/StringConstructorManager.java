package com.diandi.manager;


import com.diandi.entity.author.AuthorImage;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.manager.properties.WebSiteProperties;
import com.diandi.util.enums.AuthorImageCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created on 2018/1/2.
 * 字符拼接管理者
 *
 * @author DuanJiaNing
 */
@Component
public class StringConstructorManager {

    @Autowired
    private WebSiteProperties websiteProperties;

    @Autowired
    private AuthorProperties propertiesManager;

    /**
     * 构造图片的访问url
     *
     * @param image     图片
     * @param defaultCate 图片无法获取时使用的默认图片所属类别
     * @return 获取图片的url
     */
    public String constructImageUrl(AuthorImage image, AuthorImageCategoryEnum defaultCate) {
        if (image == null) return null;

        // 参见ImageController：http://localhost:8080/image/1/type=public/523?default=5
        StringBuilder buffer = new StringBuilder(50);
        int cate = image.getImageCategory();
        buffer.append("http://")
                .append(websiteProperties.getAddr())
                .append("/image/")
                .append(image.getAuthorId())
                .append("/type=")
                // 私有图片登录才能获取
                .append(cate == AuthorImageCategoryEnum.PRIVATE.getCode() ? "private" : "public")
                .append("/")
                .append(image.getImageId())
                .append("?default=")
                .append(defaultCate == null ? AuthorImageCategoryEnum.DEFAULT_IMAGE.getCode() :
                        defaultCate.getCode());

        return buffer.toString();
    }

    /**
     * 拼接图片保存位置所在文件夹路径
     *
     * @param bloggerName  博主名
     * @param categoryName 图片所属类别名
     * @return 文件夹路径
     */
    public String constructImageDirPath(String bloggerName, String categoryName) {
        String rootDirPath = propertiesManager.getAuthorImageRootPath();
        return rootDirPath + File.separator + bloggerName + File.separator + categoryName;
    }
}
