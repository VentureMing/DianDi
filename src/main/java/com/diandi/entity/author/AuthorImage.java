package com.diandi.entity.author;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shangmingyu
 * @Description: 作者上传照片信息
 * @date 2018/4/6 14:36
 */
@Data
public class AuthorImage implements Serializable{
    private static final long serialVersionUID = -7096750415002044430L;

    //articleId
    private Integer imageId;

    //所属博主id
    private Integer authorId;

    //描述
    private String describe;

    //类别（后续修改）
    private Integer imageCategory;

    //保存路径/url
    private String path;

    //名字
    private String title;

    //上传时间
    private Timestamp uploadDate;

    // 图片被引用次数(后续修改)
    private Integer useCount;
}
