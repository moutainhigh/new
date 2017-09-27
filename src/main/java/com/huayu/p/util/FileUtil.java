package com.huayu.p.util;

import com.ly.service.base.ImageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 *
 * @author ZXL 2017-05-26 12:03
 **/
public class FileUtil {

    /**
     * 写文件
     * @param imgFile
     * @param path
     * @param s
     * @return
     */
    public static String cutSaveFile(MultipartFile imgFile, String path, String s){
        String str= ImageUtil.getRandomName(imgFile.getOriginalFilename());
        File a;
        try {
            a = com.ly.service.base.FileUtil.createFile(new File(path+s+str));
            imgFile.transferTo(a);
            return s+str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            /*if (file.exists() && file.isFile()) {
                return file.delete();
            } else {
                return false;
            }*/
            return file.exists() && file.isFile()?file.delete():false;
        }catch (Exception e) {
            return false;
        }
    }

}
