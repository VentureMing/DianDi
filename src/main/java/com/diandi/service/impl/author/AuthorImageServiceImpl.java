package com.diandi.service.impl.author;


import com.diandi.dao.author.AuthorImageDao;
import com.diandi.entity.author.AuthorImage;
import com.diandi.exception.internal.InternalLuceneIOException;
import com.diandi.manager.ImageManager;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.service.author.AuthorImageService;
import com.diandi.util.common.CollectionUtils;
import com.diandi.util.common.ImageUtils;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.AuthorImageCategoryEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorImageServiceImpl implements AuthorImageService {

    @Autowired
    private AuthorImageDao imageDao;

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private AuthorProperties authorProperties;

    @Override
    public int insertImage(int authorId, String path, String describe, AuthorImageCategoryEnum category, String title) {
        AuthorImage image = new AuthorImage();
        image.setDescribe(describe);
        image.setAuthorId(authorId);
        image.setImageCategory(category.getCode());
        image.setPath(path);
        image.setTitle(title);
        int effect = imageDao.insert(image);
        if (effect <= 0) return -1;

        return image.getImageId();
    }

    @Override
    public int insertImage(MultipartFile file, int authorId, String describe, AuthorImageCategoryEnum category,
                             String title) {

        int cate = category.getCode();
        String path;

        //保存到磁盘
        try {
            path = imageManager.saveImageToDisk(file, authorId, cate);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        if (path == null) return -1;

        // 如果是图片管理员上传默认图片，需要移动其文件夹
        int pictureManagerId = authorProperties.getImageManagerAuthorId();
        if (pictureManagerId == authorId && AuthorImageCategoryEnum.isDefaultImageCategory(cate)) {
            // 如果设备上已经有该唯一图片，将原来的图片移到私有文件夹，同时修改数据库
            removeDefaultImageIfNecessary(authorId, category);
        }

        //插入新纪录
        String ti = StringUtils.isEmpty(title) ? ImageUtils.getImageName(file.getOriginalFilename()) : title;
        return this.insertImage(authorId, path, describe, category, ti);

    }

    @Override
    public int insertImage(byte[] bs, int authorId, String name, String describe, AuthorImageCategoryEnum category, String title) {

        int cate = category.getCode();
        String path;

        //保存到磁盘
        try {
            path = imageManager.saveImageToDisk(bs, name, authorId, cate);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        if (path == null) return -1;

        // 如果是图片管理员上传默认图片，需要移动其文件夹
        int pictureManagerId = authorProperties.getImageManagerAuthorId();
        if (pictureManagerId == authorId && AuthorImageCategoryEnum.isDefaultImageCategory(cate)) {
            // 如果设备上已经有该唯一图片，将原来的图片移到私有文件夹，同时修改数据库
            removeDefaultImageIfNecessary(authorId, category);
        }

        //插入新纪录
        String ti = StringUtils.isEmpty(title) ? ImageUtils.getImageName(name) : title;
        return this.insertImage(authorId, path, describe, category, ti);

    }

    /*
     * 腾地方
     * 移到默认图片到私有图片文件夹，同时修改数据库记录
     * @param authorId 博主id
     */
    private void removeDefaultImageIfNecessary(int authorId, AuthorImageCategoryEnum defaultCate) {
        AuthorImage unique = imageDao.getAuthorUniqueImage(authorId, defaultCate.getCode());

        if (unique != null) {
            try {
                //移动默认图片到私有类别图片所在文件夹
                String newPath = imageManager.moveImage(unique, authorId, AuthorImageCategoryEnum.PRIVATE);

                //更新数据库记录
                unique.setImageCategory(AuthorImageCategoryEnum.PRIVATE.getCode());
                unique.setPath(newPath);
                imageDao.update(unique);

            } catch (IOException e) {
                e.printStackTrace();
                // 移动文件出错，文件移动情况未知，麻烦大了
                // MAYBUG 回滚数据库操作，但磁盘操作无法预料，也无法处理
                throw new InternalLuceneIOException(e);
            }
        }

    }

    @Override
    public boolean deleteImage(int authorId, int imageId, boolean deleteOnDisk) {

        AuthorImage image = getImage(imageId);

        // 对默认图片，图片管理员只能以更新（上传）的方式删除图片，因为这些图片必须时刻存在
        int pictureManagerId = authorProperties.getImageManagerAuthorId();
        int cate = image.getImageCategory();
        if (authorId == pictureManagerId && AuthorImageCategoryEnum.isDefaultImageCategory(cate))
            return false;

        //删除数据库记录
        String path = image.getPath();
        int effect = imageDao.delete(imageId);
        if (effect <= 0) return false;

        if (deleteOnDisk) {
            //删除磁盘文件
            boolean succ = imageManager.deleteImageFromDisk(path);
            // 删除失败时抛出异常，使数据库事务回滚
            if (!succ) throw new InternalLuceneIOException();
        }

        return true;
    }

    @Override
    public AuthorImage getImage(int imageId) {
        return imageDao.getImageById(imageId);
    }

    @Override
    public AuthorImage  getImage(int imageId, int authorId) {
        AuthorImage  image = imageDao.getImageById(imageId);
        if (image == null || !image.getAuthorId().equals(authorId)) return null;

        return image;
    }

    public AuthorImage getDefaultImage(AuthorImageCategoryEnum category) {
        return imageDao.getAuthorUniqueImage(authorProperties.getImageManagerAuthorId(),
                category.getCode());
    }

    public ResultBean<List<AuthorImage >> listAuthorImage(int authorId, AuthorImageCategoryEnum category,
                                                          int offset, int rows) {

        List<AuthorImage> result;
        if (category == null) {
            result = imageDao.listImageByAuthorId(authorId, offset, rows);
        } else {
            result = imageDao.listImageByAuthorAndCategory(authorId, category.getCode(), offset, rows);
        }

        if (CollectionUtils.isEmpty(result)) return null;

        return new ResultBean<>(result);
    }

    @Override
    public boolean updateImage(int imageId, AuthorImageCategoryEnum category, String describe, String title) {

        AuthorImage oldImage = imageDao.getImageById(imageId);

        // 修改设备上图片路径，如果需要的话
        String newPath = null;
        if (category != null && category.getCode() != oldImage.getImageCategory()) { // 修改了类别
            int authorId = oldImage.getAuthorId();
            try {

                int imageManagerId = authorProperties.getImageManagerAuthorId();
                // 如果为图片管理员在操作
                if (imageManagerId == authorId) {

                    // 以下两种情况将更新失败，对于默认图片，且图片管理员在操作的情况下，要修改类别或删除图片，只能
                    // 以 普通 -> 默认 的修改方向替换图片，因为这些图片必须时刻存在

                    // 1 目标类别是默认类别，原先类别也为默认类别      默认 -> 默认
                    // 2 目标类别为普通类别，原先类别为默认类别        默认 -> 普通

                    int oldCategory = oldImage.getImageCategory();
                    if ((AuthorImageCategoryEnum.isDefaultImageCategory(oldCategory) &&
                            AuthorImageCategoryEnum.isDefaultImageCategory(category.getCode())) ||
                            (AuthorImageCategoryEnum.isDefaultImageCategory(oldCategory) &&
                                    !AuthorImageCategoryEnum.isDefaultImageCategory(category.getCode()))) {
                        return false;
                    } else {

                        //腾位置，如果需要的话
                        removeDefaultImageIfNecessary(authorId, category);

                        //移动到目标目录
                        newPath = imageManager.moveImage(oldImage, authorId, category);
                    }

                } else {
                    // 不是图片管理员则只需移动文件即可
                    newPath = imageManager.moveImage(oldImage, authorId, category);
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new InternalLuceneIOException(e);
            }
        }

        AuthorImage newPicture = new AuthorImage();
        newPicture.setDescribe(describe);
        newPicture.setAuthorId(oldImage.getAuthorId());
        newPicture.setImageCategory(category == null ? oldImage.getImageCategory() : category.getCode());
        newPicture.setImageId(oldImage.getImageId());
        newPicture.setTitle(title);
        newPicture.setPath(newPath == null ? oldImage.getPath() : newPath);

        int effect = imageDao.update(newPicture);
        if (effect <= 0) return false;

        return true;
    }

    @Override
    public void cleanArticleImage(int authorId) {
    }

}
