package com.huayu.common.tool;

import java.util.Random;
import java.util.UUID;

/**
 * Created by dengpeng on 16-9-25.
 */
public class RandomUtil {

    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String generateNum(Integer length){
        StringBuilder stringBuilder = new StringBuilder();
        Random ra =new Random();
        for (int i= 0 ; i < length; i++){
            stringBuilder.append(ra.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
