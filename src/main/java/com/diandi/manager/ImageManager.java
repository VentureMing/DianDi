package com.diandi.manager;


import com.diandi.dao.author.AuthorAccountDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.entity.author.AuthorAccount;
import com.diandi.entity.author.AuthorImage;
import com.diandi.exception.internal.InternalLuceneIOException;
import com.diandi.util.common.ImageUtils;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.AuthorImageCategoryEnum;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import static com.diandi.util.enums.AuthorImageCategoryEnum.PUBLIC;

/**
 * Created on 2018/1/3.
 * 图片管理
 *
 * @author DuanJiaNing
 */
@Component
public class ImageManager {

    @Autowired
    private StringConstructorManager constructorManager;

    @Autowired
    private AuthorAccountDao accountDao;

    @Autowired
    private AuthorImageDao imageDao;

    /**
     * 将图片保存到博主的对应文件夹下
     */
    public String saveImageToDisk(byte[] bs, String name, int authorId, int category) throws IOException {

        String type = ImageUtils.getImageMimeType(name);
        if (type == null) return null;

        AuthorAccount account = accountDao.getAccountById(authorId);
        String dirPath = constructorManager.constructImageDirPath(account.getAuthorEmail(),
                AuthorImageCategoryEnum.valueOf(category).name());

        File specDir = new File(dirPath);
        if (!specDir.exists() || !specDir.isDirectory()) specDir.mkdirs();

        //文件名统一添加前缀 "时间-" 以避免覆盖
        name = System.currentTimeMillis() + "-" + handleImageName(name, type);

        File image = new File(specDir.getAbsolutePath() + File.separator + name);
        if (!image.exists() || image.isDirectory()) {
            FileOutputStream stream = new FileOutputStream(image);
            stream.write(bs);
            stream.close();
        }

        return image.getAbsolutePath();
    }

    /**
     * 将图片保存到博主的对应文件夹下
     *
     * @param file      图片
     * @param authorId 博主id
     * @param category  图片类别
     * @return 路径
     */
    public String saveImageToDisk(MultipartFile file, int authorId, int category) throws IOException {
        //后缀一定要为图片类型
        String type = ImageUtils.getImageType(file);
        if (type == null) return null;

        AuthorAccount account = accountDao.getAccountById(authorId);
        String dirPath = constructorManager.constructImageDirPath(account.getAuthorEmail(),
                AuthorImageCategoryEnum.valueOf(category).name());

        File specDir = new File(dirPath);
        if (!specDir.exists() || !specDir.isDirectory()) specDir.mkdirs();

        //页面使用 <%@ page pageEncoding="utf-8" %> 指令，否则会出现文件名中文乱码
        //文件名统一添加前缀 "时间-" 以避免覆盖
        String name = System.currentTimeMillis() + "-" + handleImageName(file.getOriginalFilename(), type);

        File image = new File(specDir.getAbsolutePath() + File.separator + name);
        if (!image.exists() || image.isDirectory()) file.transferTo(image);

        return image.getAbsolutePath();
    }

    //拼接图片名，确保以文件类型为后缀
    private String handleImageName(String name, String type) {
        String[] sp = name.split("\\.");
        if (sp.length == 1) { //没有后缀
            return name + "." + type;
        } else if (sp.length == 2) { //有后缀
            if (!sp[1].equals(type)) { //后缀不正确
                return name.replace(sp[1], type);
            } else return name; //后缀正确
        } else {
            String red = name.replace(".", "");
            return red + "." + type;
        }
    }

    /**
     * 从磁盘中删除一个图片文件
     *
     * @param path 图片路径
     * @return 成功删除为true，否则失败
     */
    public boolean deleteImageFromDisk(String path) {
        if (StringUtils.isEmpty(path)) return false;

        File image = new File(path);
        if (image.exists() && image.isFile()) {
            return image.delete();
        }

        return false;
    }

