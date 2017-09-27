package com.huayu.common;

/**
 * Created by DengPeng on 2017/5/19.
 */
public class ConstantsHolder {

    private static ThreadLocalContext<LocalContext> context = new ThreadLocalContext();


    public static void setContext(LocalContext city){
        context.setContext(city);
    }

    public static LocalContext getContext(){
        return context.getContext();
    }

    public static void clearContext(){
        context.clearContext();
    }

}
