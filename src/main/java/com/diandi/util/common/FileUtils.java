package com.diandi.util.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author shangmingyu
 * @Description: TODO
 * @date 2018/5/8 16:32
 */
public class FileUtils {

    /**
     * 将文件保存到指定路径
     *
     * @param file     文件
     * @param fullPath 路径
     */
    public static boolean saveFileTo(MultipartFile file, String fullPath) {
        try {
            file.transferTo(new File(fullPath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