    /**
     * 移动设备上的图片
     *
     * @param image   要移动的图片
     * @param authorId 移动到的目标博主
     * @param category  目标博主的类别
     * @return 新的图片保存路径
     */
    public String moveImage(AuthorImage image, int authorId, AuthorImageCategoryEnum category) throws IOException {

        AuthorAccount account = accountDao.getAccountById(authorId);
        String dirPath = constructorManager.constructImageDirPath(account.getAuthorEmail(), category.name());
        File newDir = new File(dirPath);
        if (!newDir.exists() || !newDir.isDirectory()) newDir.mkdirs();

        File oldImage = new File(image.getPath());
        File newImage = new File(newDir.getAbsolutePath() + File.separator + oldImage.getName());
        if (oldImage.equals(newImage)) return oldImage.getAbsolutePath();

        FileUtils.moveFile(oldImage, newImage);

        return newImage.getAbsolutePath();
    }

    /**
     * 修改图片类别，并移动到对应类别
     *
     * @param authorId 博主id
     * @param imageId 图片id
     * @param category  类别
     * @return 移动了返回true
     */
    public boolean moveImageAndUpdateDbIfNecessary(int authorId, int imageId, AuthorImageCategoryEnum category)
            throws IOException {
        if (imageId <= 0 || category == null) return false;

        AuthorImage image = imageDao.getImageById(imageId);
        if (image == null || image.getImageCategory() == category.getCode()) return false;

        String newPath = moveImage(image, authorId, category);
        image.setPath(newPath);
        image.setImageCategory(category.getCode());
        return imageDao.update(image) > 0;

    }

    /**
     * 检查图片类别，如果新的图片为PRIVATE，则修改为PUBLIC，并移动文件，同时将其useCount++，旧图片的useCount--
     *
     * @param authorId    博主id
     * @param newImageId 新图片id
     * @param oldImageId 旧图片id
     * @return 操作成功返回true
     */
    public boolean moveImageAndUpdateDbAndUseCountIfNecessary(int authorId, int newImageId, int oldImageId) throws IOException {

        if (newImageId == oldImageId) return false;

        // 将新图片指向图片修改为公开并移动目录（需要的话）
        if (newImageId > 0) {
            // UPDATE: 2018/1/28 更新 是否“公开”图片由 Service 决定，而不是方法最终调用处
            AuthorImage image = imageDao.getImageById(newImageId);
            // 私有图片才有必要对齐进行“公开”处理
            if (image.getImageCategory().equals(AuthorImageCategoryEnum.PRIVATE.getCode()))
                moveImageAndUpdateDbIfNecessary(authorId, newImageId, PUBLIC);

            //新图片useCount++
            imageDao.updateUseCountPlus(newImageId);
        }

        //旧图片useCount--
        if (oldImageId > 0) {
            imageDao.updateUseCountMinus(oldImageId);
        }

        return true;
    }

    /**
     * 新增操作可能指定了新的图片引用
     *
     * @param authorId 博主id
     * @param imageId 图片id
     * @return 操作成功返回true
     */
    public boolean imageInsertHandle(int authorId, int imageId) {
        if (imageId < 0) return false;

        try {
            return moveImageAndUpdateDbAndUseCountIfNecessary(authorId, imageId, -1);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalLuceneIOException(e);
        }
    }

    /**
     * 更新操作可能指定了新的图片引用
     *
     * @param authorId    博主id
     * @param newImageId 新图片id
     * @param oldImageId 旧图片id
     * @return 操作成功返回true
     */
    public boolean imageUpdateHandle(int authorId, int newImageId, Integer oldImageId) {

        if (newImageId > 0 && !Integer.valueOf(newImageId).equals(oldImageId)) {
            try {
                return moveImageAndUpdateDbAndUseCountIfNecessary(authorId, newImageId,
                        Optional.ofNullable(oldImageId).orElse(-1));
            } catch (IOException e) {
                e.printStackTrace();
                throw new InternalLuceneIOException(e);
            }
        }

        return false;
    }

}
