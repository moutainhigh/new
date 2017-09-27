package com.huayu.common.tool;

import com.ly.service.base.ImageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by DengPeng on 2016/12/30.
 */
public class FileUtil {

    public static String saveFile(MultipartFile imgFile, String path, String s){
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
}
