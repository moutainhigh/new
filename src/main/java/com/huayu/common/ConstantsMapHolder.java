package com.huayu.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DengPeng on 2017/5/9.
 */
public class ConstantsMapHolder {

    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void put(String key, String value){
        map.put(key,value);
    }

    public String get(String key){
        return map.get(key);
    }

    private static volatile ConstantsMapHolder instance ;

    private ConstantsMapHolder(){
        this.map = new HashMap<>();
    }

    public static ConstantsMapHolder getInstance(){
        if(instance == null){
            synchronized (ConstantsMapHolder.class){
                if (instance == null){
                    instance = new ConstantsMapHolder();
                }
            }
        }
        return instance;
    }
}
