package com.huayu.common;

public class DBContextHolder{
    public static final String DATA_SOURCE = "dataSource";
    public static final String SQLServerDATA_SOURCE = "sqlServerDataSource";
      
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
      
    public static void setDBType(String dbType) {  
        contextHolder.set(dbType);  
    }  
      
    public static String getDBType() {  
        return contextHolder.get();  
    }  
      
    public static void clearDBType() {  
        contextHolder.remove();  
    }  
} 