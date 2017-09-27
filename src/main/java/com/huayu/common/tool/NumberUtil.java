package com.huayu.common.tool;

/**
 * Created by DengPeng on 2017/6/13.
 */
public class NumberUtil {

    public static String buildNum(Integer num, Integer length){
        String numStr = String.valueOf(num);
        StringBuilder stringBuilder = new StringBuilder(numStr);
        int numLen = numStr.length();
        if (length>numLen){
            for (int i= 0 ; i < length-numLen; i++){
                stringBuilder.insert(i,"0");
            }
        }
        return stringBuilder.toString();
    }
}
